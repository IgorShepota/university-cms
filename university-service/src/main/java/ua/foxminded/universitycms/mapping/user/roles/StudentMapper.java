package ua.foxminded.universitycms.mapping.user.roles;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.universitycms.dto.user.roles.StudentDTO;

@Mapper(componentModel = "spring")
public interface StudentMapper {

  @Mapping(source = "ownerGroup.name", target = "groupName")
  StudentDTO studentToStudentDTO(Student student);

  Student studentDTOToStudent(StudentDTO studentDTO);

}
