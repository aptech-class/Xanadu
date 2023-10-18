package Xanadu.Repositories;

import Xanadu.Entities.Image;
import Xanadu.Entities.OptionValue;
import Xanadu.Entities.Product;
import Xanadu.Entities.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Long> {
    @Query("SELECT v FROM Variant v WHERE v.image IN :images")
    List<Variant> findByImageIn(@Param("images") List<Image> images);
    Variant findBySku(String string);
    @Modifying
    @Query("DELETE FROM Variant v WHERE v.product = :product AND v.id NOT IN :variantsIds")
    void deleteByProductAndIdNotIn(@Param("product") Product product,@Param("variantsIds") List<Long> variantsIds);

    @Query("SELECT v FROM Variant v WHERE v.product = :product AND v.optionValues IS EMPTY")
    List<Variant> findByProductAndNotExistOptionValue(@Param("product") Product product);
}
