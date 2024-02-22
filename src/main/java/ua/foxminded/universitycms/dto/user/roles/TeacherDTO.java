package ua.foxminded.universitycms.dto.user.roles;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ua.foxminded.universitycms.dto.user.UserDTO;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class TeacherDTO extends UserDTO {

  private List<String> courseIds;
  private List<String> courseAssignmentIds;

}
