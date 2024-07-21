package ua.foxminded.universitycms.service.user;

import java.util.List;
import ua.foxminded.universitycms.dto.user.StudentSearchCriteria;
import ua.foxminded.universitycms.dto.user.UserRegistrationDTO;
import ua.foxminded.universitycms.dto.user.UserResponseDTO;
import ua.foxminded.universitycms.dto.user.role.StudentResponseDTO;
import ua.foxminded.universitycms.dto.user.role.TeacherResponseDTO;

public interface UserService {

  void registerUser(UserRegistrationDTO userRegistrationDTO);

  List<UserResponseDTO> getAllUsersSorted(String sort, String order);

  List<StudentResponseDTO> searchStudents(StudentSearchCriteria criteria, String sort,
      String order);

  List<TeacherResponseDTO> getAllTeachers();

  List<UserResponseDTO> getAllUsers();

  List<UserResponseDTO> getAllUsers(Integer page, Integer itemsPerPage);

  void updateUserRole(String userId, String roleNName);

}
