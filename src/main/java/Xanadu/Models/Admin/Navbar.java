package Xanadu.Models.Admin;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Navbar {
    private String title;
    private String url;
    private Boolean status = false;
    private List<Navbar> navbars;


    public static Navbar create() {
        Navbar navbar = new Navbar();
        navbar.setTitle("menu");
        navbar.setUrl("/admin/products/index.html");
        navbar.setStatus(true);
        List<Navbar> navbarMap = createNavbars();
        navbar.setNavbars(navbarMap);
        return navbar;
    }

    public static Navbar create(String url) {
        Navbar navbar = create();
        navbar.setUrl(url);
        return navbar;
    }

    private static List<Navbar> createNavbars() {
        List<Navbar> navbarMap = new ArrayList<>();
        Navbar dashboard = new Navbar("dashboard", "/admin/home.html", false, null);
        navbarMap.add(dashboard);

        Navbar productNavbar = new Navbar();
        productNavbar.setTitle("products");
        List<Navbar> productSubNavbar = new ArrayList<>();
        productSubNavbar.add(new Navbar("list", "/admin/products/index.html", false, null));
        productSubNavbar.add(new Navbar("create", "/admin/products/create.html", false, null));
        productSubNavbar.add(new Navbar("edit", "/admin/products/edit.html", false, null));
        productSubNavbar.add(new Navbar("variant", "/admin/products/variant.html", false, null));
        productNavbar.setNavbars(productSubNavbar);
        navbarMap.add(productNavbar);


        return navbarMap;
    }

    public void setActive(@Nonnull String active) {
        this.status = true;
        if (active.isEmpty() || this.navbars == null) {
            return;
        }

        String title = active.contains(".") ? active.substring(0, active.indexOf(".")) : active;
        AtomicBoolean checkAll = new AtomicBoolean(false);
        String subActive = active.replace(title, "").replace(".", "");

        for (Navbar navbar : this.navbars) {
            {
                if (Objects.equals(title, navbar.getTitle())) {
                    checkAll.set(true);
                    navbar.setActive(subActive);
                    break;
                }
            }
        }
        if (!checkAll.get()) {
            throw new RuntimeException("object(" + title + ") active not found!");
        }
    }
}
