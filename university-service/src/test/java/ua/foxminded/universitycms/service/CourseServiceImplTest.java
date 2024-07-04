package ua.foxminded.universitycms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.foxminded.universitycms.dto.CourseDTO;
import ua.foxminded.universitycms.mapping.CourseMapper;
import ua.foxminded.universitycms.model.entity.Course;
import ua.foxminded.universitycms.model.entity.CourseAssignment;
import ua.foxminded.universitycms.model.entity.CourseStatus;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.TeacherData;
import ua.foxminded.universitycms.repository.CourseAssignmentRepository;
import ua.foxminded.universitycms.repository.CourseRepository;
import ua.foxminded.universitycms.repository.user.universityuserdata.TeacherDataRepository;
import ua.foxminded.universitycms.service.exception.CourseAlreadyExistsException;
import ua.foxminded.universitycms.service.exception.InactiveCourseException;
import ua.foxminded.universitycms.service.impl.CourseServiceImpl;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

  @Mock
  private CourseRepository courseRepository;

  @Mock
  private TeacherDataRepository teacherDataRepository;

  @Mock
  private CourseAssignmentRepository courseAssignmentRepository;

  @Mock
  private CourseMapper courseMapper;

  @InjectMocks
  private CourseServiceImpl courseService;

  @Test
  void addCourseShouldWorkCorrectlyIfCourseEntityDTOCorrect() {
    CourseDTO courseDTO = new CourseDTO();
    courseDTO.setId("some-id");
    Course course = new Course();
    course.setId(courseDTO.getId());
    when(courseMapper.courseDTOToCourse(courseDTO)).thenReturn(course);
    when(courseRepository.save(any(Course.class))).thenReturn(course);

    courseService.addCourse(courseDTO);

    verify(courseMapper).courseDTOToCourse(courseDTO);
    verify(courseRepository).save(course);
  }

  @Test
  void addCourseShouldThrowExceptionWhenNameExists() {
    CourseDTO courseDTO = new CourseDTO();
    courseDTO.setName("Existing Course");
    courseDTO.setDescription("New Description");

    when(courseRepository.existsByNameIgnoreCase(courseDTO.getName())).thenReturn(true);

    assertThatThrownBy(() -> courseService.addCourse(courseDTO))
        .isInstanceOf(CourseAlreadyExistsException.class)
        .hasMessage("Course with name '" + courseDTO.getName() + "' already exists");

    verify(courseRepository, never()).save(any(Course.class));
  }

  @Test
  void addCourseShouldThrowExceptionWhenDescriptionExists() {
    CourseDTO courseDTO = new CourseDTO();
    courseDTO.setName("New Course");
    courseDTO.setDescription("Existing Description");

    when(courseRepository.existsByNameIgnoreCase(courseDTO.getName())).thenReturn(false);
    when(courseRepository.existsByDescriptionIgnoreCase(courseDTO.getDescription())).thenReturn(
        true);

    assertThatThrownBy(() -> courseService.addCourse(courseDTO))
        .isInstanceOf(CourseAlreadyExistsException.class)
        .hasMessage("Course with description '" + courseDTO.getDescription() + "' already exists");

    verify(courseRepository, never()).save(any(Course.class));
  }

  @Test
  void getCourseByIdShouldReturnCourseDTOWhenCourseExists() {
    String id = "test-id";
    Course course = new Course();
    course.setId(id);
    CourseDTO expectedDTO = new CourseDTO();
    expectedDTO.setId(id);

    when(courseRepository.findById(id)).thenReturn(Optional.of(course));
    when(courseMapper.courseToCourseDTO(course)).thenReturn(expectedDTO);

    Optional<CourseDTO> result = courseService.getCourseById(id);

    assertThat(result).isPresent()
        .contains(expectedDTO);
  }

  @Test
  void getCourseByIdShouldReturnEmptyOptionalWhenCourseDoesNotExist() {
    String id = "non-existing-id";
    when(courseRepository.findById(id)).thenReturn(Optional.empty());

    Optional<CourseDTO> result = courseService.getCourseById(id);

    assertThat(result).isNotPresent();
  }

  @Test
  void getAllCoursesShouldReturnListOfCourseDTOsWhenCoursesExist() {
    List<Course> courses = Arrays.asList(new Course(), new Course());
    when(courseRepository.findAll()).thenReturn(courses);

    CourseDTO dto1 = new CourseDTO();
    CourseDTO dto2 = new CourseDTO();
    when(courseMapper.courseToCourseDTO(any(Course.class))).thenReturn(dto1).thenReturn(dto2);

    List<CourseDTO> result = courseService.getAllCourses();

    assertThat(result).hasSize(2)
        .containsExactly(dto1, dto2);
  }

  @Test
  void getAllCoursesShouldReturnEmptyListWhenNoCoursesExist() {
    when(courseRepository.findAll()).thenReturn(Collections.emptyList());

    List<CourseDTO> result = courseService.getAllCourses();

    assertThat(result).isEmpty();
  }

  @Test
  void getAllCoursesWithPaginationShouldReturnListOfCourseDTOsWhenCoursesExist() {
    int page = 1;
    int itemsPerPage = 2;
    Pageable pageable = PageRequest.of(0, itemsPerPage);
    List<Course> courses = Arrays.asList(new Course(), new Course());
    Page<Course> pagedResponse = new PageImpl<>(courses);

    when(courseRepository.findAll(pageable)).thenReturn(pagedResponse);
    CourseDTO dto1 = new CourseDTO();
    CourseDTO dto2 = new CourseDTO();
    when(courseMapper.courseToCourseDTO(any(Course.class))).thenReturn(dto1).thenReturn(dto2);

    List<CourseDTO> result = courseService.getAllCourses(page, itemsPerPage);

    assertThat(result).hasSize(2)
        .containsExactly(dto1, dto2);
  }

  @Test
  void getAllActiveCoursesShouldReturnListOfActiveCourseDTOs() {
    Course activeCourse1 = new Course();
    activeCourse1.setId("1");
    activeCourse1.setName("Course 1");
    activeCourse1.setStatus(CourseStatus.ACTIVE);

    Course activeCourse2 = new Course();
    activeCourse2.setId("2");
    activeCourse2.setName("Course 2");
    activeCourse2.setStatus(CourseStatus.ACTIVE);

    CourseDTO courseDTO1 = new CourseDTO();
    courseDTO1.setId("1");
    courseDTO1.setName("Course 1");

    CourseDTO courseDTO2 = new CourseDTO();
    courseDTO2.setId("2");
    courseDTO2.setName("Course 2");

    when(courseRepository.findByStatus(CourseStatus.ACTIVE)).thenReturn(
        Arrays.asList(activeCourse1, activeCourse2));
    when(courseMapper.courseToCourseDTO(activeCourse1)).thenReturn(courseDTO1);
    when(courseMapper.courseToCourseDTO(activeCourse2)).thenReturn(courseDTO2);

    List<CourseDTO> result = courseService.getAllActiveCourses();

    assertThat(result).hasSize(2);
    assertThat(result).extracting(CourseDTO::getName).containsExactly("Course 1", "Course 2");

    verify(courseRepository, times(1)).findByStatus(CourseStatus.ACTIVE);
    verify(courseMapper, times(1)).courseToCourseDTO(activeCourse1);
    verify(courseMapper, times(1)).courseToCourseDTO(activeCourse2);
  }

  @Test
  void getAllActiveCoursesShouldReturnEmptyListWhenNoActiveCourses() {
    when(courseRepository.findByStatus(CourseStatus.ACTIVE)).thenReturn(Collections.emptyList());

    List<CourseDTO> result = courseService.getAllActiveCourses();

    verify(courseRepository).findByStatus(CourseStatus.ACTIVE);
    verify(courseMapper, never()).courseToCourseDTO(any());

    assertThat(result).isEmpty();
  }

  @Test
  void updateCourseShouldCorrectlyMapAndSaveCourse() {
    CourseDTO courseDTO = new CourseDTO();
    courseDTO.setId("course-id");
    Course course = new Course();
    Course existingCourse = new Course();
    existingCourse.setStatus(CourseStatus.ACTIVE);

    when(courseRepository.findById(courseDTO.getId())).thenReturn(Optional.of(existingCourse));
    when(courseMapper.courseDTOToCourse(courseDTO)).thenReturn(course);

    courseService.updateCourse(courseDTO);

    verify(courseRepository).findById(courseDTO.getId());
    verify(courseMapper).courseDTOToCourse(courseDTO);
    verify(courseRepository).save(course);
  }

  @Test
  void updateCourseShouldThrowExceptionWhenCourseIsInactive() {
    CourseDTO courseDTO = new CourseDTO();
    courseDTO.setId("course-id");
    Course existingCourse = new Course();
    existingCourse.setStatus(CourseStatus.INACTIVE);

    when(courseRepository.findById(courseDTO.getId())).thenReturn(Optional.of(existingCourse));

    assertThatThrownBy(() -> courseService.updateCourse(courseDTO))
        .isInstanceOf(InactiveCourseException.class)
        .hasMessage("Cannot update an inactive course.");

    verify(courseRepository).findById(courseDTO.getId());
    verify(courseMapper, never()).courseDTOToCourse(any());
    verify(courseRepository, never()).save(any());
  }

  @Test
  void updateCourseShouldThrowExceptionWhenCourseNotFound() {
    String courseId = "non-existent-id";
    CourseDTO courseDTO = new CourseDTO();
    courseDTO.setId(courseId);

    when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> courseService.updateCourse(courseDTO))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("Course not found");

    verify(courseRepository, times(1)).findById(courseId);
    verify(courseMapper, never()).courseDTOToCourse(any(CourseDTO.class));
    verify(courseRepository, never()).save(any(Course.class));
  }

  @Test
  void activateCourseShouldSetCourseStatusToActive() {
    String courseId = "1";
    Course course = new Course();
    course.setId(courseId);
    course.setStatus(CourseStatus.INACTIVE);

    when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

    courseService.activateCourse(courseId);

    assertThat(course.getStatus()).isEqualTo(CourseStatus.ACTIVE);
    verify(courseRepository, times(1)).findById(courseId);
    verify(courseRepository, times(1)).save(course);
  }

  @Test
  void activateCourseShouldStopExecutionIfStatusAlreadyActive() {
    String courseId = "1";
    Course course = new Course();
    course.setId(courseId);
    course.setStatus(CourseStatus.ACTIVE);

    when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

    courseService.activateCourse(courseId);

    verify(courseRepository, never()).save(any(Course.class));
    assertThat(course.getStatus()).isEqualTo(CourseStatus.ACTIVE);
  }

  @Test
  void activateCourseShouldThrowExceptionWhenCourseNotFound() {
    String courseId = "1";
    when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> courseService.activateCourse(courseId))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("Course not found");

    verify(courseRepository, times(1)).findById(courseId);
    verify(courseRepository, never()).save(any(Course.class));
  }

  @Test
  void deactivateCourseShouldSetCourseStatusToInactiveAndRemoveAssociations() {
    String courseId = "1";
    Course course = new Course();
    course.setId(courseId);
    course.setStatus(CourseStatus.ACTIVE);

    TeacherData teacher = new TeacherData();
    teacher.setId("teacher1");
    teacher.setCourses(new ArrayList<>(Collections.singletonList(course)));

    CourseAssignment assignment = new CourseAssignment();
    assignment.setId("assignment1");
    assignment.setCourse(course);

    when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
    when(teacherDataRepository.findAll()).thenReturn(Collections.singletonList(teacher));
    when(courseAssignmentRepository.findByCourseId(courseId)).thenReturn(
        Collections.singletonList(assignment));

    courseService.deactivateCourse(courseId);

    assertThat(course.getStatus()).isEqualTo(CourseStatus.INACTIVE);
    assertThat(teacher.getCourses()).doesNotContain(course);
    assertThat(assignment.getCourse()).isNull();

    verify(courseRepository, times(1)).findById(courseId);
    verify(teacherDataRepository, times(1)).findAll();
    verify(courseAssignmentRepository, times(1)).findByCourseId(courseId);
    verify(teacherDataRepository, times(1)).save(teacher);
    verify(courseAssignmentRepository, times(1)).save(assignment);
    verify(courseRepository, times(1)).save(course);
  }

  @Test
  void deactivateCourseShouldHandleNoTeachersAndAssignments() {
    String courseId = "1";
    Course course = new Course();
    course.setId(courseId);
    course.setStatus(CourseStatus.ACTIVE);

    when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
    when(teacherDataRepository.findAll()).thenReturn(Collections.emptyList());
    when(courseAssignmentRepository.findByCourseId(courseId)).thenReturn(Collections.emptyList());

    courseService.deactivateCourse(courseId);

    assertThat(course.getStatus()).isEqualTo(CourseStatus.INACTIVE);

    verify(courseRepository, times(1)).findById(courseId);
    verify(teacherDataRepository, times(1)).findAll();
    verify(courseAssignmentRepository, times(1)).findByCourseId(courseId);
    verify(teacherDataRepository, never()).save(any(TeacherData.class));
    verify(courseAssignmentRepository, never()).save(any(CourseAssignment.class));
    verify(courseRepository, times(1)).save(course);
  }

  @Test
  void deactivateCourseShouldThrowExceptionWhenCourseNotFound() {
    String courseId = "1";

    when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> courseService.deactivateCourse(courseId))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("Course not found");

    verify(courseRepository, times(1)).findById(courseId);
    verify(teacherDataRepository, never()).findAll();
    verify(courseAssignmentRepository, never()).findByCourseId(courseId);
    verify(courseRepository, never()).save(any(Course.class));
  }

  @Test
  void deleteCourseShouldReturnTrueWhenCourseIsInactive() {
    String id = "course-id";
    Course course = new Course();
    course.setId(id);
    course.setStatus(CourseStatus.INACTIVE);

    when(courseRepository.findById(id)).thenReturn(Optional.of(course));

    boolean result = courseService.deleteCourse(id);

    verify(courseRepository).findById(id);
    verify(courseRepository).deleteById(id);
    assertThat(result).isTrue();
  }

  @Test
  void deleteCourseShouldReturnFalseWhenCourseIsActive() {
    String id = "course-id";
    Course course = new Course();
    course.setId(id);
    course.setStatus(CourseStatus.ACTIVE);

    when(courseRepository.findById(id)).thenReturn(Optional.of(course));

    boolean result = courseService.deleteCourse(id);

    verify(courseRepository).findById(id);
    verify(courseRepository, never()).deleteById(id);
    assertThat(result).isFalse();
  }

  @Test
  void deleteCourseShouldThrowExceptionWhenCourseNotFound() {
    String id = "non-existing-id";

    when(courseRepository.findById(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> courseService.deleteCourse(id))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("Course not found");

    verify(courseRepository).findById(id);
    verify(courseRepository, never()).deleteById(anyString());
  }

}
