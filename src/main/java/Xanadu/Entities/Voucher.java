package Xanadu.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Voucher extends EntityBasic{

    private Integer inventory;

    @ManyToOne
    private Discount discount;

    @OneToMany(mappedBy = "voucher")
    private List<VoucherItem>  voucherItems;



}
