package ua.foxminded.universitycms.mapping.coursemanagement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.foxminded.universitycms.dto.coursemanagement.CourseAssignmentDTO;
import ua.foxminded.universitycms.dto.coursemanagement.CourseDTO;
import ua.foxminded.universitycms.entity.coursemanagement.Course;
import ua.foxminded.universitycms.entity.coursemanagement.CourseAssignment;

@Mapper
public interface CourseAssignmentMapper {

  CourseAssignmentMapper INSTANCE = Mappers.getMapper(CourseAssignmentMapper.class);

  CourseDTO courseToCourseDTO(Course course);

  Course courseDTOToCourse(CourseDTO courseDTO);

  @Mapping(source = "group.id", target = "groupId")
  @Mapping(source = "course.id", target = "courseId")
  @Mapping(source = "teacher.id", target = "teacherId")
  CourseAssignmentDTO courseAssignmentToCourseAssignmentDTO(CourseAssignment courseAssignment);

  @Mapping(source = "groupId", target = "group.id")
  @Mapping(source = "courseId", target = "course.id")
  @Mapping(source = "teacherId", target = "teacher.id")
  CourseAssignment courseAssignmentDTOToCourseAssignment(CourseAssignmentDTO courseAssignmentDTO);
}