package Xanadu.Controllers.Customer;

import Xanadu.Entities.Customer;
import Xanadu.Entities.ShippingAddress;
import Xanadu.Services.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class CustomerController extends AbstractController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/signin.html")
    public String signin(
            @ModelAttribute("message") String message,
            @AuthenticationPrincipal UserDetails customer,
            Model model
    ) {
        if (customer != null) {
            return "redirect:/home.html";
        }
        setMenu(model);
        model.addAttribute("message", message);
        return "/customer/signin";
    }

    @PostMapping("/signin")
    public String signin(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String message = (String) request.getAttribute("message");
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/signin.html";
    }

    @GetMapping("/signup.html")
    public String signup(
            Model model,
            @AuthenticationPrincipal UserDetails customer
    ) {
        if (customer != null) {
            return "redirect:/home.html";
        }
        setMenu(model);
        model.addAttribute("customer", new Customer());

        return "/customer/signup";
    }

    @PostMapping("/signup.html")
    public String signup(
            @Valid @ModelAttribute Customer customer,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            setMenu(model);
            return "/customer/signup";
        }
        customer.setStatus(true);
        customerService.save(customer);
        return "redirect:/signin.html";
    }

    @GetMapping("/cart.html")
    public String getCart(Model model) {
        setMenu(model);
        return "/customer/cart";
    }

    @GetMapping("/checkout.html")
    public String checkout(Model model) {
        setMenu(model);
        return "/customer/checkout";
    }

    @PostMapping("/checkout.html")
    public String checkout(
            Model model,
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @ModelAttribute("shippingAddress") ShippingAddress shippingAddress,
            BindingResult bindingResult

    ) {
        return "/checkout";
    }


}
