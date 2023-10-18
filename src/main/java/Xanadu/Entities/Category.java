package Xanadu.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString(callSuper = true, exclude = {"collections"})
@EqualsAndHashCode(callSuper = true, exclude = {"collections"})
@Table(indexes = {@Index(columnList = "title")})
public class Category extends EntityBasic {

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private Boolean status;

    @Column(length = 10000)
    @Lob
    private String description;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "collection_id"))
    private List<Collection> collections;
}
