package Xanadu.Controllers.Admin;

import Xanadu.Entities.Order;
import Xanadu.Models.Admin.PageOption;
import Xanadu.Services.OrderService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class OrderManager extends AbstractManager {

    @Autowired
    OrderService orderService;

    @GetMapping("/index.html")
    public String getOrders(
            Model model,
            @RequestParam(value = "pageSize", required = false, defaultValue = "1") Integer pageSize,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "30") Integer pageNumber
    ) {
        setPageOption(model, pageSize, pageNumber, "/admin/orders/index.html");
        setMenu(model,"orders.list");
        Page<Order> orders = orderService.findAll(pageNumber - 1, pageSize);
        return "/admin/orders";
    }

    @GetMapping("/{id}.html")
    public String getOrder(Model model, @PathVariable("id") Long id){
        setMenu(model,"orders");
        Order  order = orderService.findByIdWithOrderItem(id)    ;
        model.addAttribute("order",order);

        return "/admin/order.view";
    }

    @GetMapping("/create.html")
    public String createOrder(Model model){
        setMenu(model,"orders.create");
        model.addAttribute("order",new Order());

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
