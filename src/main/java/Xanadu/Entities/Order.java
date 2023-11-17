package Xanadu.Entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"customer", "shippingAddress", "voucherItem", "transactions", "orderItems"})
@ToString(callSuper = true, exclude = {"customer", "shippingAddress", "voucherItem", "transactions", "orderItems"})
@Entity(name = "order_table")
public class Order extends EntityBasic {

    @Column(nullable = false)
    private Float totalPrice;

    @Column(nullable = false)
    private Float amount;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FulfillmentStatus fulfillmentStatus;

    @Column(nullable = false)
    private Float shippingFee;

    @Lob
    @Column(length = 10000)
    private String note;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;


    private Boolean paymentStatus = false;

    @ManyToOne
    private Customer customer;

    @Valid
    @ManyToOne
    private ShippingAddress shippingAddress;

    @OneToOne
    private VoucherItem voucherItem;

    @OneToMany(mappedBy = "order")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

}
