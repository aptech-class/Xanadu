package Xanadu.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"variant", "cart"})
@ToString(callSuper = true, exclude = {"variant", "cart"})
@Entity
public class CartItem extends EntityBasic {
    private String title;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Float subTotalPrice;

    @ManyToOne
    private Variant variant;

    @ManyToOne
    private Cart cart;
}
