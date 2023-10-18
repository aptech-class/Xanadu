package Xanadu.Repositories;

import Xanadu.Entities.Option;
import Xanadu.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option,Long> {
    @Query("SELECT o from option_table o WHERE o.product = :product AND o.id NOT IN :optionsIds")
    List<Option> findByProductAndIdInNotIn(@Param("product") Product product,@Param("optionsIds") List<Long> optionsIds);

    List<Option> findByProduct(Product product);
}
