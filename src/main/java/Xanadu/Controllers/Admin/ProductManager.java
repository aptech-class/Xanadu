package Xanadu.Controllers.Admin;

import Xanadu.Entities.Product;
import Xanadu.Services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/admin")
@Slf4j
public class ProductManager {
    @Autowired
    private ProductService productService;

    @GetMapping("/products.html")
    public String getProducts(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "30") Integer pageSize,
            Model model
    ) {
        Page<Product> products = productService.findAllWithImages(pageNumber - 1, pageSize);
        model.addAttribute("products", products);
        return "admin/products";
    }

    @GetMapping("/products/{handle}.html")
    public String getProduct(
            @RequestParam(value = "handle", required = true) String handle,
            Model model
    ) {
        Product product = productService.findByHandle(handle);
        model.addAttribute("product",product);
        return "product";
    }

    @GetMapping(value = {"/products", "products/*"})
    public String redirectToProducts() {
        return "redirect:/admin/products.html";
    }

}
