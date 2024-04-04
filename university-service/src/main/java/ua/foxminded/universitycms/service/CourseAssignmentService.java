package ua.foxminded.universitycms.service;

import java.util.List;
import java.util.Optional;
import ua.foxminded.universitycms.dto.CourseAssignmentDTO;

public interface CourseAssignmentService {

  void addCourseAssignment(CourseAssignmentDTO courseAssignmentDTO);

  Optional<CourseAssignmentDTO> getCourseAssignmentById(String id);

  List<CourseAssignmentDTO> getAllCourseAssignments();

  List<CourseAssignmentDTO> getAllCourseAssignments(Integer page, Integer itemsPerPage);

  void updateCourseAssignment(CourseAssignmentDTO courseAssignmentDTO);

  boolean deleteCourseAssignment(String id);

}
