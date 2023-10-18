package Xanadu.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"products", "categories"})
@Entity
@ToString(callSuper = true, exclude = {"products", "categories"})
@Table(indexes = {@Index(columnList = "handle")})
public class Collection extends EntityBasic {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String handle;

    @Column(length = 10000)
    @Lob
    private String description;

    private Boolean status = true;

    @ManyToMany(mappedBy = "collections")
    private List<Product> products;

    @ManyToMany(mappedBy = "collections")
    private List<Category> categories;

}
