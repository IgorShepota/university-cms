package ua.foxminded.universitycms.model.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.foxminded.universitycms.model.entity.user.role.Role;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.UniversityUserData;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(exclude = "password")
@ToString(exclude = "password")
public class User {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(name = "email", nullable = false, unique = true)
  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Email should be valid")
  private String email;

  @Column(name = "password", nullable = false)
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$",
      message = "The password must contain a minimum of 8 characters, including one uppercase"
          + " letter, one lowercase letter, one number, and one special character")
  private String password;

  @Column(name = "first_name", nullable = false)
  @NotBlank(message = "First name cannot be blank")
  @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "First name should contain only letters")
  private String firstName;

  @Column(name = "last_name", nullable = false)
  @NotBlank(message = "Last name cannot be blank")
  @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Last name should contain only letters")
  private String lastName;

  @Column(name = "gender", nullable = false)
  @Pattern(regexp = "^(Male|Female)$", message = "Gender must be either Male or Female")
  private String gender;

  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  @OneToOne(mappedBy = "user")
  private UniversityUserData universityUserData;

}
