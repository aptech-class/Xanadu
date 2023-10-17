package Xanadu.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(indexes = {@Index(columnList = "username")})
public class Customer extends EntityBasic {

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String email;
    private String image;
    private String phone;
    private Boolean status;
    private Date lastLogin;
    private Boolean verifiedEmail;
    private Boolean emailMarketingConsent;
    private Boolean smsMarketingConsent;

    @Lob
    @Column(length = 10000)
    private String node;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "customer_tag_id"))
    private List<CustomerTag> customerTags;

    @OneToMany(mappedBy = "customer")
    private List<ShippingAddress> shippingAddresses;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "customer")
    private List<Review> reviews;
//
//    @OneToMany(mappedBy = "customer")
//    private List<Cart> carts;
//
//    @OneToMany(mappedBy = "customer")
//    private List<VoucherItem> voucherItems;
}





