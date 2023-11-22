package Xanadu.Controllers.Admin;

import Xanadu.Entities.*;
import Xanadu.Models.Admin.PageOption;
import Xanadu.Services.*;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/admin/products")
@Slf4j
public class ProductManager extends AbstractManager {
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
    @Autowired
    private VariantService variantService;

    @GetMapping("/index.html")
    public String getProducts(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "30") Integer pageSize,
            Model model
    ) {
        Page<Product> products = productService.findAllWithImages(pageNumber - 1, pageSize);
        model.addAttribute("products", products);
        setPageOption(model, pageSize, pageNumber,"/admin/products/index.html");
        setMenu(model, "products.list");
        return "admin/products";
    }

    @GetMapping("/{handle}.html")
    public String getProduct(
            @PathVariable(value = "handle", required = true) String handle,
            Model model
    ) {
        Product product = productService.findByHandleFetchEagerAll(handle);
        if (product == null) return "admin/notFound";
        model.addAttribute("product", product);
        setMenu(model, "products");

        return "admin/product.view";
    }

    @GetMapping("/create.html")
    public String createProduct(Model model) {
        setMenu(model, "products.create");
        model.addAttribute("product", new Product());
        setObjectRelateToModel(model);
        return "/admin/product.create";
    }

    @PostMapping("/create")
    public String createProduct(
            @Valid @ModelAttribute Product product,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            setMenu(model, "products.create");
            setObjectRelateToModel(model);
            return "/admin/product.create";
        }
        productService.saveSpread(product);
        return "redirect:/admin/products/" + product.getHandle() + ".html";
    }

    @GetMapping("/{handle}/edit.html")
    public String editProduct(
            @PathVariable("handle") String handle,
            Model model
    ) {
        setMenu(model, "products");
        setObjectRelateToModel(model);

        Product productExists = productService.findByHandleFetchEagerAll(handle);
        model.addAttribute("product", productExists);
        return "/admin/product.edit";
    }

    @PostMapping("/edit")
    public String editProduct(
            @Valid @ModelAttribute Product product,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            setMenu(model, "products");
            setObjectRelateToModel(model);
            return "/admin/product.edit";
        }
        productService.saveSpread(product);
        return "redirect:/admin/products/" + product.getHandle() + "/variants/edit.html";
    }

    @GetMapping("/{handle}/variants/edit.html")
    public String editVariants(@PathVariable("handle") String handle, Model model) {
        Product product = productService.findByHandleWithVariantsAndImages(handle);
        if (product == null) return "/admin/notFound";
        model.addAttribute("product", product);
        setMenu(model, "products");
        return "/admin/product.variants.edit";
    }

    @GetMapping("/edit.html")
    public String findProductToEdit(Model model) {
        setMenu(model, "products.edit");
        return "/admin/product.edit";
    }

    @GetMapping("/orderItems.html")
    public String findProductToEditVariants(Model model) {
        setMenu(model, "products.variants");
        return "/admin/product.variants.edit";
    }

    @PostMapping("/variants/edit")
    public String editVariants(@ModelAttribute Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            setMenu(model, "products.variant");
            return "/admin/variants.edit";
        }
        List<Variant> variants = product.getVariants();
        for (Variant variant : variants) {
            variant.setProduct(product);
        }
        variantService.saveAll(variants);

        return "redirect:/admin/products/" + product.getHandle() + ".html";
    }

    @GetMapping(value = {"/products", "products/*"})
    public String redirectToGetProducts() {
        return "redirect:/admin/products/products.html";
    }

    protected void setObjectRelateToModel(Model model) {
        List<ProductTag> productTags = productTagService.findAll();
        model.addAttribute("productTags", productTags);
        List<Collection> collections = collectionService.findAll();
        model.addAttribute("collections", collections);
        List<Vendor> vendors = vendorService.findAll();
        model.addAttribute("vendors", vendors);
        List<ProductType> productTypes = productTypeService.findAll();
        model.addAttribute("productTypes", productTypes);
    }

    @Override
    protected void setPageOption(Model model, Integer pageSize, Integer pageNumber,String url) {
        List<Integer> sizeOptions = Arrays.asList(20, 30, 50);
        PageOption pageOption = new PageOption(sizeOptions, pageSize, pageNumber, url);
        model.addAttribute("pageOption", pageOption);
    }

    @Override
    protected void setMenu(Model model, @Nonnull String active) {
        setDefaultMenu(model, active, "/admin/products/index.html");
    }

    protected void setMenu(Model model, @Nonnull String active, String url) {
        setDefaultMenu(model, active, url);
    }
}
