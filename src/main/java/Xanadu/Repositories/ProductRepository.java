package Xanadu.Repositories;

import Xanadu.Entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByHandle(String handle);

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.images")
    Page<Product> findAllWithImages(Pageable pageable);
}
