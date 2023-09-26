package Xanadu.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Vendor extends EntityBasic{

    @Column(nullable = false)
    private String name;
    private String description;

    @OneToMany(mappedBy = "vendor")
    private List<Product> products;
}
