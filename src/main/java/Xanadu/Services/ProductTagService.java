package Xanadu.Services;

import Xanadu.Entities.ProductTag;
import Xanadu.Repositories.ProductTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTagService {
    @Autowired
    ProductTagRepository productTagRepository;

    public List<ProductTag> saveAll(Iterable<ProductTag> productTypes) {
        return productTagRepository.saveAll(productTypes);
    }

    public ProductTag save(ProductTag productTag) {
        return productTagRepository.save(productTag);
    }

    public ProductTag findByValue(String value) {
        return productTagRepository.findByValue(value);
    }

    public List<ProductTag> findAll() {
        return productTagRepository.findAll();
    }
}
