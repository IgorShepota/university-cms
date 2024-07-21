package ua.foxminded.universitycms.mapping.user.role;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.universitycms.dto.user.role.TeacherResponseDTO;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.TeacherData;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

  @Mapping(source = "user.id", target = "id")
  @Mapping(source = "user.email", target = "email")
  @Mapping(source = "user.firstName", target = "firstName")
  @Mapping(source = "user.lastName", target = "lastName")
  @Mapping(source = "user.gender", target = "gender")
  @Mapping(source = "user.role.name", target = "roleName")
  @Mapping(source = "user.creationDateTime", target = "creationDateTime")
  TeacherResponseDTO mapToTeacherResponseDTO(TeacherData teacherData);

}
