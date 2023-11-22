package Xanadu.Controllers.Customer;

import Xanadu.Entities.*;
import Xanadu.Exceptions.OutOfStockException;
import Xanadu.Services.CustomerService;
import Xanadu.Services.OrderService;
import Xanadu.Services.ShippingAddressService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@Slf4j
public class CustomerController extends AbstractController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ShippingAddressService shippingAddressService;


    @GetMapping("/signin.html")
    public String signin(
            @ModelAttribute("message") String message,
            @AuthenticationPrincipal UserDetails customer,
            Model model
    ) throws InvocationTargetException, IllegalAccessException {
        if (customer == null ||
                (message != null && !message.isEmpty()) ||
                customer.getAuthorities().stream().noneMatch(auth -> auth.getAuthority().equals("USER"))
        ) {
            model.addAttribute("message", message);
            setMenu(model);
            return "/customer/signin";
        }
        return "redirect:/home.html";
    }

    @RequestMapping("/signin")
    public String signin(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String message = (String) request.getAttribute("message");
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/signin.html";
    }

    @GetMapping("/signup.html")
    public String signup(
            Model model,
            @AuthenticationPrincipal UserDetails customer
    ) throws InvocationTargetException, IllegalAccessException {
        if (customer == null ||
                customer.getAuthorities().stream().noneMatch(auth -> auth.getAuthority().equals("USER"))
        ) {
            setMenu(model);
            model.addAttribute("customer", new Customer());
            return "/customer/signup";
        }

        return "redirect:/home.html";

    }

    @PostMapping("/signup.html")
    public String signup(
            @Valid @ModelAttribute Customer customer,
            BindingResult bindingResult,
            Model model
    ) throws InvocationTargetException, IllegalAccessException {
        if (bindingResult.hasErrors()) {
            ObjectError error = bindingResult.getGlobalError();
            if (error != null) {
                String message = error.getDefaultMessage();
                bindingResult.rejectValue("confirmPassword","null", message == null ? "" : message);
            }
            setMenu(model);
            return "/customer/signup";
        }
        customer.setStatus(true);
        customerService.save(customer);
        return "redirect:/signin.html";
    }

    @GetMapping("/cart.html")
    public String getCart(Model model) throws InvocationTargetException, IllegalAccessException {
        setMenu(model);
        return "/customer/cart";
    }

    @GetMapping("/checkout.html")
    public String checkout(Model model, @AuthenticationPrincipal UserDetails userDetails) throws InvocationTargetException, IllegalAccessException {
        setMenu(model);


        List<String> paymentMethods = new ArrayList<>();
        paymentMethods.add(TransactionKind.NORMAL.name());
//        paymentMethods.add(TransactionKind.MOMO_GATEWAY.name());
        model.addAttribute("paymentMethods", paymentMethods);
        List<ShippingAddress> shippingAddresses = shippingAddressService.findByCustomer(userDetails.getUsername());
        if (shippingAddresses != null) {
            shippingAddresses.parallelStream().forEach(shippingAddress -> {
                try {
                    hibernateProcessor.unProxy(shippingAddress, new HashMap<>(), ShippingAddress.class.getName() + "/");
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        model.addAttribute("shippingAddresses", shippingAddresses);
        model.addAttribute("shippingAddress", shippingAddresses != null && !shippingAddresses.isEmpty() ? shippingAddresses.get(0) : new ShippingAddress());


        return "/customer/checkout";
    }

    @PostMapping("/checkout.html")
    public String checkout(
            Model model,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("paymentMethod") String paymentMethod,
            @RequestParam("note") String note,
            @Valid @ModelAttribute ShippingAddress shippingAddress,
            BindingResult bindingResult

    ) throws InvocationTargetException, IllegalAccessException {
        setMenu(model);
        List<String> paymentMethods = new ArrayList<>();
        paymentMethods.add(TransactionKind.NORMAL.name());
//          paymentMethods.add(TransactionKind.MOMO_GATEWAY.name());
        model.addAttribute("paymentMethods", paymentMethods);
        List<ShippingAddress> shippingAddresses = shippingAddressService.findByCustomer(userDetails.getUsername());
        if (shippingAddresses != null) {
            shippingAddresses.parallelStream().forEach(address -> {
                try {
                    hibernateProcessor.unProxy(address, new HashMap<>(), ShippingAddress.class.getName() + "/");
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        model.addAttribute("shippingAddresses", shippingAddresses);

        if (bindingResult.hasErrors()) {
            return "/customer/checkout";
        }

        Customer customer = customerService.findByUsername(userDetails.getUsername());
        try {
            orderService.save(customer, shippingAddress, note);
        } catch (OutOfStockException e) {
            model.addAttribute("message", e.getMessage());
            return "/customer/checkout";
        }

        if (paymentMethod.equals(TransactionKind.MOMO_GATEWAY.name())) {
            return "redirect:/momo/transaction.html";
        }

        return "redirect:/orders.html";
    }

    @GetMapping("/orders.html")
    public String getOrders(Model model, @AuthenticationPrincipal UserDetails userDetails) throws InvocationTargetException, IllegalAccessException {
        Customer customer = customerService.findByUsername(userDetails.getUsername());
        setMenu(model);
        List<Order> orders = orderService.findByCustomer(customer);
        model.addAttribute("orders", orders);

        return "/customer/orders";
    }

}
