package Xanadu.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@ToString(callSuper = true,exclude = {"customer","orders"})
@EqualsAndHashCode(callSuper = true,exclude = {"customer","orders"})
@Data
public class ShippingAddress extends EntityBasic{
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phone;

    private String city;

    @Lob
    @Column(length =10000)
    private String detail;

    private String company;
    private Boolean isDefault = false ;
    private String country;
    private String latitude;
    private String longitude;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "shippingAddress")
    private List<Order> orders;
}
