package ua.foxminded.universitycms.service.user;

import java.util.List;
import java.util.Optional;
import ua.foxminded.universitycms.model.dto.user.UserDTO;

public interface UserService {

  void addUser(UserDTO userDTO);

  Optional<UserDTO> getUserById(String id);

  List<UserDTO> getAllUsers();

  List<UserDTO> getAllUsers(Integer page, Integer itemsPerPage);

  void updateUser(UserDTO userDTO);

  boolean deleteUser(String id);

}
