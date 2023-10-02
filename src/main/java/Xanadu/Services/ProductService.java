package Xanadu.Services;

import Xanadu.Entities.Product;
import Xanadu.Repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product findByHandle(String handle) {
//        List<Product> products =  productRepository.findByHandle(handle);
//        if(products.size()>2){
//            log.info(">2");
//        }
//        return products.isEmpty() ? null: products.get(0) ;
//        try{
        return productRepository.findByHandle(handle);
//
//        }catch (Exception e){
//            log.info(e.getMessage());
//            return null;
//        }
    }
}
