package ua.foxminded.universitycms.dto.user.roles;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import ua.foxminded.universitycms.dto.user.UserDTO;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class AdminDTO extends UserDTO {

}
