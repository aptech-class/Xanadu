package Xanadu.Controllers.Admin;

import Xanadu.Models.Admin.MenuActive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/admin")
@Slf4j
public class HomeController {

    @GetMapping(value = {"home.html"})
    public String home(Model model) {
        MenuActive  menuActive = new MenuActive("dashboard","none");
        model.addAttribute("menuActive",menuActive);
        model.addAttribute("pageTitle", "Home");
        return "admin/index";
    }

    @RequestMapping(value = {"/home", "/home/**", "/", "", "index", "index.html"})
    public String redirectToHomePage() {
        return "redirect:/admin/home.html";
    }
}
