package Xanadu.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(indexes = {@Index(columnList = "name",unique = true)})
public class Vendor extends EntityBasic{

    @Column(nullable = false)
    private String name;
    private String description;

    @OneToMany(mappedBy = "vendor")
    private List<Product> products;
}
