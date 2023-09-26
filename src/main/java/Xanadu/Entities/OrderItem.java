package Xanadu.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class OrderItem extends EntityBasic{

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Float subTotalPrice;

    @ManyToOne
    private Variant variant ;

    @ManyToOne
    private Order order;
}
