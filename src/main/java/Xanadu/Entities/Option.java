package Xanadu.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"optionValues", "product"})
@ToString(callSuper = true, exclude = {"optionValues", "product"})
@Entity(name = "option_table")
public class Option extends EntityBasic {

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "option")
    private List<OptionValue> optionValues;

    @ManyToOne
    private Product product;

}
