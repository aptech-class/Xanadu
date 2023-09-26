package Xanadu.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class OptionValue extends  EntityBasic{

    @Column(nullable = false)
    private String value;

    @ManyToOne
    private Option option;

    @ManyToMany(mappedBy = "optionValues")
    private List<Variant> variants;
}
