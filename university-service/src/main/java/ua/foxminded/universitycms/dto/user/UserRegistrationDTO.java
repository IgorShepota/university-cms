package ua.foxminded.universitycms.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationDTO {

  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "Password cannot be blank")
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
      message = "The password must contain a minimum of 8 characters, including one uppercase"
          + " letter, one lowercase letter, one number, and one special character")
  private String password;

  @NotBlank(message = "First name cannot be blank")
  @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "First name should contain only letters")
  private String firstName;

  @NotBlank(message = "Last name cannot be blank")
  @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Last name should contain only letters")
  private String lastName;

  @NotBlank(message = "Gender cannot be blank")
  @Pattern(regexp = "^(Male|Female)$", message = "Gender must be either Male or Female")
  private String gender;

}
