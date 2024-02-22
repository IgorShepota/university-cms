package ua.foxminded.universitycms.mapping.user.roles;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.foxminded.universitycms.dto.user.roles.StudentDTO;
import ua.foxminded.universitycms.entity.user.roles.Student;

@Mapper
public interface StudentMapper {

  StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

  @Mapping(source = "ownerGroup.id", target = "ownerGroupId")
  StudentDTO studentToStudentDTO(Student student);

  @Mapping(source = "ownerGroupId", target = "ownerGroup.id")
  Student studentDTOToStudent(StudentDTO studentDTO);

}
