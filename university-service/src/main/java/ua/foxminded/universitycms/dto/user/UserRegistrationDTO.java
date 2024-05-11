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

  @NotBlank(message = "{email.required}")
  @Email(message = "{email.valid}")
  private String email;

  @NotBlank(message = "{password.required}")
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$",
      message = "{password.pattern}")
  private String password;

  @NotBlank(message = "{firstname.required}")
  @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "{firstname.pattern}")
  private String firstName;

  @NotBlank(message = "{lastname.required}")
  @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "{lastname.pattern}")
  private String lastName;

  @NotBlank(message = "{gender.required}")
  @Pattern(regexp = "^(Male|Female)$", message = "{gender.pattern}")
  private String gender;

}
