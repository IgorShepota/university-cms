package ua.foxminded.universitycms.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.universitycms.model.entity.Course;
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

  @Test
  void findByCourseIdShouldReturnAssignmentsWhenCourseExists() {
    List<CourseAssignment> assignments = courseAssignmentRepository.findByCourseId(
        "8a9d5f8e-a8d8-11ed-a8fc-0242ac120002");

    assertThat(assignments).hasSize(1);
    assertThat(assignments).extracting(CourseAssignment::getCourse).extracting(Course::getName)
        .containsOnly("Database Systems");
  }

  @Test
  void findByCourseIdShouldReturnEmptyListWhenCourseDoesNotExist() {
    List<CourseAssignment> assignments = courseAssignmentRepository.findByCourseId(
        "non-existent-id");

    assertThat(assignments).isEmpty();
  }

  @Test
  void findCourseAssignmentsWithNoGroupShouldReturnAllAssignmentsWithoutGroup() {
    Set<CourseAssignment> assignments = courseAssignmentRepository.findCourseAssignmentsWithNoGroup();

    assertThat(assignments).hasSize(1);
    assertThat(assignments).extracting(CourseAssignment::getGroup).containsOnlyNulls();
  }

}
