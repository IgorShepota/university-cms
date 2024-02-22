package ua.foxminded.universitycms.service.coursemanagement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.foxminded.universitycms.entity.coursemanagement.Course;
import ua.foxminded.universitycms.repository.coursemanagement.CourseRepository;

@SpringBootTest
class CourseServiceTest {

  @MockBean
  private CourseRepository courseRepository;

  @Autowired
  private CourseService courseService;

  @Test
  void addCourseShouldWorkCorrectlyIfCourseEntityCorrect() {
    Course newCourse = Course.builder().withId("1").withName("Mathematics").build();

    courseService.addCourse(newCourse);

    verify(courseRepository).save(newCourse);
  }

  @Test
  void getCourseByIdShouldReturnCorrectCourseIfExists() {
    String courseId = "1";
    Course mockCourse = Course.builder().withId(courseId).withName("Mathematics").build();

    when(courseRepository.findById(courseId)).thenReturn(Optional.of(mockCourse));

    Optional<Course> result = courseService.getCourseById(courseId);

    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(mockCourse);
  }

  @Test
  void getCourseByIdShouldReturnEmptyIfCourseDoesNotExist() {
    String nonExistentId = "nonexistent";
    when(courseRepository.findById(nonExistentId)).thenReturn(Optional.empty());

    Optional<Course> result = courseService.getCourseById(nonExistentId);

    assertThat(result).isEmpty();
  }

  @Test
  void getAllCoursesShouldReturnAllCourses() {
    List<Course> mockCourses = Arrays.asList(
        Course.builder().withId("1").withName("Mathematics").build(),
        Course.builder().withId("2").withName("Physics").build());

    when(courseRepository.findAll()).thenReturn(mockCourses);

    List<Course> courses = courseService.getAllCourses();

    assertThat(courses).hasSize(2).isEqualTo(mockCourses);
  }

  @Test
  void getAllCoursesWithPaginationShouldReturnCorrectData() {
    Course course1 = Course.builder().withId("1").withName("Mathematics").build();

    Course course2 = Course.builder().withId("2").withName("Physics").build();

    List<Course> courses = Arrays.asList(course1, course2);
    Page<Course> coursePage = new PageImpl<>(courses);

    when(courseRepository.findAll(any(Pageable.class))).thenReturn(coursePage);

    List<Course> returnedCourses = courseService.getAllCourses(1, 2);

    assertThat(returnedCourses).hasSize(2).containsExactlyInAnyOrder(course1, course2);

    verify(courseRepository).findAll(PageRequest.of(0, 2));
  }

  @Test
  void updateCourseShouldCallSaveMethod() {
    Course courseToUpdate = Course.builder().withId("1").withName("Mathematics").build();

    courseService.updateCourse(courseToUpdate);

    verify(courseRepository).save(courseToUpdate);
  }

  @Test
  void deleteCourseShouldWorkCorrectlyIfCourseExists() {
    String courseId = "1";
    boolean expectedResult = true;

    when(courseRepository.existsById(courseId)).thenReturn(true);

    boolean result = courseService.deleteCourse(courseId);

    assertThat(result).isEqualTo(expectedResult);
    verify(courseRepository).deleteById(courseId);
  }

  @Test
  void deleteCourseShouldReturnFalseIfCourseNotExists() {
    String courseId = "nonexistent";
    boolean expectedResult = false;

    when(courseRepository.existsById(courseId)).thenReturn(false);

    boolean result = courseService.deleteCourse(courseId);

    assertThat(result).isEqualTo(expectedResult);
  }
}
