package Xanadu.Repositories;

import Xanadu.Entities.Collection;
import Xanadu.Entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByHandle(String handle);

    List<Product> findByTitleContains(String title);
    @Query("SELECT p FROM Product p " +
            "LEFT JOIN  p.variants v " +
            "LEFT JOIN  v.orderItems oi " +
            "WHERE oi.createdAt BETWEEN :startDate AND :endDate " +
            "GROUP BY p " +
            "ORDER BY SUM(oi.quantity) DESC")
    List<Product> findBestSellerProducts(@Param("startDate")Date startDate,@Param("endDate") Date endDate, Pageable pageable );

    Page<Product> findDistinctByCollectionsInAndIdNot(List<Collection> collections, Long id, Pageable pageable);

    int countDistinctByCollectionsInAndIdNot(List<Collection> collections, Long id);
}
