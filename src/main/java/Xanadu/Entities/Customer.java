package Xanadu.Entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"customerTags", "shippingAddresses", "orders", "transactions", "reviews", "carts", "voucherItems"})
@ToString(callSuper = true, exclude = {"customerTags", "shippingAddresses", "orders", "transactions", "reviews", "carts", "voucherItems"})
@Table(indexes = {@Index(columnList = "username")})
public class Customer extends EntityBasic {

    @NotNull
    @NotBlank(message = "Username is required!")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Password is required!")
    @Length(min = 6, max = 50)
    @Column(nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @NotNull
    @NotBlank(message = "First name is required!")
    @Column(nullable = false)
    private String firstName;

    @NotNull
    @NotBlank(message = "Last name is required!")
    @Column(nullable = false)
    private String lastName;

    @Email
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

    @Valid
    @OneToMany(mappedBy = "customer")
    private List<ShippingAddress> shippingAddresses;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "customer")
    private List<Review> reviews;

    @OneToMany(mappedBy = "customer")
    private List<Cart> carts;

    @OneToMany(mappedBy = "customer")
    private List<VoucherItem> voucherItems;

    @Transient
    MultipartFile imageFile ;
}





