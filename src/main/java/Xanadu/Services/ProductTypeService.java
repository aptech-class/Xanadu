package Xanadu.Services;

import Xanadu.Entities.ProductType;
import Xanadu.Repositories.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class ProductTypeService {
    @Autowired
    ProductTypeRepository productTypeRepository;

    public List<ProductType> saveAll(Iterable<ProductType> productTypes) {
        return productTypeRepository.saveAll(productTypes);
    }

    public ProductType save(ProductType productType) {
        return productTypeRepository.save(productType);
    }

    public ProductType findByTitle(String title) {
        return productTypeRepository.findByTitle(title);
    }
}
