package Xanadu.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true,exclude = {"customers"})
@ToString(callSuper = true,exclude = {"customers"})
@Table(indexes = {@Index(columnList = "value",unique = true)})
public class CustomerTag extends EntityBasic{

    @Lob
    @Column(length = 10000)
    private String descriptions;

    @Column(nullable = false)
    private String value;

    @ManyToMany(mappedBy = "customerTags")
    private List<Customer> customers;
}
