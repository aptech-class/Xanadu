package Xanadu.Repositories;

import Xanadu.Entities.OptionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionValueRepository extends JpaRepository<OptionValue, Long> {
    @Query(value = "SELECT ov.* FROM option_value ov, option_table o WHERE ov.value = :value and ov.option_id = o.id and o.product_id = :productId",nativeQuery = true)
    OptionValue findByValueAndProductId(@Param("value") String value, @Param("productId") Long productId);
}
