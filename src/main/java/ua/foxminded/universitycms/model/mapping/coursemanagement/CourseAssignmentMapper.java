package ua.foxminded.universitycms.model.mapping.coursemanagement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.universitycms.model.dto.coursemanagement.CourseAssignmentDTO;
import ua.foxminded.universitycms.model.entity.coursemanagement.CourseAssignment;

@Mapper
public interface CourseAssignmentMapper {

  @Mapping(source = "group.id", target = "groupId")
  @Mapping(source = "course.id", target = "courseId")
  @Mapping(source = "teacher.id", target = "teacherId")
  CourseAssignmentDTO courseAssignmentToCourseAssignmentDTO(CourseAssignment courseAssignment);

  CourseAssignment courseAssignmentDTOToCourseAssignment(CourseAssignmentDTO courseAssignmentDTO);

}
