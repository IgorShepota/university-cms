package ua.foxminded.universitycms.mapping.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.foxminded.universitycms.dto.user.UserDTO;
import ua.foxminded.universitycms.entity.user.User;

@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserDTO userToUserDTO(User user);

  User userDTOToUser(UserDTO userDTO);

}
