package Xanadu.Repositories;

import Xanadu.Entities.Image;
import Xanadu.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    Image findBySrc(String src);

    List<Image> findByProductId(Long id);

    @Modifying
    @Query("DELETE FROM Image i WHERE i.product = :product AND i.id NOT IN :imagesIds")
    void deleteByProductAndIdNotIn(@Param("product") Product product, @Param("imagesIds") List<Long> imagesIds);

    @Query("SELECT i FROM Image i WHERE i.product = :product AND i.id NOT IN :imagesIds")
    List<Image> findByProductAndIdNotIn(@Param("product") Product product,@Param("imagesIds") List<Long> imagesIds);

    List<Image> findByProduct(Product product);
}
