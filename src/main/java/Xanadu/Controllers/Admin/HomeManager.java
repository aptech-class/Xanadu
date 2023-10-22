package Xanadu.Controllers.Admin;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/admin")
@Slf4j
public class HomeManager extends AbstractManager {

    @GetMapping(value = {"home.html"})
    public String home(Model model) {
        setMenu(model,"dashboard");
        return "admin/index";
    }

    @RequestMapping(value = {"/home", "/home/**", "/", "", "/index", "/index.html"})
    public String redirectToHomePage() {
        return "redirect:/admin/home.html";
    }

    @Override

    protected void setMenu(Model model, @Nonnull String active) {
        setDefaultMenu(model,active,"/admin/home.htm");
    }
    protected void setMenu(Model model, @Nonnull String active,String url) {
        setDefaultMenu(model,active,url);
    }
    @Override
    protected void setObjectRelateToModel(Model model) {

    }

    @Override
    protected void setPageOption(Model model, Integer pageSize, Integer pageNumber,String url) {

    }
}
