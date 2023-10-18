package Xanadu.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString(callSuper = true,exclude = {"voucherItems","discount"})
@EqualsAndHashCode(callSuper = true,exclude = {"voucherItems","discount"})
public class Voucher extends EntityBasic{

    private Integer inventory;

    @ManyToOne
    private Discount discount;

    @OneToMany(mappedBy = "voucher")
    private List<VoucherItem>  voucherItems;



}
