package ua.foxminded.universitycms.dto.user.roles;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ua.foxminded.universitycms.dto.user.UserDTO;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class StudentDTO extends UserDTO {

  private String ownerGroupId;

  public StudentDTO() {
    super();
  }

  @Builder(builderMethodName = "studentBuilder")
  public StudentDTO(String id, String email, String firstName, String lastName, String gender, String ownerGroupId) {
    super(id, email, firstName, lastName, gender);
    this.ownerGroupId = ownerGroupId;
  }
}