package ua.foxminded.universitycms.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.dto.CourseDTO;
import ua.foxminded.universitycms.mapping.CourseMapper;
import ua.foxminded.universitycms.model.entity.Course;
import ua.foxminded.universitycms.model.entity.CourseAssignment;
import ua.foxminded.universitycms.model.entity.CourseStatus;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.TeacherData;
import ua.foxminded.universitycms.repository.CourseAssignmentRepository;
import ua.foxminded.universitycms.repository.CourseRepository;
import ua.foxminded.universitycms.repository.user.universityuserdata.TeacherDataRepository;
import ua.foxminded.universitycms.service.CourseService;
import ua.foxminded.universitycms.service.exception.CourseAlreadyExistsException;
import ua.foxminded.universitycms.service.exception.CourseNotFoundException;
import ua.foxminded.universitycms.service.exception.InactiveCourseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepository;
  private final CourseMapper courseMapper;
  private final CourseAssignmentRepository courseAssignmentRepository;
  private final TeacherDataRepository teacherDataRepository;

  @Override
  @Transactional
  public void addCourse(CourseDTO courseDTO) {
    if (courseRepository.existsByNameIgnoreCase(courseDTO.getName())) {
      throw new CourseAlreadyExistsException(
          "Course with name '" + courseDTO.getName() + "' already exists");
    }
    if (courseRepository.existsByDescriptionIgnoreCase(courseDTO.getDescription())) {
      throw new CourseAlreadyExistsException(
          "Course with description '" + courseDTO.getDescription() + "' already exists");
    }

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
  public List<CourseDTO> getAllActiveCourses() {
    log.info("Fetching all active courses.");
    return courseRepository.findByStatus(CourseStatus.ACTIVE).stream()
        .map(courseMapper::courseToCourseDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void updateCourse(CourseDTO courseDTO) {
    log.info("Updating course: {}", courseDTO);

    Course course = courseRepository.findById(courseDTO.getId())
        .orElseThrow(() -> new CourseNotFoundException("Course not found"));

    if (!course.getStatus().equals(CourseStatus.ACTIVE)) {
      throw new InactiveCourseException("Cannot update an inactive course.");
    }

    course = courseMapper.courseDTOToCourse(courseDTO);
    courseRepository.save(course);
  }

  @Override
  @Transactional
  public void activateCourse(String id) {
    log.info("Activating course with id: {}", id);
    Course course = courseRepository.findById(id)
        .orElseThrow(() -> new CourseNotFoundException("Course not found"));

    if (course.getStatus().equals(CourseStatus.ACTIVE)) {
      log.warn("Course {} is already active.", id);
      return;
    }

    course.setStatus(CourseStatus.ACTIVE);
    courseRepository.save(course);
    log.info("Course {} activated successfully.", id);
  }

  @Override
  @Transactional
  public void deactivateCourse(String id) {
    log.info("Deactivating course with id: {}", id);

    Course course = courseRepository.findById(id)
        .orElseThrow(() -> new CourseNotFoundException("Course not found"));

    List<TeacherData> teachers = teacherDataRepository.findAll();
    for (TeacherData teacher : teachers) {
      teacher.getCourses().remove(course);
      teacherDataRepository.save(teacher);
    }

    List<CourseAssignment> assignments = courseAssignmentRepository.findByCourseId(id);
    for (CourseAssignment assignment : assignments) {
      assignment.setCourse(null);
      courseAssignmentRepository.save(assignment);
    }

    course.setStatus(CourseStatus.INACTIVE);
    courseRepository.save(course);
    log.info("Course {} deactivated successfully.", id);
  }

  @Override
  @Transactional
  public boolean deleteCourse(String id) {
    log.info("Deleting course with id: {}", id);

    Course course = courseRepository.findById(id)
        .orElseThrow(() -> new CourseNotFoundException("Course not found"));

    if (course.getStatus().equals(CourseStatus.INACTIVE)) {
      courseRepository.deleteById(id);
      log.info("Course {} deleted successfully.", id);
      return true;
    } else {
      log.error("Cannot delete an active course with id: {}", id);
      return false;
    }
  }

}
