package ua.foxminded.universitycms.dto.user.universityuserdata;

import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
public class StudentDataDTO extends UniversityUserDataDTO {

  @Pattern(regexp = "^FLA-\\d{3}$", message = "{group.name.pattern}")
  private String groupName;

}
