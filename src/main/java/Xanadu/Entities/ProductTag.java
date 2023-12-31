package Xanadu.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@ToString(callSuper = true,exclude = {"products"})
@Data
@EqualsAndHashCode(callSuper = true,exclude = {"products"})
public class ProductTag extends  EntityBasic{

    @Column(nullable = false,unique = true)
    private String value;

    @Column(length = 10000)
    @Lob
    private String descriptions="";

    @ManyToMany(mappedBy = "productTags")
    private List<Product> products;
}
