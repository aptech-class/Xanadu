package Xanadu.Controllers.Admin;

import Xanadu.Models.Admin.Navbar;
import jakarta.annotation.Nonnull;
import org.springframework.ui.Model;

import java.util.List;

public abstract class AbstractManager {


    void setDefaultMenu(Model model,@Nonnull String active,@Nonnull String url) {
        Navbar navbar = Navbar.create(url);
        navbar.setActive(active);
        model.addAttribute("menu", navbar);
    }
    abstract protected void setMenu(Model model,@Nonnull String active);
    abstract protected void setMenu(Model model,@Nonnull String active,String url);
    abstract protected void  setObjectRelateToModel(Model model);
    protected abstract void setPageOption(Model model, Integer pageSize, Integer pageNumber,String url);
}
