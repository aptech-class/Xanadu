package Xanadu.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Image extends  EntityBasic{

    @Column(nullable = false,length = 1000)
    private String src;

    private String alt;
    private String height;
    private String with;
    private Integer position;

    @ManyToOne
    private Product product;

    @OneToMany(mappedBy = "image")
    private List<Variant> variants;
}
