package ua.foxminded.universitycms.mapping.user.roles;

import org.mapstruct.Mapper;
import ua.foxminded.universitycms.dto.user.roles.AdminDTO;
import ua.foxminded.universitycms.model.entity.user.roles.Admin;

@Mapper(componentModel = "spring")
public interface AdminMapper {

  AdminDTO adminToAdminDTO(Admin admin);

  Admin adminDTOToAdmin(AdminDTO adminDTO);

}