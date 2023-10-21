package Xanadu.Controllers.Admin;

import Xanadu.Models.Admin.Navbar;
import jakarta.annotation.Nonnull;
import org.springframework.ui.Model;

public abstract class AbstractManger {

    void setMenu(Model model,@Nonnull String active) {
        Navbar navbar = Navbar.create();
        navbar.setActive(active);
        model.addAttribute("menu", navbar);
    }

    void setMenu(Model model,@Nonnull String active,@Nonnull String url) {
        Navbar navbar = Navbar.create();
        navbar.setActive(active);
        model.addAttribute("menu", navbar);
    }
    abstract protected void  setObjectRelateToModel(Model model);

}
