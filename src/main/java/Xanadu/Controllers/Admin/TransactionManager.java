package Xanadu.Controllers.Admin;

import Xanadu.Entities.Customer;
import Xanadu.Entities.Order;
import Xanadu.Entities.Transaction;
import Xanadu.Entities.TransactionKind;
import Xanadu.Services.CustomerService;
import Xanadu.Services.OrderService;
import Xanadu.Services.TransactionService;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller("myTransactionManager")
@RequestMapping("/admin/transactions/")
public class TransactionManager extends AbstractManager {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/create.html")
    public String create(
            Model model,
            @RequestParam(value = "orderId") Long orderId

    ) {
        Order order = orderService.findById(orderId);
        setMenu(model, "transactions.create");
        Transaction transaction = new Transaction();
        transaction.setOrder(order);
        transaction.setCustomer(order.getCustomer());
        transaction.setAmount(order.getAmount());
        model.addAttribute("transaction", transaction);
        return "/admin/transaction.create";
    }

    @PostMapping("/create.html")
    public String create(
            Model model,
            @Valid @ModelAttribute Transaction transaction,
            BindingResult bindingResult

    ) {
        if (bindingResult.hasErrors()) {
            setMenu(model, "transactions.create");
            return "/admin/transaction.create";

        }
        transactionService.save(transaction);

        return "redirect:/admin/orders/" + transaction.getOrder().getId() + ".html";
    }

    @Override
    protected void setMenu(Model model, @Nonnull String active) {
        setDefaultMenu(model, active, "/admin/transactions/index.html");
    }

    @Override
    protected void setMenu(Model model, @Nonnull String active, String url) {

    }

    @Override
    protected void setObjectRelateToModel(Model model) {

    }

    @Override
    protected void setPageOption(Model model, Integer pageSize, Integer pageNumber, String url) {

    }
}
