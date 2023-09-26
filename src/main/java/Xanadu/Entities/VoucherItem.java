package Xanadu.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class VoucherItem extends EntityBasic {

    private Boolean status;

    @OneToOne(mappedBy = "voucherItem")
    private Order order;

    @ManyToOne
    private Voucher voucher;

    @ManyToOne
    private Customer customer;

}
