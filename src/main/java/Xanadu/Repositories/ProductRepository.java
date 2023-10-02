package Xanadu.Repositories;

import Xanadu.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByHandle(String handle);
}
