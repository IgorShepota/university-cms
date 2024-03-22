package ua.foxminded.universitycms.service;

import java.util.List;
import java.util.Optional;
import ua.foxminded.universitycms.model.entity.CourseAssignment;

public interface CourseAssignmentService {

  void addCourseAssignment(CourseAssignment courseAssignment);

  Optional<CourseAssignment> getCourseAssignmentById(String id);

  List<CourseAssignment> getAllCourseAssignments();

  List<CourseAssignment> getAllCourseAssignments(Integer page, Integer itemsPerPage);

  void updateCourseAssignment(CourseAssignment courseAssignment);

  boolean deleteCourseAssignment(String id);

}
