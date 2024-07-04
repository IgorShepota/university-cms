package ua.foxminded.universitycms.dto.user;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.foxminded.universitycms.model.entity.user.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserResponseDTO {

  private String id;
  private String email;
  private String firstName;
  private String lastName;
  private Gender gender;
  private String roleName;
  private LocalDateTime creationDateTime;

}
