package ua.foxminded.universitycms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import ua.foxminded.universitycms.repository.CourseRepository;
import ua.foxminded.universitycms.service.impl.CourseServiceImpl;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

  @Mock
  private CourseRepository courseRepository;

  @Mock
  private CourseMapper courseMapper;

  @InjectMocks
  private CourseServiceImpl courseServiceImpl;


  @Test
  void addCourseShouldWorkCorrectlyIfCourseEntityDTOCorrect() {
    CourseDTO courseDTO = new CourseDTO();
    courseDTO.setId("some-id");
    Course course = new Course();
    course.setId(courseDTO.getId());
    when(courseMapper.courseDTOToCourse(courseDTO)).thenReturn(course);
    when(courseRepository.save(any(Course.class))).thenReturn(course);

    courseServiceImpl.addCourse(courseDTO);

    verify(courseMapper).courseDTOToCourse(courseDTO);
    verify(courseRepository).save(course);
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

    Optional<CourseDTO> result = courseServiceImpl.getCourseById(id);

    assertThat(result).isPresent()
        .contains(expectedDTO);
  }

  @Test
  void getCourseByIdShouldReturnEmptyOptionalWhenCourseDoesNotExist() {
    String id = "non-existing-id";
    when(courseRepository.findById(id)).thenReturn(Optional.empty());

    Optional<CourseDTO> result = courseServiceImpl.getCourseById(id);

    assertThat(result).isNotPresent();
  }

  @Test
  void getAllCoursesShouldReturnListOfCourseDTOsWhenCoursesExist() {
    List<Course> courses = Arrays.asList(new Course(), new Course());
    when(courseRepository.findAll()).thenReturn(courses);

    CourseDTO dto1 = new CourseDTO();
    CourseDTO dto2 = new CourseDTO();
    when(courseMapper.courseToCourseDTO(any(Course.class))).thenReturn(dto1).thenReturn(dto2);

    List<CourseDTO> result = courseServiceImpl.getAllCourses();

    assertThat(result).hasSize(2)
        .containsExactly(dto1, dto2);
  }

  @Test
  void getAllCoursesShouldReturnEmptyListWhenNoCoursesExist() {
    when(courseRepository.findAll()).thenReturn(Collections.emptyList());

    List<CourseDTO> result = courseServiceImpl.getAllCourses();

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

    List<CourseDTO> result = courseServiceImpl.getAllCourses(page, itemsPerPage);

    assertThat(result).hasSize(2)
        .containsExactly(dto1, dto2);
  }

  @Test
  void updateCourseShouldCorrectlyMapAndSaveCourse() {
    CourseDTO courseDTO = new CourseDTO();
    Course course = new Course();

    when(courseMapper.courseDTOToCourse(courseDTO)).thenReturn(course);

    courseServiceImpl.updateCourse(courseDTO);

    verify(courseMapper).courseDTOToCourse(courseDTO);
    verify(courseRepository).save(course);
  }

  @Test
  void deleteCourseShouldReturnTrueWhenCourseExists() {
    String id = "existing-id";
    when(courseRepository.existsById(id)).thenReturn(true);

    boolean result = courseServiceImpl.deleteCourse(id);

    verify(courseRepository).deleteById(id);
    assertThat(result).isTrue();
  }

  @Test
  void deleteCourseShouldReturnFalseWhenCourseDoesNotExist() {
    String id = "non-existing-id";
    when(courseRepository.existsById(id)).thenReturn(false);

    boolean result = courseServiceImpl.deleteCourse(id);

    verify(courseRepository, never()).deleteById(id);
    assertThat(result).isFalse();
  }

}
