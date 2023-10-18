package Xanadu.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(callSuper = true, exclude = {"order", "customer"})
@EqualsAndHashCode(callSuper = true, exclude = {"order", "customer"})
public class Transaction extends EntityBasic {

    private Float amount;

    private String Status;

    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionKind transactionKind;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Customer customer;


}
