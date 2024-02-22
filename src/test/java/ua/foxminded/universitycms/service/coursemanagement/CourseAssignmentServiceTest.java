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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.foxminded.universitycms.entity.coursemanagement.CourseAssignment;
import ua.foxminded.universitycms.repository.coursemanagement.CourseAssignmentRepository;

@SpringBootTest
class CourseAssignmentServiceTest {

  @MockBean
  private CourseAssignmentRepository courseAssignmentRepository;

  @Autowired
  private CourseAssignmentService courseAssignmentService;

  @Test
  void addCourseAssignmentShouldWorkCorrectlyIfCourseAssignmentEntityCorrect() {
    CourseAssignment newCourseAssignment = new CourseAssignment();
    newCourseAssignment.setId("1");

    courseAssignmentService.addCourseAssignment(newCourseAssignment);

    verify(courseAssignmentRepository).save(newCourseAssignment);
  }

  @Test
  void getCourseAssignmentByIdShouldWorkCorrectlyIfCourseAssignmentExists() {
    String courseAssignmentId = "1";
    CourseAssignment mockCourseAssignment = new CourseAssignment();
    mockCourseAssignment.setId(courseAssignmentId);

    when(courseAssignmentRepository.findById(courseAssignmentId)).thenReturn(
        Optional.of(mockCourseAssignment));

    Optional<CourseAssignment> result = courseAssignmentService.getCourseAssignmentById(
        courseAssignmentId);

    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(mockCourseAssignment);
  }

  @Test
  void getAllCourseAssignmentsShouldReturnAllCourseAssignments() {
    List<CourseAssignment> mockCourseAssignments = Arrays.asList(new CourseAssignment(),
        new CourseAssignment());

    when(courseAssignmentRepository.findAll()).thenReturn(mockCourseAssignments);

    List<CourseAssignment> courseAssignments = courseAssignmentService.getAllCourseAssignments();

    assertThat(courseAssignments).hasSize(2).isEqualTo(mockCourseAssignments);
  }

  @Test
  void getAllCourseAssignmentsWithPaginationShouldReturnCorrectData() {
    CourseAssignment courseAssignment1 = new CourseAssignment();
    courseAssignment1.setId("1");
    CourseAssignment courseAssignment2 = new CourseAssignment();
    courseAssignment2.setId("2");

    List<CourseAssignment> courseAssignments = Arrays.asList(courseAssignment1, courseAssignment2);
    PageImpl<CourseAssignment> courseAssignmentPage = new PageImpl<>(courseAssignments);

    when(courseAssignmentRepository.findAll(any(Pageable.class))).thenReturn(courseAssignmentPage);

    List<CourseAssignment> returnedCourseAssignments = courseAssignmentService.getAllCourseAssignments(
        1, 2);

    assertThat(returnedCourseAssignments).hasSize(2)
        .containsExactlyInAnyOrderElementsOf(courseAssignments);

    verify(courseAssignmentRepository).findAll(PageRequest.of(0, 2));
  }

  @Test
  void updateCourseAssignmentShouldCallRepositorySaveMethod() {
    CourseAssignment courseAssignment = new CourseAssignment();
    courseAssignment.setId("1");

    courseAssignmentService.updateCourseAssignment(courseAssignment);

    verify(courseAssignmentRepository).save(courseAssignment);
  }

  @Test
  void deleteCourseAssignmentShouldWorkCorrectlyIfCourseAssignmentExists() {
    String courseAssignmentId = "1";

    when(courseAssignmentRepository.existsById(courseAssignmentId)).thenReturn(true);

    boolean result = courseAssignmentService.deleteCourseAssignment(courseAssignmentId);

    assertThat(result).isTrue();
    verify(courseAssignmentRepository).deleteById(courseAssignmentId);
  }

  @Test
  void deleteCourseAssignmentShouldReturnFalseIfCourseAssignmentNotExists() {
    String courseAssignmentId = "1";

    when(courseAssignmentRepository.existsById(courseAssignmentId)).thenReturn(false);

    boolean result = courseAssignmentService.deleteCourseAssignment(courseAssignmentId);

    assertThat(result).isFalse();
  }

}
