package Xanadu.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Transaction extends EntityBasic{

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
