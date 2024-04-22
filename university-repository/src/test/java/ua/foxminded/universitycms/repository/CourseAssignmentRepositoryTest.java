package ua.foxminded.universitycms.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.universitycms.model.entity.CourseAssignment;

class CourseAssignmentRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private CourseAssignmentRepository courseAssignmentRepository;

  @Test
  void findByIdShouldReturnCourseAssignmentWhenIdExists() {
    String expectedId = "3a9d5f8e-a8d8-11ed-a8fc-0242ac120002";
    Optional<CourseAssignment> optionalCourseAssignment = courseAssignmentRepository.findById(
        expectedId);
    assertThat(optionalCourseAssignment).isPresent();
    CourseAssignment courseAssignment = optionalCourseAssignment.get();
    assertThat(courseAssignment.getId()).isEqualTo(expectedId);
  }

}
