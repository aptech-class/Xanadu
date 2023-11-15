package Xanadu.Controllers.Customer;

import Xanadu.Entities.Product;
import Xanadu.Services.ProductService;
import Xanadu.Utils.HibernateProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController extends AbstractController{
    @Autowired
    private ProductService productService;

    @Autowired
    HibernateProcessor hibernateProcessor;

    @GetMapping("/{handle}.html")
    public String getProduct (@PathVariable("handle") String handle, Model model) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        setMenu(model);
        Product product = productService.findByHandleWithImagesAndOptionsAndVariantsAndCollections(handle);

        model.addAttribute("product", hibernateProcessor.unProxy(product,new HashMap<>(),new StringBuilder()));
        List<Product> products  = productService.findAlsoLikeProducts(product.getCollections(),product);
        model.addAttribute("alsoLikeProducts",products);

        return "/customer/product.html";
    }

}
