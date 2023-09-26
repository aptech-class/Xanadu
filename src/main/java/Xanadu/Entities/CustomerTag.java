package Xanadu.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerTag extends EntityBasic{

    @Lob
    @Column(length = 10000)
    private String descriptions;

    @Column(nullable = false)
    private String value;

    @ManyToMany(mappedBy = "customerTags")
    private List<Customer> customers;
}
