package Xanadu.Models.Admin;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.lang.model.element.Name;
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

    public Navbar(String title, String url) {
        this.url = url;
        this.title = title;
    }

    public static Navbar create() {
        Navbar navbar = new Navbar();
        navbar.setTitle("menu");
        navbar.setUrl("/admin/products/index.html");
        navbar.setStatus(true);
        List<Navbar> navbars = createNavbars();
        navbar.setNavbars(navbars);
        return navbar;
    }

    public static Navbar create(String url) {
        Navbar navbar = create();
        navbar.setUrl(url);
        return navbar;
    }

    private static List<Navbar> createNavbars() {
        List<Navbar> navbars = new ArrayList<>();
        Navbar dashboard = new Navbar("dashboard", "/admin/home.html");
        navbars.add(dashboard);

        Navbar productNavbar = new Navbar();
        productNavbar.setTitle("products");
        List<Navbar> productsSubNavbar = new ArrayList<>();
        productsSubNavbar.add(new Navbar("list", "/admin/products/index.html"));
        productsSubNavbar.add(new Navbar("create", "/admin/products/create.html"));
        // productsSubNavbar.add(new Navbar("edit", "/admin/products/edit.html"));
        // productsSubNavbar.add(new Navbar("variants", "/admin/products/orderItems.html"));
        productNavbar.setNavbars(productsSubNavbar);
        navbars.add(productNavbar);

        Navbar customerNavbar = new Navbar();
        customerNavbar.setTitle("customers");
        List<Navbar> customersSubNavbar = new ArrayList<>();
        customersSubNavbar.add(new Navbar("list", "/admin/customers/index.html"));
        customersSubNavbar.add(new Navbar("create", "/admin/customers/create.html"));
        // customersSubNavbar.add(new Navbar("edit", "/admin/customers/edit.html"));
        // customersSubNavbar.add(new Navbar("orders", "/admin/customers/orders.html"));
        customerNavbar.setNavbars(customersSubNavbar);
        navbars.add(customerNavbar);

        Navbar ordersNavbar = new Navbar();
        ordersNavbar.setTitle("orders");
        List<Navbar> ordersSubNavbar = new ArrayList<>();
        ordersSubNavbar.add(new Navbar("list", "/admin/orders/index.html"));
        ordersSubNavbar.add(new Navbar("create", "/admin/orders/create.html"));
        // ordersSubNavbar.add(new Navbar("edit", "/admin/orders/edit.html"));
        // ordersSubNavbar.add(new Navbar("items", "/admin/orders/items.html"));
        ordersNavbar.setNavbars(ordersSubNavbar);
        navbars.add(ordersNavbar);

        Navbar categoriesNavbar = new Navbar();
        categoriesNavbar.setTitle("categories");
        List<Navbar> categoriesSubNavbar = new ArrayList<>();
        categoriesSubNavbar.add(new Navbar("list", "/admin/categories/index.html"));
        categoriesSubNavbar.add(new Navbar("create", "/admin/categories/create.html"));
        // categoriesSubNavbar.add(new Navbar("edit", "/admin/categories/edit.html"));
        // categoriesSubNavbar.add(new Navbar("items", "/admin/categories/items.html"));
        categoriesNavbar.setNavbars(categoriesSubNavbar);
        navbars.add(categoriesNavbar);

        Navbar usersNavbar = new Navbar();
        usersNavbar.setTitle("users");
        List<Navbar> usersSubNavbar = new ArrayList<>();
        usersSubNavbar.add(new Navbar("list", "/admin/users/index.html"));
        usersSubNavbar.add(new Navbar("create", "/admin/users/create.html"));
        // usersSubNavbar.add(new Navbar("edit", "/admin/users/edit.html"));
        // usersSubNavbar.add(new Navbar("items", "/admin/users/items.html"));
        usersNavbar.setNavbars(usersSubNavbar);
        navbars.add(usersNavbar);

        return navbars;
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
