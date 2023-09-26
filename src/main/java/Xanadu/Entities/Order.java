package Xanadu.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "order_table")
public class Order extends EntityBasic{

    @Column(nullable = false)
    private Float totalPrice;

    @Column(nullable = false)
    private Float amount;

    @Column(nullable = false)
    private String fulfillmentStatus;

    @Column(nullable = false)
    private Float shippingFee;

    @Lob
    @Column(length = 10000)
    private String node;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private ShippingAddress shippingAddress;

    @OneToOne
    private VoucherItem voucherItem;

    @OneToMany(mappedBy = "order")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

}
