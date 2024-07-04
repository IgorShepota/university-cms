package ua.foxminded.universitycms.mapping;

import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ua.foxminded.universitycms.dto.GroupDTO;
import ua.foxminded.universitycms.mapping.user.role.StudentMapper;
import ua.foxminded.universitycms.model.entity.Group;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {
    StudentMapper.class, CourseAssignmentMapper.class})
public interface GroupMapper {

  @Mapping(source = "students", target = "students")
  @Mapping(source = "courseAssignments", target = "courseAssignments")
  @Mapping(target = "courseAssignmentsCount", ignore = true)
  @Mapping(target = "studentCount", ignore = true)
  GroupDTO groupToGroupDTO(Group group);

  Group groupDTOToGroup(GroupDTO groupDTO);

  @AfterMapping
  default void setCourseAssignmentsCount(Group group, @MappingTarget GroupDTO groupDTO) {
    if (group.getCourseAssignments() != null) {
      groupDTO.setCourseAssignmentsCount(group.getCourseAssignments().size());
    } else {
      groupDTO.setCourseAssignmentsCount(0);
    }
  }

  @AfterMapping
  default void setStudentCount(Group group, @MappingTarget GroupDTO groupDTO) {
    if (group.getStudents() != null) {
      groupDTO.setStudentCount(group.getStudents().size());
    } else {
      groupDTO.setStudentCount(0);
    }
  }

}
