package ua.foxminded.universitycms.service.coursemanagement;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.entity.coursemanagement.CourseAssignment;
import ua.foxminded.universitycms.repository.coursemanagement.CourseAssignmentRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseAssignmentService {

  private final CourseAssignmentRepository courseAssignmentRepository;

  @Transactional
  public void addCourseAssignment(CourseAssignment courseAssignment) {
    courseAssignmentRepository.save(courseAssignment);
    log.info("CourseAssignment with id {} was successfully saved.", courseAssignment.getId());
  }

  public Optional<CourseAssignment> getCourseAssignmentById(String id) {
    log.info("Fetching CourseAssignment with id {}.", id);
    return courseAssignmentRepository.findById(id);
  }

  public List<CourseAssignment> getAllCourseAssignments() {
    log.info("Fetching all CourseAssignments.");
    return courseAssignmentRepository.findAll();
  }

  public List<CourseAssignment> getAllCourseAssignments(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of CourseAssignments with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);

    return courseAssignmentRepository.findAll(pageable).getContent();
  }

  @Transactional
  public void updateCourseAssignment(CourseAssignment courseAssignment) {
    log.info("Updating CourseAssignment: {}", courseAssignment);
    courseAssignmentRepository.save(courseAssignment);
  }

  @Transactional
  public boolean deleteCourseAssignment(String id) {
    if (courseAssignmentRepository.existsById(id)) {
      courseAssignmentRepository.deleteById(id);
      log.info("CourseAssignment with id {} was successfully deleted.", id);
      return true;
    } else {
      log.info("CourseAssignment with id {} not found.", id);
      return false;
    }
  }
}

