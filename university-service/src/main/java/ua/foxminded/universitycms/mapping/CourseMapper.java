package ua.foxminded.universitycms.mapping;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.universitycms.dto.CourseDTO;
import ua.foxminded.universitycms.model.entity.Course;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CourseMapper {

  @Mapping(target = "teacherCount", expression = "java(course.getTeachers().size())")
  CourseDTO courseToCourseDTO(Course course);

  Course courseDTOToCourse(CourseDTO courseDTO);

}
