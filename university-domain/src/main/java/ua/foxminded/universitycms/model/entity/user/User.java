package ua.foxminded.universitycms.model.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(setterPrefix = "with")
@Data
@EqualsAndHashCode(exclude = "password")
@ToString(exclude = "password")
public class User {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(name = "email", nullable = false, unique = true)
  @Email(message = "Email should be valid")
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "first_name", nullable = false)
  @NotBlank(message = "First name cannot be blank")
  private String firstName;

  @Column(name = "last_name", nullable = false)
  @NotBlank(message = "Last name cannot be blank")
  private String lastName;

  @Column(name = "gender", nullable = false)
  @Pattern(regexp = "^(Male|Female)$", message = "Gender must be either Male or Female")
  private String gender;

}
