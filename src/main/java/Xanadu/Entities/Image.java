package Xanadu.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true,exclude = {"product","variants"})
@Entity
@EqualsAndHashCode(callSuper = true,exclude = {"product","variants"})
public class Image extends  EntityBasic{

    @Column(nullable = false,length = 1000,unique = true)
    private String src;

    private String alt;
    private String height;
    private String width;
    private Integer position;

    @ManyToOne
    private Product product;

    @OneToMany(mappedBy = "image")
    private List<Variant> variants;
}
