package Xanadu.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductType extends EntityBasic{

    private String title;

    @OneToMany(mappedBy = "productType")
    private List<Product> products;
}
