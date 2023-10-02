package Xanadu.Repositories;

import Xanadu.Entities.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTagRepository extends JpaRepository<ProductTag,Long> {
    ProductTag findByValue(String value);
}
