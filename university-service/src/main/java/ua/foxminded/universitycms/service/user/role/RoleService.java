package ua.foxminded.universitycms.service.user.role;

import java.util.List;
import java.util.Optional;
import ua.foxminded.universitycms.dto.user.role.RoleDTO;

public interface RoleService {

  void addRole(RoleDTO roleDTO);

  Optional<RoleDTO> getRoleById(String id);

  List<RoleDTO> getAllRoles();

  List<RoleDTO> getAllRoles(Integer page, Integer itemsPerPage);

  void updateRole(RoleDTO roleDTO);

  boolean deleteRole(String id);

}
