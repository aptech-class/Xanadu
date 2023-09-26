package Xanadu.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Category extends EntityBasic{

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false,length = 10000)
    @Lob
    private String description;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "category_id"),inverseJoinColumns = @JoinColumn(name = "collection_id"))
    private List<Collection> collections;
}
