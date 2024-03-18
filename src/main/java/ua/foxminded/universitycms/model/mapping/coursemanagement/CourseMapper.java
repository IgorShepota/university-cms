package ua.foxminded.universitycms.model.mapping.coursemanagement;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import ua.foxminded.universitycms.model.dto.coursemanagement.CourseDTO;
import ua.foxminded.universitycms.model.entity.coursemanagement.Course;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CourseMapper {

  CourseDTO courseToCourseDTO(Course course);

  Course courseDTOToCourse(CourseDTO courseDTO);

}
