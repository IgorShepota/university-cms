package ua.foxminded.universitycms.mapping.user.roles;

import org.mapstruct.Mapper;
import ua.foxminded.universitycms.dto.user.roles.AdminDTO;

@Mapper(componentModel = "spring")
public interface AdminMapper {

  AdminDTO adminToAdminDTO(Admin admin);

  Admin adminDTOToAdmin(AdminDTO adminDTO);

}
