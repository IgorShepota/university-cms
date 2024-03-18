package ua.foxminded.universitycms.service.coursemanagement.course;

import java.util.List;
import java.util.Optional;
import ua.foxminded.universitycms.model.dto.coursemanagement.CourseDTO;

public interface CourseService {

  void addCourse(CourseDTO courseDTO);

  Optional<CourseDTO> getCourseById(String id);

  List<CourseDTO> getAllCourses();

  List<CourseDTO> getAllCourses(Integer page, Integer itemsPerPage);

  void updateCourse(CourseDTO courseDTO);

  boolean deleteCourse(String id);

}
