package Xanadu.Controllers.Admin.Api;

import Xanadu.Entities.Product;
import Xanadu.Services.ProductService;
import Xanadu.Utils.HibernateProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/amin/api")
public class ResProductManager{
    @Autowired
    ProductService productService;
    @Autowired
    HibernateProcessor hibernateProcessor;

    @GetMapping("/product.json")
    public ResponseEntity<List<Product>> getProducts(@RequestParam("title") String title) {
        List<Product> products = productService.findByTitleContains(title);
        products.parallelStream().forEach(product -> {
            try {
                hibernateProcessor.unProxy(product, new HashMap<>(), new StringBuilder());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
        return ResponseEntity.status(200).body(products);
    }
}
