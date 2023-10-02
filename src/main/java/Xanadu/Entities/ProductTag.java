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
public class ProductTag extends  EntityBasic{

    @Column(nullable = false,unique = true)
    private String value;

    @Column(length = 10000)
    @Lob
    private String descriptions="";

    @ManyToMany(mappedBy = "productTags")
    private List<Product> products;
}
