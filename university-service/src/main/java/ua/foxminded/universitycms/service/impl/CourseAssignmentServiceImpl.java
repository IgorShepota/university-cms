package ua.foxminded.universitycms.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.dto.CourseAssignmentDTO;
import ua.foxminded.universitycms.mapping.CourseAssignmentMapper;
import ua.foxminded.universitycms.model.entity.Course;
import ua.foxminded.universitycms.model.entity.CourseAssignment;
import ua.foxminded.universitycms.model.entity.Group;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.TeacherData;
import ua.foxminded.universitycms.repository.CourseAssignmentRepository;
import ua.foxminded.universitycms.repository.CourseRepository;
import ua.foxminded.universitycms.repository.GroupRepository;
import ua.foxminded.universitycms.repository.user.universityuserdata.TeacherDataRepository;
import ua.foxminded.universitycms.service.CourseAssignmentService;
import ua.foxminded.universitycms.service.exception.CourseAssignmentAlreadyExistsException;
import ua.foxminded.universitycms.service.exception.CourseAssignmentNotFoundException;
import ua.foxminded.universitycms.service.exception.CourseNotFoundException;
import ua.foxminded.universitycms.service.exception.GroupNotFoundException;
import ua.foxminded.universitycms.service.exception.TeacherNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseAssignmentServiceImpl implements CourseAssignmentService {

  private final CourseAssignmentRepository courseAssignmentRepository;
  private final CourseAssignmentMapper courseAssignmentMapper;
  private final CourseRepository courseRepository;
  private final TeacherDataRepository teacherDataRepository;
  private final GroupRepository groupRepository;

  @Transactional
  public void addCourseAssignment(CourseAssignmentDTO courseAssignmentDTO) {
    Course course = courseRepository.findById(courseAssignmentDTO.getCourseId())
        .orElseThrow(() -> new CourseNotFoundException("Course not found"));

    TeacherData teacher = teacherDataRepository.findById(courseAssignmentDTO.getTeacherId())
        .orElseThrow(() -> new TeacherNotFoundException("Teacher not found"));

    Group group = null;
    if (courseAssignmentDTO.getGroupId() != null) {
      group = groupRepository.findById(courseAssignmentDTO.getGroupId())
          .orElseThrow(() -> new GroupNotFoundException("Group not found"));
    }

    if (courseAssignmentRepository.existsByCourseAndTeacherDataAndGroup(course, teacher, group)) {
      throw new CourseAssignmentAlreadyExistsException(
          "Course assignment with this combination already exists");
    }

    CourseAssignment courseAssignment = CourseAssignment.builder()
        .withCourse(course)
        .withTeacherData(teacher)
        .withGroup(group)
        .build();

    courseAssignmentRepository.save(courseAssignment);

    long count = courseAssignmentRepository.countByCourseAndTeacherData(course, teacher);

    if (count == 1) {
      teacher.getCourses().add(course);
      course.getTeachers().add(teacher);
      teacherDataRepository.save(teacher);
      courseRepository.save(course);
      log.info("Added new link between Course {} and Teacher {} in teachers_courses table.",
          course.getId(), teacher.getId());
    } else {
      log.info("Link between Course {} and Teacher {} already exists in teachers_courses table.",
          course.getId(), teacher.getId());
    }

    log.info("CourseAssignment with id {} was successfully saved.", courseAssignment.getId());
  }

  @Override
  public Optional<CourseAssignmentDTO> getCourseAssignmentById(String id) {
    log.info("Fetching CourseAssignment with id {}.", id);
    return courseAssignmentRepository.findById(id)
        .map(courseAssignmentMapper::courseAssignmentToCourseAssignmentDTO);
  }

  @Override
  public List<CourseAssignmentDTO> getAllCourseAssignments() {
    log.info("Fetching all CourseAssignments.");
    List<CourseAssignment> courseAssignments = courseAssignmentRepository.findAll();
    return courseAssignments.stream()
        .map(courseAssignmentMapper::courseAssignmentToCourseAssignmentDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<CourseAssignmentDTO> getAllCourseAssignments(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of CourseAssignments with {} items per page.", page, itemsPerPage);
    Pageable pageable = PageRequest.of(page - 1, itemsPerPage);

    return courseAssignmentRepository.findAll(pageable).stream()
        .map(courseAssignmentMapper::courseAssignmentToCourseAssignmentDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void updateCourseAssignment(CourseAssignmentDTO courseAssignmentDTO) {
    log.info("Updating CourseAssignment: {}", courseAssignmentDTO);
    CourseAssignment existingAssignment = courseAssignmentRepository.findById(
            courseAssignmentDTO.getId())
        .orElseThrow(() -> new CourseAssignmentNotFoundException("CourseAssignment not found"));

    Course newCourse = courseRepository.findById(courseAssignmentDTO.getCourseId())
        .orElseThrow(() -> new CourseNotFoundException("Course not found"));

    TeacherData newTeacher = teacherDataRepository.findById(courseAssignmentDTO.getTeacherId())
        .orElseThrow(() -> new TeacherNotFoundException("Teacher not found"));

    Group newGroup = null;
    if (courseAssignmentDTO.getGroupId() != null) {
      newGroup = groupRepository.findById(courseAssignmentDTO.getGroupId())
          .orElseThrow(() -> new GroupNotFoundException("Group not found"));
    }

    if (courseAssignmentRepository.existsByCourseAndTeacherDataAndGroupAndIdNot(newCourse,
        newTeacher, newGroup, existingAssignment.getId())) {
      throw new CourseAssignmentAlreadyExistsException(
          "Course assignment with this combination already exists");
    }

    Course oldCourse = existingAssignment.getCourse();
    TeacherData oldTeacher = existingAssignment.getTeacherData();

    if (!oldCourse.equals(newCourse) || !oldTeacher.equals(newTeacher)) {
      long oldCount = courseAssignmentRepository.countByCourseAndTeacherData(oldCourse, oldTeacher);
      if (oldCount == 1) {
        oldCourse.getTeachers().remove(oldTeacher);
        oldTeacher.getCourses().remove(oldCourse);
        log.info("Removed link between Course {} and Teacher {} in teachers_courses table.",
            oldCourse.getId(), oldTeacher.getId());
      }

      if (!courseAssignmentRepository.existsByCourseAndTeacherData(newCourse, newTeacher)) {
        newTeacher.getCourses().add(newCourse);
        newCourse.getTeachers().add(newTeacher);
        log.info("Added link between Course {} and Teacher {} in teachers_courses table.",
            newCourse.getId(), newTeacher.getId());
      }
    }

    existingAssignment.setCourse(newCourse);
    existingAssignment.setTeacherData(newTeacher);
    existingAssignment.setGroup(newGroup);

    courseAssignmentRepository.save(existingAssignment);
    teacherDataRepository.save(newTeacher);
    courseRepository.save(newCourse);

    if (!oldCourse.equals(newCourse)) {
      courseRepository.save(oldCourse);
    }
    if (!oldTeacher.equals(newTeacher)) {
      teacherDataRepository.save(oldTeacher);
    }

    log.info("CourseAssignment with id {} was successfully updated.", existingAssignment.getId());
  }

  @Override
  @Transactional
  public boolean deleteCourseAssignment(String id) {
    return courseAssignmentRepository.findById(id).map(assignment -> {
      Course course = assignment.getCourse();
      TeacherData teacher = assignment.getTeacherData();
      Group group = assignment.getGroup();

      if (course != null && teacher != null) {
        long count = courseAssignmentRepository.countByCourseAndTeacherData(course, teacher);
        if (count == 1) {
          course.getTeachers().remove(teacher);
          teacher.getCourses().remove(course);
          courseRepository.save(course);
          teacherDataRepository.save(teacher);
          log.info("Removed link between Course {} and Teacher {} in teachers_courses table.",
              course.getId(), teacher.getId());
        } else {
          log.info(
              "Link between Course {} and Teacher {} in teachers_courses table was not removed due to existing CourseAssignments.",
              course.getId(), teacher.getId());
        }
      }

      if (course != null) {
        course.getCourseAssignments().remove(assignment);
      }
      if (teacher != null) {
        teacher.getCourseAssignments().remove(assignment);
      }
      if (group != null) {
        group.getCourseAssignments().remove(assignment);
      }

      courseAssignmentRepository.delete(assignment);

      log.info("CourseAssignment with id {} was successfully deleted.", id);
      return true;
    }).orElseGet(() -> {
      log.info("CourseAssignment with id {} not found.", id);
      return false;
    });
  }

}
