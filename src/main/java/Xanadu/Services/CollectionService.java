package Xanadu.Services;

import Xanadu.Entities.Collection;
import Xanadu.Entities.Image;
import Xanadu.Entities.Product;
import Xanadu.Entities.Variant;
import Xanadu.Repositories.CollectionRepository;
import Xanadu.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionService {
    @Autowired
    CollectionRepository collectionRepository;
    @Autowired
    ProductRepository productRepository;

    public Collection findByHandle(String handle){
        return collectionRepository.findByHandle(handle);
    }

    public Collection save(Collection collection) {
        return collectionRepository.save(collection);
    }

    public List<Collection> findByProduct(Product product) {
        List<Product> products =new ArrayList<>();
        products.add(product);
        return collectionRepository.findByProductsIn(products);
    }

    public List<Collection> findAll() {
        return collectionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Collection> findTopCollections() {
        List<Collection> collections = collectionRepository.findAll(PageRequest.of(0,8)).toList();
        collections.forEach(collection -> {
           collection.getProducts().forEach(product -> product.getImages().forEach(Image::getId));
        });
        return collections;
    }

    @Transactional(readOnly = true)
    public Collection findByHandleWithProducts(String handle) {
        Collection collection=  collectionRepository.findByHandle(handle);
        collection.getProducts().forEach(product -> {
            product.getImages().forEach(Image::getId);
            product.getVariants().forEach(Variant::getId);
            product.getOptions().forEach(option -> option.getOptionValues().forEach(optionValue -> optionValue.getVariants().forEach(Variant::getId)));
        });
        return collection;
    }
}
