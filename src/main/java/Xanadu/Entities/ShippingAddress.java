package Xanadu.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Controller;

import java.util.List;

@Entity
@ToString(callSuper = true,exclude = {"customer","orders"})
@EqualsAndHashCode(callSuper = true,exclude = {"customer","orders"})
@Data
public class ShippingAddress extends EntityBasic{
    @NotBlank
    @Column(nullable = false)
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    private String lastName;

    @NotBlank
    @Column(nullable = false)
    private String phone;

    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String city;

    @NotBlank
    @Column(nullable = false)
    private String district;

    @NotBlank
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
