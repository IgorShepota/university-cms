package ua.foxminded.universitycms.dto.user.role;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ua.foxminded.universitycms.dto.user.UserResponseDTO;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class TeacherResponseDTO extends UserResponseDTO {

  public String getFullName() {
    return getFirstName() + " " + getLastName();
  }

}
