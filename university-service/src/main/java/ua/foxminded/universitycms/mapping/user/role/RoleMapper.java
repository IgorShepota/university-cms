package ua.foxminded.universitycms.mapping.user.role;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import ua.foxminded.universitycms.dto.user.role.RoleDTO;
import ua.foxminded.universitycms.model.entity.user.role.Role;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface RoleMapper {

  RoleDTO roleToRoleDTO(Role role);

  Role roleDTOToRole(RoleDTO roleDTO);

}
