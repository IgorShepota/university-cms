package ua.foxminded.universitycms.mapping.coursemanagement;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.foxminded.universitycms.dto.coursemanagement.CourseDTO;
import ua.foxminded.universitycms.entity.coursemanagement.Course;

@Mapper
public interface CourseMapper {

  CourseAssignmentMapper INSTANCE = Mappers.getMapper(CourseAssignmentMapper.class);

  CourseDTO courseToCourseDTO(Course course);

  Course courseDTOToCourse(CourseDTO courseDTO);
}
