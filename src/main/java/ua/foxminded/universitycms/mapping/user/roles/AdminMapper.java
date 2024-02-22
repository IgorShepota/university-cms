package ua.foxminded.universitycms.mapping.user.roles;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.foxminded.universitycms.dto.user.roles.AdminDTO;
import ua.foxminded.universitycms.entity.user.roles.Admin;

@Mapper
public interface AdminMapper {

  AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

  AdminDTO adminToAdminDTO(Admin admin);

  Admin adminDTOToAdmin(AdminDTO adminDTO);

}
