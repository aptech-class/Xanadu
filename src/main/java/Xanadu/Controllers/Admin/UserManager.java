package Xanadu.Controllers.Admin;

import Xanadu.Entities.Collection;
import Xanadu.Entities.Role;
import Xanadu.Entities.User;
import Xanadu.Models.Admin.PageOption;
import Xanadu.Services.UserService;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/users")
@Slf4j
public class UserManager extends AbstractManager {
    @Autowired
    UserService userService;

    @GetMapping("/index.html")
    public String getUsers(Model model) {
        setMenu(model, "users.list");

        return "admin/users";
    }

    @GetMapping("/{username}.html")
    public String getUser (Model model, @PathVariable("username") String username){
        setMenu(model,"users");
        User user = userService.findByUsername(username);
        model.addAttribute("user",user);
        return "admin/user.view";
    }

    @GetMapping("/create.html")
    public String create(Model model) {
        setMenu(model, "users.create");
        model.addAttribute("user", new User());
        setObjectRelateToModel(model);

        return "admin/user.create";
    }

    @PostMapping("/create.html")
    public String create(
            @Valid @ModelAttribute User user,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            setMenu(model, "users.create");
            setObjectRelateToModel(model);
            return "admin/user.create";
        }

        User userSaved = userService.save(user);
        return "redirect:/admin/users/" + userSaved.getUsername() + ".html";
    }



    @Override
    protected void setMenu(Model model, @Nonnull String active) {
        setDefaultMenu(model, active, "/admin/users/index.html");
    }

    @Override
    protected void setMenu(Model model, @Nonnull String active, String url) {
        setDefaultMenu(model, active, url);
    }

    @Override
    protected void setObjectRelateToModel(Model model) {
        String[] roles = {Role.ADMIN.name(), Role.MIDDLE.name(), Role.STAFF.name()};
        model.addAttribute("roles", roles);
    }

    @Override
    protected void setPageOption(Model model, Integer pageSize, Integer pageNumber, String url) {
        List<Integer> sizeOptions = Arrays.asList(20, 30, 50);
        PageOption pageOption = new PageOption(sizeOptions, pageSize, pageNumber, url);
        model.addAttribute("pageOption", pageOption);
    }
}
