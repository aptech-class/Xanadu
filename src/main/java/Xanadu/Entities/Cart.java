package Xanadu.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString(callSuper = true, exclude = {"cartItems", "customer"})
@EqualsAndHashCode(callSuper = true, exclude = {"cartItems", "customer"})
public class Cart extends EntityBasic {

    @Column(nullable = false)
    private Float totalPrice;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;

    @ManyToOne
    private Customer customer;

}
