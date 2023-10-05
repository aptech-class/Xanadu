package Xanadu.Controllers.Admin;

import Xanadu.Entities.Collection;
import Xanadu.Entities.Product;
import Xanadu.Entities.ProductTag;
import Xanadu.Entities.Vendor;
import Xanadu.Services.CollectionService;
import Xanadu.Services.ProductService;
import Xanadu.Services.ProductTagService;
import Xanadu.Services.VendorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/admin")
@Slf4j
public class ProductManager {
    private final String wordErrorForHandle = ".*[#%{}\\\\^~\\[\\]`]+.*";
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTagService productTagService;
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private VendorService vendorService;

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
            @PathVariable(value = "handle", required = true) String handle,
            Model model
    ) {
        Product product = productService.findByHandleFetchEagerAll(handle);
        model.addAttribute("product", product);
        List<ProductTag> productTags = productTagService.findAll();
        model.addAttribute("productTags", productTags);
        List<Collection> collections = collectionService.findAll();
        model.addAttribute("collections", collections);
        List<Vendor> vendors = vendorService.findAll();
        model.addAttribute("vendors", vendors);

        return "admin/product";
    }

    @GetMapping(value = {"/products", "products/*"})
    public String redirectToProducts() {
        return "redirect:/admin/products.html";
    }

}
