package Xanadu.Entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Entity
@Data
@ToString(callSuper = true, exclude = {"images", "options", "variants", "productTags", "collections", "reviews", "vendor", "discount", "productType"})
@EqualsAndHashCode(callSuper = true, exclude = {"images", "options", "variants", "productTags", "collections", "reviews", "vendor", "discount", "productType"})
@Table(indexes = {@Index(columnList = "handle", unique = true)})
public class Product extends EntityBasic {

    @NotBlank(message = "Title is required!")
    @NotNull(message = "Title is not null!")
    @Column(nullable = false, length = 1000)
    private String title;
    private Integer rating = 5;
    private Boolean published = false;

    @NotNull(message = "Handle is not null!")
    @NotBlank(message = "Handle is required!")
    @Pattern(regexp = "[^#%{}\\\\^~\\[\\]`].*", message = "Handle cannot contain character: #%{}^~[]\\`")
    @Column(nullable = false, unique = true)
    private String handle;

    @Lob
    @Column(length = 10000)
    @NotBlank(message = "BodyHtml is required!")
    @NotNull(message = "BodyHtml is not null!")
    private String bodyHtml = "";

    @NotEmpty(message = "Product Tags is not empty!")
    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "product_tag_id"))
    private List<ProductTag> productTags;

    @NotEmpty(message = "Images is not empty!")
    @OneToMany(mappedBy = "product")
    private List<Image> images;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @NotEmpty(message = "Collections is not empty!")
    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "collection_id"))
    private List<Collection> collections;

    @NotNull(message = "Vendor is not null!")
    @ManyToOne
    private Vendor vendor;

    @OneToMany(mappedBy = "product")
    private List<Variant> variants;

    @Valid
    @NotEmpty(message = "Options is not empty!")
    @OneToMany(mappedBy = "product")
    private List<Option> options;

    @ManyToOne
    private Discount discount;

    @NotNull
    @ManyToOne
    private ProductType productType;

    @Transient
    List<MultipartFile> imageFiles;

}
