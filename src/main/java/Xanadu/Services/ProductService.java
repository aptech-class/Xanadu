package Xanadu.Services;

import Xanadu.Entities.Product;
import Xanadu.Repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        return productRepository.findByHandle(handle);
    }

    public Page<Product> findAllWithImages(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        return  productRepository.findAllWithImages(pageable);
    }
}
