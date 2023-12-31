package Xanadu.Controllers.Customer;

import Xanadu.Entities.Collection;
import Xanadu.Entities.Product;
import Xanadu.Services.CollectionService;
import Xanadu.Services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Controller
@Slf4j
public class HomeController extends AbstractController {
    @Autowired
    private CollectionService collectionService;
    @Autowired
    private ProductService productService;

    @GetMapping("home.html")
    public String home(Model model) throws InvocationTargetException, IllegalAccessException {
        setMenu(model);
        List<Collection> collections = collectionService.findTopCollections();
        model.addAttribute("topCollections", collections);
        List<Product> products = productService.findBestSellerProducts();
        model.addAttribute("bestSellerProducts", products);

        return "customer/home";
    }

    @GetMapping(value = {"/", "/index", "/index.html", "/home"})
    public String redirectHome() {
        return "redirect:/home.html";
    }
}
