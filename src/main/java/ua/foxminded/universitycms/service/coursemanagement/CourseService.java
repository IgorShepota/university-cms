package ua.foxminded.universitycms.service.coursemanagement;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.entity.coursemanagement.Course;
import ua.foxminded.universitycms.repository.coursemanagement.CourseRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

  private final CourseRepository courseRepository;

  @Transactional
  public void addCourse(Course course) {
    courseRepository.save(course);
    log.info("Course with id {} was successfully saved.", course.getId());
  }

  public Optional<Course> getCourseById(String id) {
    log.info("Fetching course with id {}.", id);
    return courseRepository.findById(id);
  }

  public List<Course> getAllCourses() {
    log.info("Fetching all courses.");
    return courseRepository.findAll();
  }

  public List<Course> getAllCourses(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of courses with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);

    return courseRepository.findAll(pageable).getContent();
  }

  @Transactional
  public void updateCourse(Course course) {
    log.info("Updating course: {}", course);
    courseRepository.save(course);
  }

  @Transactional
  public boolean deleteCourse(String id) {
    if (courseRepository.existsById(id)) {
      courseRepository.deleteById(id);
      log.info("Course with id {} was successfully deleted.", id);
      return true;
    } else {
      log.info("Course with id {} not found.", id);
      return false;
    }
  }

}
