package Xanadu.Services;

import Xanadu.Entities.Collection;
import Xanadu.Entities.Product;
import Xanadu.Repositories.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionService {
    @Autowired
    CollectionRepository collectionRepository;

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
}
