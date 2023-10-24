package Xanadu.Repositories;

import Xanadu.Entities.OrderItem;
import Xanadu.Entities.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    @Query("SELECT oi FROM OrderItem oi WHERE oi.variant IN :variants")
    List<OrderItem> findByVariantIn(@Param("variants") List<Variant> variants);
}
