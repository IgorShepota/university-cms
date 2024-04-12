package ua.foxminded.universitycms.mapping.user.roles;

import org.mapstruct.Mapper;
import ua.foxminded.universitycms.dto.user.roles.TeacherDTO;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

  TeacherDTO teacherToTeacherDTO(Teacher teacher);

  Teacher teacherDTOToTeacher(TeacherDTO teacherDTO);

}
