package ua.foxminded.universitycms.mapping;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.universitycms.dto.CourseAssignmentDTO;
import ua.foxminded.universitycms.model.entity.CourseAssignment;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CourseAssignmentMapper {

  @Mapping(target = "courseName", expression = "java(courseAssignment.getCourse() != null ? courseAssignment.getCourse().getName() : null)")
  @Mapping(target = "groupName", expression = "java(courseAssignment.getGroup() != null ? courseAssignment.getGroup().getName() : null)")
  @Mapping(target = "teacherFullName", expression = "java(getTeacherFullName(courseAssignment))")
  CourseAssignmentDTO courseAssignmentToCourseAssignmentDTO(CourseAssignment courseAssignment);

  @Mapping(target = "lessons", ignore = true)
  CourseAssignment courseAssignmentDTOToCourseAssignment(CourseAssignmentDTO courseAssignmentDTO);

  default String getTeacherFullName(CourseAssignment courseAssignment) {
    if (courseAssignment.getTeacherData() == null
        || courseAssignment.getTeacherData().getUser() == null) {
      return null;
    }
    return courseAssignment.getTeacherData().getUser().getFirstName() + " " +
        courseAssignment.getTeacherData().getUser().getLastName();
  }

}
