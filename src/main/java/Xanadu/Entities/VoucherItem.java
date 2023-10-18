package Xanadu.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"voucher", "order"})
@ToString(callSuper = true, exclude = {"voucher", "order"})
public class VoucherItem extends EntityBasic {

    private Boolean status;

    @OneToOne(mappedBy = "voucherItem")
    private Order order;

    @ManyToOne
    private Voucher voucher;

    @ManyToOne
    private Customer customer;

}
