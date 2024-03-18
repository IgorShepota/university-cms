package ua.foxminded.universitycms.service.user.roles.student;

import java.util.List;
import java.util.Optional;
import ua.foxminded.universitycms.model.dto.user.roles.StudentDTO;

public interface StudentService {

  void addStudent(StudentDTO studentDTO);

  Optional<StudentDTO> getStudentById(String id);

  List<StudentDTO> getAllStudents();

  List<StudentDTO> getAllStudents(Integer page, Integer itemsPerPage);

  void updateStudent(StudentDTO studentDTO);

  boolean deleteStudent(String id);

}
