package ua.foxminded.universitycms.mapping;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import ua.foxminded.universitycms.dto.CourseDTO;
import ua.foxminded.universitycms.model.entity.Course;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CourseMapper {

  CourseDTO courseToCourseDTO(Course course);

  Course courseDTOToCourse(CourseDTO courseDTO);

}
