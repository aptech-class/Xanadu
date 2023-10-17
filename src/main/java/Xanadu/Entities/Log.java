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
public class Log extends EntityBasic{

    @Column(length = 10000)
    @Lob
    private String description;

    @Column(length = 10000)
    @Lob
    private String content;

    @ManyToOne
    private User user;
}
