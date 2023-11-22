package Xanadu.Controllers.Admin;

import Xanadu.Entities.Customer;
import Xanadu.Entities.FulfillmentStatus;
import Xanadu.Entities.Order;
import Xanadu.Entities.TransactionKind;
import Xanadu.Models.Admin.PageOption;
import Xanadu.Services.CustomerService;
import Xanadu.Services.OrderService;
import Xanadu.Utils.HibernateProcessor;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Controller
@RequestMapping("/admin/orders")
public class OrderManager extends AbstractManager {

    @Autowired
    OrderService orderService;
    @Autowired
    CustomerService customerService;

    @GetMapping("/index.html")
    public String getOrders(
            Model model,
            @RequestParam(value = "pageSize", required = false, defaultValue = "30") Integer pageSize,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber
    ) {
        setPageOption(model, pageSize, pageNumber, "/admin/orders/index.html");
        setMenu(model, "orders.list");
        Page<Order> orders = orderService.findAll(pageNumber - 1, pageSize);
        model.addAttribute("orders", orders);
        return "/admin/orders";
    }

    @GetMapping("/{id}.html")
    public String getOrder(Model model, @PathVariable("id") Long id) throws InvocationTargetException, IllegalAccessException {
        setMenu(model, "orders");
        Order order = orderService.findByIdWithOrderItemsAndTransactions(id);
        model.addAttribute("order", hibernateProcessor.unProxy(order, new HashMap<>(), Order.class.getName() + "/"));

        return "/admin/order.view";
    }

    @GetMapping("/{id}/edit.html")
    public String editOrder(Model model, @PathVariable("id") Long id) throws InvocationTargetException, IllegalAccessException {
        setMenu(model, "orders");
        Order order = orderService.findByIdWithOrderItemsAndTransactions(id);
        model.addAttribute("order", hibernateProcessor.unProxy(order, new HashMap<>(), Order.class.getName() + "/"));
        List<String> fulfillmentStatuses = new ArrayList<>();
        fulfillmentStatuses.add(FulfillmentStatus.CANCELLED.name());
        fulfillmentStatuses.add(FulfillmentStatus.OPEN.name());
        fulfillmentStatuses.add(FulfillmentStatus.SUCCESS.name());
        fulfillmentStatuses.add(FulfillmentStatus.PENDING.name());
        fulfillmentStatuses.add(FulfillmentStatus.ERROR.name());
        model.addAttribute("fulfillmentStatuses", fulfillmentStatuses);
        return "/admin/order.edit";
    }

    @PostMapping("/{id}/edit.html")
    public String editOrder(
            Model model,
            @Valid @ModelAttribute Order order,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails

    ) {
        if (bindingResult.hasErrors()) {
            setMenu(model, "orders");
            return "/admin/order.edit";
        }
        Customer customer = customerService.findByUsername(userDetails.getUsername());
        orderService.save(order, customer);

        return "redirect:/admin/orders/" + order.getId() + ".html";
    }

    @GetMapping("/create.html")
    public String createOrder(Model model) {
        setMenu(model, "orders.create");
        model.addAttribute("order", new Order());

        return "/admin/order.create";
    }

    @Override
    protected void setMenu(Model model, @Nonnull String active) {
        setDefaultMenu(model, active, "/admin/orders/index.html");
    }

    @Override
    protected void setMenu(Model model, @Nonnull String active, String url) {
        setDefaultMenu(model, active, url);
    }

    @Override
    protected void setObjectRelateToModel(Model model) {

    }

    @Override
    protected void setPageOption(Model model, Integer pageSize, Integer pageNumber, String url) {
        List<Integer> sizeOptions = Arrays.asList(20, 30, 50);
        PageOption pageOption = new PageOption(sizeOptions, pageSize, pageNumber, url);
        model.addAttribute("pageOption", pageOption);
    }
}
