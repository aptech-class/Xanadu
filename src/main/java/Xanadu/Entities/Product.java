package Xanadu.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends EntityBasic{

    @Column(nullable = false, length = 1000)
    private String title;
    private Integer rating = 5;
    private Boolean published = false;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name="product_id"),inverseJoinColumns = @JoinColumn(name = "product_tag_id"))
    private List<ProductTag> productTags;

    @OneToMany(mappedBy = "product")
    private List<Image> images;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "product_id"),inverseJoinColumns = @JoinColumn(name = "collection_id"))
    private  List<Collection> collections;

    @ManyToOne
    private Vendor vendor;

    @OneToMany(mappedBy = "product")
    private List<Variant> variants;

    @OneToMany(mappedBy = "product")
    private List<Option> options;

    @ManyToOne
    private Discount discount;

    @ManyToOne
    private ProductType productType;


}
