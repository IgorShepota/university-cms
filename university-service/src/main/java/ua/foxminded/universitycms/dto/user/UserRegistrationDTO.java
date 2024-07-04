package ua.foxminded.universitycms.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.universitycms.model.entity.user.Gender;

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

  @NotNull(message = "{user.gender.required}")
  private Gender gender;

}
