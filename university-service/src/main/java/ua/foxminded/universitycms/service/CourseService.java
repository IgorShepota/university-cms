package ua.foxminded.universitycms.service;

import java.util.List;
import java.util.Optional;
import ua.foxminded.universitycms.dto.CourseDTO;

public interface CourseService {

  void addCourse(CourseDTO courseDTO);

  Optional<CourseDTO> getCourseById(String id);

  List<CourseDTO> getAllCourses();

  List<CourseDTO> getAllCourses(Integer page, Integer itemsPerPage);

  void updateCourse(CourseDTO courseDTO);

  boolean deleteCourse(String id);

}
