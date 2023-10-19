package Xanadu.Controllers.Admin;

import Xanadu.Entities.Product;
import Xanadu.Entities.Variant;
import Xanadu.Models.Admin.MenuActive;
import Xanadu.Repositories.VariantRepository;
import Xanadu.Services.ProductService;
import Xanadu.Services.VariantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
@Slf4j
public class VariantManager {
    @Autowired
    private ProductService productService;
    @Autowired
    private VariantService variantService;
    @GetMapping("/{handle}/variants/edit.html")
    public String editVariants(@PathVariable("handle") String handle, Model model) {
        MenuActive menuActive = new MenuActive("products", "variantsProduct");
        model.addAttribute("menuActive", menuActive);

        Product product = productService.findByHandleWithVariantsAndImages( handle);
        model.addAttribute("product", product);
        return "/admin/variants.edit";
    }

    @PostMapping("/variants/edit")
    public String editVariants(@ModelAttribute Product product, Model model){
        List<Variant> variants = product.getVariants();
            for (Variant variant : variants) {
            variant.setProduct(product);
        }
        List<Variant> variantsSaved =  variantService.saveAll(variants);

        return "redirect:/admin/products/"+product.getHandle()+".html";
    }

}
