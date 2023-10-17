package Xanadu.Controllers.Admin;

import Xanadu.Entities.Product;
import Xanadu.Models.Admin.MenuActive;
import Xanadu.Services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/products")
@Slf4j
public class VariantManager {

    @Autowired
    private ProductService productService;

    @GetMapping("/{handle}/variants/edit.html")
    public String editVariants(@PathVariable("handle") String handle, Model model) {
        MenuActive menuActive = new MenuActive("products", "variantsProduct");
        model.addAttribute("menuActive", menuActive);

        Product product = productService.findByHandleWithVariantsAndImages( handle);
        model.addAttribute("product", product);
        return "/admin/variants.edit";
    }

}
