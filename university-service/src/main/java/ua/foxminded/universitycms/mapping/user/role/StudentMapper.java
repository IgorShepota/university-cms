package ua.foxminded.universitycms.mapping.user.role;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.universitycms.dto.user.role.StudentResponseDTO;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.StudentData;

@Mapper(componentModel = "spring")
public interface StudentMapper {

  @Mapping(source = "user.id", target = "id")
  @Mapping(source = "user.email", target = "email")
  @Mapping(source = "user.firstName", target = "firstName")
  @Mapping(source = "user.lastName", target = "lastName")
  @Mapping(source = "user.gender", target = "gender")
  @Mapping(source = "user.role.name", target = "roleName")
  @Mapping(source = "ownerGroup.name", target = "groupName")
  @Mapping(source = "user.creationDateTime", target = "creationDateTime")
  StudentResponseDTO mapToStudentResponseDTO(StudentData studentData);

}
