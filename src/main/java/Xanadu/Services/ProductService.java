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

import java.util.concurrent.CompletableFuture;

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
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> products =  productRepository.findAll(pageable);
        products.stream().parallel().forEach(product -> product.getImages());
        return products;
    }

    public Product findByHandleFetchEagerAll(String handle) {
        Product product = productRepository.findByHandle(handle);

        CompletableFuture<Void> imagesFuture = CompletableFuture.runAsync(() -> product.getImages());
        CompletableFuture<Void> optionsFuture = CompletableFuture.runAsync(() -> product.getOptions().stream().parallel().forEach(option -> {
            option.getOptionValues();
        }));
        CompletableFuture<Void> collectionsFuture = CompletableFuture.runAsync(() -> product.getCollections());
        CompletableFuture<Void> variantsFuture = CompletableFuture.runAsync(() -> product.getVariants());
        CompletableFuture<Void> reviewsFuture = CompletableFuture.runAsync(() -> product.getReviews());
        CompletableFuture<Void> productTagsFuture = CompletableFuture.runAsync(() -> product.getProductTags());

        CompletableFuture.allOf(imagesFuture, optionsFuture, collectionsFuture, variantsFuture, reviewsFuture, productTagsFuture).join();

        return product;
    }
}
