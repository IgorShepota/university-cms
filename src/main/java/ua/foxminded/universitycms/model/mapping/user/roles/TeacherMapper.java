package ua.foxminded.universitycms.model.mapping.user.roles;

import org.mapstruct.Mapper;
import ua.foxminded.universitycms.model.dto.user.roles.TeacherDTO;
import ua.foxminded.universitycms.model.entity.user.roles.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

  TeacherDTO teacherToTeacherDTO(Teacher teacher);

  Teacher teacherDTOToTeacher(TeacherDTO teacherDTO);

}
