package ua.foxminded.universitycms.service.user;

import java.util.List;
import ua.foxminded.universitycms.dto.user.UserResponseDTO;
import ua.foxminded.universitycms.dto.user.UserRegistrationDTO;
import ua.foxminded.universitycms.dto.user.role.StudentResponseDTO;

public interface UserService {

  void registerUser(UserRegistrationDTO userRegistrationDTO);

  List<UserResponseDTO> getAllUsersSorted(String sort, String order);

  List<StudentResponseDTO> getAllStudentsSorted(String sortField, String sortOrder);

  List<UserResponseDTO> getAllTeachers();

  List<UserResponseDTO> getAllUsers();

  List<UserResponseDTO> getAllUsers(Integer page, Integer itemsPerPage);

  void updateUserRole(String userId, String roleNName);

}
