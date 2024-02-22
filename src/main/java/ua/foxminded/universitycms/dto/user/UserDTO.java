package ua.foxminded.universitycms.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(setterPrefix = "with")
public class UserDTO {

  private String id;
  private String email;
  private String firstName;
  private String lastName;
  private String gender;

}