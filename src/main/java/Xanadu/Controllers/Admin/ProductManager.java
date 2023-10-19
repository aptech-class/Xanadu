package Xanadu.Controllers.Admin;

import Xanadu.Entities.*;
import Xanadu.Models.Admin.MenuActive;
import Xanadu.Models.Admin.PageOption;
import Xanadu.Services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/admin")
@Slf4j
public class ProductManager {
    private final List<Integer> sizeOptions = Arrays.asList(20, 30, 50);
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTagService productTagService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private VendorService vendorService;
    @Autowired
    private ProductTypeService productTypeService;

    @GetMapping("/products.html")
    public String getProducts(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "30") Integer pageSize,
            Model model
    ) {
        Page<Product> products = productService.findAllWithImages(pageNumber - 1, pageSize);
        model.addAttribute("products", products);
        MenuActive menuActive = new MenuActive("products", "listProducts");
        model.addAttribute("menuActive", menuActive);
        PageOption pageOption = new PageOption(sizeOptions, pageSize, pageNumber, "/admin/products");
        model.addAttribute("pageOption", pageOption);
        return "admin/products";
    }

    @GetMapping("/products/{handle}.html")
    public String getProduct(
            @PathVariable(value = "handle", required = true) String handle,
            Model model
    ) {
        Product product = productService.findByHandleFetchEagerAll(handle);
        model.addAttribute("product", product);

        MenuActive menuActive = new MenuActive("products", "itemProduct");
        model.addAttribute("menuActive", menuActive);

        return "admin/product";
    }

    @GetMapping("/products/create.html")
    public String createProduct(Model model) {
        MenuActive menuActive = new MenuActive("products", "createProduct");
        model.addAttribute("menuActive", menuActive);

        List<ProductTag> productTags = productTagService.findAll();
        model.addAttribute("productTags", productTags);
        List<Collection> collections = collectionService.findAll();
        model.addAttribute("collections", collections);
        List<Vendor> vendors = vendorService.findAll();
        model.addAttribute("vendors", vendors);
        List<ProductType> productTypes = productTypeService.findAll();
        model.addAttribute("productTypes", productTypes);

        return "/admin/product.create";
    }

    @PostMapping("/products/create")
    public String createProduct(@ModelAttribute Product product, Model model) {
        Product productCreated = productService.saveSpread(product);
        model.addAttribute("product", product);
        MenuActive menuActive = new MenuActive("products", "createProduct");
        model.addAttribute("menuActive", menuActive);

        return "redirect:/admin/products/" + product.getHandle() + ".html";
    }

    @GetMapping("/products/{handle}/edit.html")
    public String editProduct(
            @PathVariable(value = "handle", required = true) String handle,
            @ModelAttribute("redirectProduct") Product redirectProduct,
            Model model
    ) {
        MenuActive menuActive = new MenuActive("products", "editProduct");
        model.addAttribute("menuActive", menuActive);
        List<ProductTag> productTags = productTagService.findAll();
        model.addAttribute("productTags", productTags);
        List<Collection> collections = collectionService.findAll();
        model.addAttribute("collections", collections);
        List<Vendor> vendors = vendorService.findAll();
        model.addAttribute("vendors", vendors);
        List<ProductType> productTypes = productTypeService.findAll();
        model.addAttribute("productTypes", productTypes);

        if (redirectProduct.getId() != null) {
            model.addAttribute("product", redirectProduct);
            return "/admin/product.edit";
        }

        Product productExists = productService.findByHandleFetchEagerAll(handle);
        model.addAttribute("product", productExists);
        return "/admin/product.edit";
    }

    @PostMapping("/products/edit")
    public String editProduct(
            @ModelAttribute Product product,
            RedirectAttributes redirectAttributes
    ) {
//        redirectAttributes.addFlashAttribute("redirectProduct", product);
//        return "redirect:/admin/products/" + product.getHandle() + "/edit.html";

        productService.saveSpread(product);
        return "redirect:/admin/products/" + product.getHandle() + "/variants/edit.html";
    }

    @GetMapping(value = {"/products", "products/*"})
    public String redirectToGetProducts() {
        return "redirect:/admin/products.html";
    }

}
