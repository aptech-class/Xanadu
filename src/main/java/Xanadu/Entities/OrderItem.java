package Xanadu.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"order", "variant"})
@ToString(callSuper = true, exclude = {"order", "variant"})
@Entity
public class OrderItem extends EntityBasic {

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Float subTotalPrice;

    @ManyToOne
    private Variant variant;

    @ManyToOne
    private Order order;
}
