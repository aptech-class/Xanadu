package Xanadu.Controllers.Customer;

import Xanadu.Entities.Collection;
import Xanadu.Services.CollectionService;
import Xanadu.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.InvocationTargetException;


@Controller
@RequestMapping("/collections")
public class CollectionController extends AbstractController{
    @Autowired
    ProductService productService;
    @Autowired
    CollectionService collectionService ;
    @GetMapping("/{handle}/products.html")
    public String getProducts (@PathVariable("handle") String handle, Model model) throws InvocationTargetException, IllegalAccessException {
        setMenu(model);
        Collection collection =  collectionService.findByHandleWithProducts(handle);
        model.addAttribute("collection", collection);
        return "customer/products";
    }
}
