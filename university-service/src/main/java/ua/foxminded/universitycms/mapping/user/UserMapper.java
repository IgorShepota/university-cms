package ua.foxminded.universitycms.mapping.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.universitycms.dto.user.UserResponseDTO;
import ua.foxminded.universitycms.dto.user.UserRegistrationDTO;
import ua.foxminded.universitycms.model.entity.user.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(source = "role.name", target = "roleName")
  UserResponseDTO userToUserResponseDTO(User user);

  User userRegistrationDTOToUser(UserRegistrationDTO userRegistrationDTO);

}
