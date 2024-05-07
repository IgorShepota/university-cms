package ua.foxminded.universitycms.dto.user;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDTO {

  private String id;
  private String email;
  private String firstName;
  private String lastName;
  private String gender;
  private String roleName;
  private LocalDateTime creationDateTime;

}
