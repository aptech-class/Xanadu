package Xanadu.Repositories;

import Xanadu.Entities.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType,Long> {
    ProductType findByTitle(String title);
}
