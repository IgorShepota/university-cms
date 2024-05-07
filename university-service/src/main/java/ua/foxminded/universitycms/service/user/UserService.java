package ua.foxminded.universitycms.service.user;

import java.util.List;
import ua.foxminded.universitycms.dto.user.UserDTO;
import ua.foxminded.universitycms.dto.user.UserRegistrationDTO;
import ua.foxminded.universitycms.dto.user.role.StudentDTO;

public interface UserService {

  void registerUser(UserRegistrationDTO userRegistrationDTO);

  List<UserDTO> getAllUsersSorted(String sort, String order);

  List<StudentDTO> getAllStudents();

  List<UserDTO> getAllTeachers();

  List<UserDTO> getAllUsers();

  List<UserDTO> getAllUsers(Integer page, Integer itemsPerPage);

  void updateUserRole(String userId, String roleNName);

}
