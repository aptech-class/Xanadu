package Xanadu.Repositories;

import Xanadu.Entities.Collection;
import Xanadu.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
    Collection findByHandle(String handle);

    List<Collection> findByProductsIn(List<Product> products);
}
