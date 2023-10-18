package Xanadu.Repositories;

import Xanadu.Entities.Option;
import Xanadu.Entities.OptionValue;
import Xanadu.Entities.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface OptionValueRepository extends JpaRepository<OptionValue, Long> {
    @Query(value = "SELECT ov.* FROM option_value ov, option_table o WHERE ov.value = :value and ov.option_id = o.id and o.product_id = :productId",nativeQuery = true)
    OptionValue findByValueAndProductId(@Param("value") String value, @Param("productId") Long productId);

    @Query("SELECT ov FROM OptionValue ov WHERE ov.option = :option AND ov.id NOT IN :optionValuesIds")
    List<OptionValue> findByOptionAndIdNotIn(@Param("option") Option option,@Param("optionValuesIds") List<Long> optionValuesIds);

    @Query("SELECT ov FROM OptionValue ov WHERE ov.option IN :options")
    List<OptionValue> findByOptionIn(@Param("options") List<Option> options);
}
