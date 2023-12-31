package Xanadu.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@ToString(callSuper = true, exclude = {"image", "product", "optionValues", "cartItems", "orderItems"})
@EqualsAndHashCode(callSuper = true, exclude = {"image", "product", "optionValues", "cartItems", "orderItems"})
public class Variant extends EntityBasic {

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false)
    private Float prePrice;

    @Column(nullable = false)
    private Integer inventory;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String sku;

    @ManyToOne
    private Image image;

    @ManyToOne
    private Product product;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "variant_id"), inverseJoinColumns = @JoinColumn(name = "option_value_id"))
    private List<OptionValue> optionValues;

    @OneToMany(mappedBy = "variant")
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "variant")
    private List<OrderItem> orderItems;
}
