package Xanadu.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Review extends  EntityBasic{

    @Column(nullable = false,length = 10000)
    @Lob
    private String content;

    @Column(nullable = false)
    private Integer rating;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Product product;

}
