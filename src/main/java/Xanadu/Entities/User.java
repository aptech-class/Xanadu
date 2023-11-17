package Xanadu.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Entity
@ToString(callSuper = true, exclude = {"logs"})
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"logs"})
@Table(indexes = {@Index(columnList = "username",unique = true)})
public class User extends EntityBasic {

    @NotBlank
    @Column(nullable = false)
    private String username;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String image = "/files/images/users/default.png";

    @NotBlank
    @Column(nullable = false)
    private String phone;

    private Boolean status = false;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Log> logs;

    @Transient
    @JsonIgnore
    MultipartFile imageFile;

    @Transient
    @JsonIgnore
    private String confirmPassword;
}





