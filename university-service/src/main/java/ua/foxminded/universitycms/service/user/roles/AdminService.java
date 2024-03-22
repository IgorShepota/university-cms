package ua.foxminded.universitycms.service.user.roles;

import java.util.List;
import java.util.Optional;
import ua.foxminded.universitycms.dto.user.roles.AdminDTO;

public interface AdminService {

  void addAdmin(AdminDTO adminDTO);

  Optional<AdminDTO> getAdminById(String id);

  List<AdminDTO> getAllAdmins();

  List<AdminDTO> getAllAdmins(Integer page, Integer itemsPerPage);

  void updateAdmin(AdminDTO adminDTO);

  boolean deleteAdmin(String id);

}
