package Xanadu.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@ToString(callSuper = true,exclude = {"customer","orders"})
@EqualsAndHashCode(callSuper = true,exclude = {"customer","orders"})
@Data
public class ShippingAddress extends EntityBasic{
    @NotNull
    @NotBlank(message = "First name is required!")
    @Column(nullable = false)
    private String firstName;

    @NotNull
    @NotBlank(message = "Last name is required!")
    @Column(nullable = false)
    private String lastName;

    @NotNull
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
