package Xanadu.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Collection extends  EntityBasic{

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String handle;

    @Column(nullable = false,length = 10000)
    @Lob
    private String description;

    @ManyToMany(mappedBy = "collections")
    private List<Product> products;

    @ManyToMany(mappedBy = "collections")
    private List<Category> categories;

}
