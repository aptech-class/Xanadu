package Xanadu.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(indexes = {@Index(columnList = "handle", unique = true)})
public class Product extends EntityBasic {

    @Column(nullable = false, length = 1000)
    private String title;
    private Integer rating = 5;
    private Boolean published = false;

    @Column(nullable = false,unique = true)
    private String handle;

    @Lob
    @Column(length = 10000)
    private String bodyHtml = "";

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "product_tag_id"))
    private List<ProductTag> productTags;

    @OneToMany(mappedBy = "product")
    private List<Image> images;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "collection_id"))
    private List<Collection> collections;

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

    @Transient
    List<MultipartFile> imageFiles;

}
