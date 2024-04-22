package ua.foxminded.universitycms.dto.user.role;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ua.foxminded.universitycms.dto.user.UserDTO;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class StudentDTO extends UserDTO {

  private String groupName;

}
