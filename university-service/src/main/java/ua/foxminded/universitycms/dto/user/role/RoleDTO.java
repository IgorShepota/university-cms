package ua.foxminded.universitycms.dto.user.role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDTO {

  private String id;

  @NotBlank(message = "{role.name.required}")
  @Pattern(regexp = "^[A-Z_]+$", message = "{role.name.pattern}")
  private String name;

}
