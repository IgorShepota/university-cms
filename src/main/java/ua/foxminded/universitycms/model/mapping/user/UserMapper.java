package ua.foxminded.universitycms.model.mapping.user;

import org.mapstruct.Mapper;
import ua.foxminded.universitycms.model.dto.user.UserDTO;
import ua.foxminded.universitycms.model.entity.user.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserDTO userToUserDTO(User user);

  User userDTOToUser(UserDTO userDTO);

}
