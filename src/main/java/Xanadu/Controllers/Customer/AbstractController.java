package Xanadu.Controllers.Customer;

import Xanadu.Entities.Category;
import Xanadu.Entities.Customer;
import Xanadu.Services.CategoryService;
import Xanadu.Services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.List;

@Slf4j
public abstract class AbstractController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    CustomerService customerService;

    public void setMenu(Model model) {
        List<Category> categories = categoryService.findByStatus(true);
        model.addAttribute("categories", categories);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (!(authentication instanceof AnonymousAuthenticationToken) && username != null) {
            Customer customer = customerService.findByUsernameWithCart(username);
            model.addAttribute("customer", customer);
        }
    }
}
