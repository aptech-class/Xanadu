package Xanadu.API.Controllers;

import Xanadu.Entities.Product;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ResProductController {
    @GetMapping("/product.json")
    public ResponseEntity<List<Product>> getProducts(@Param("filter") String search) {
        List<Product> products = new ArrayList<>();
        return ResponseEntity.status(200).body(products);
    }
}
