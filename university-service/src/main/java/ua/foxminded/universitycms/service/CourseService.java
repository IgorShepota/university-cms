package ua.foxminded.universitycms.service;

import java.util.List;
import ua.foxminded.universitycms.dto.CourseDTO;

public interface CourseService {

  void addCourse(CourseDTO courseDTO);

  CourseDTO getCourseById(String id);

  List<CourseDTO> getAllCourses();

  List<CourseDTO> getAllCourses(Integer page, Integer itemsPerPage);

  List<CourseDTO> getAllActiveCourses();

  void updateCourse(CourseDTO courseDTO);

  void activateCourse(String id);

  void deactivateCourse(String id);

  boolean deleteCourse(String id);

}
