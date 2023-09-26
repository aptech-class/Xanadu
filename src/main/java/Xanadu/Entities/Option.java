package Xanadu.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Option extends EntityBasic{

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "option")
    private List<OptionValue> optionValues;

    @ManyToOne
    private Product product;

}
