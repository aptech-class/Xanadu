package Xanadu.Entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"optionValues", "product"})
@ToString(callSuper = true, exclude = {"optionValues", "product"})
@Entity(name = "option_table")
public class Option extends EntityBasic {

    @NotBlank(message = "Name is required!")
    @Column(nullable = false)
    private String name;

    @NotEmpty(message = "Option value is not empty!")
    @OneToMany(mappedBy = "option")
    private List<OptionValue> optionValues;

    @ManyToOne
    private Product product;

}
