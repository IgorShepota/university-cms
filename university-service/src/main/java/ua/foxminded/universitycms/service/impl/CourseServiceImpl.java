package ua.foxminded.universitycms.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.mapping.CourseMapper;
import ua.foxminded.universitycms.dto.CourseDTO;
import ua.foxminded.universitycms.model.entity.Course;
import ua.foxminded.universitycms.repository.CourseRepository;
import ua.foxminded.universitycms.service.CourseService;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepository;
  private final CourseMapper courseMapper;

  @Override
  @Transactional
  public void addCourse(CourseDTO courseDTO) {
    Course course = courseMapper.courseDTOToCourse(courseDTO);
    course = courseRepository.save(course);
    log.info("Course with id {} was successfully saved.", course.getId());
  }

  @Override
  public Optional<CourseDTO> getCourseById(String id) {
    log.info("Fetching course with id {}.", id);
    return courseRepository.findById(id).map(courseMapper::courseToCourseDTO);
  }

  @Override
  public List<CourseDTO> getAllCourses() {
    log.info("Fetching all courses.");
    return courseRepository.findAll().stream()
        .map(courseMapper::courseToCourseDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<CourseDTO> getAllCourses(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of courses with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);
    return courseRepository.findAll(pageable).getContent().stream()
        .map(courseMapper::courseToCourseDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void updateCourse(CourseDTO courseDTO) {
    log.info("Updating course: {}", courseDTO);
    Course course = courseMapper.courseDTOToCourse(courseDTO);
    courseRepository.save(course);
  }

  @Override
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
