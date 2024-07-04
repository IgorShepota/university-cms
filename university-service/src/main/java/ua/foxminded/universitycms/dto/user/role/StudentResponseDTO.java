package ua.foxminded.universitycms.dto.user.role;

import javax.validation.constraints.Pattern;
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
public class StudentResponseDTO extends UserResponseDTO {

  @Pattern(regexp = "^FLA-\\d{3}$", message = "{group.name.pattern}")
  private String groupName;

}
