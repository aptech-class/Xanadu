package Xanadu.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true,exclude = {"vouchers","products"})
@ToString(callSuper = true,exclude = {"vouchers","products"})
@Table(indexes = {@Index(columnList = "code")})
public class Discount extends  EntityBasic{
    @Column(nullable = false,unique = true)
    private String code;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @OneToMany(mappedBy = "discount")
    private List<Voucher> vouchers;

    @OneToMany(mappedBy = "discount")
    private List<Product> products;
}
