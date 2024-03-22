package ua.foxminded.universitycms.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.universitycms.dto.CourseAssignmentDTO;
import ua.foxminded.universitycms.model.entity.CourseAssignment;

@Mapper
public interface CourseAssignmentMapper {

  @Mapping(source = "group.id", target = "groupId")
  @Mapping(source = "course.id", target = "courseId")
  @Mapping(source = "teacher.id", target = "teacherId")
  CourseAssignmentDTO courseAssignmentToCourseAssignmentDTO(CourseAssignment courseAssignment);

  CourseAssignment courseAssignmentDTOToCourseAssignment(CourseAssignmentDTO courseAssignmentDTO);

}