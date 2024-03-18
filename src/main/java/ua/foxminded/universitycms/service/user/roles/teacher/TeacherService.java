package ua.foxminded.universitycms.service.user.roles.teacher;

import java.util.List;
import java.util.Optional;
import ua.foxminded.universitycms.model.dto.user.roles.TeacherDTO;

public interface TeacherService {

  void addTeacher(TeacherDTO teacherDTO);

  Optional<TeacherDTO> getTeacherById(String id);

  List<TeacherDTO> getAllTeachers();

  List<TeacherDTO> getAllTeachers(Integer page, Integer itemsPerPage);

  void updateTeacher(TeacherDTO teacherDTO);

  boolean deleteTeacher(String id);

}
