package ua.foxminded.universitycms.model.mapping.user.roles;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.universitycms.model.dto.user.roles.StudentDTO;
import ua.foxminded.universitycms.model.entity.user.roles.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {

  @Mapping(source = "ownerGroup.name", target = "groupName")
  StudentDTO studentToStudentDTO(Student student);

  Student studentDTOToStudent(StudentDTO studentDTO);

}
