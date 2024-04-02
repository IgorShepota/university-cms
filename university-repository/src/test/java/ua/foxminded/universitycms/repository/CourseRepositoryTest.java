package ua.foxminded.universitycms.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.universitycms.model.entity.Course;

class CourseRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private CourseRepository courseRepository;

  @Test
  void findByIdShouldReturnCourseWhenIdExists() {
    String expectedId = "4f5a5568-8f09-47c7-9f6b-5fb4f6a90d47";
    Optional<Course> optionalCourse = courseRepository.findById(
        expectedId);
    assertThat(optionalCourse).isPresent();
    Course course = optionalCourse.get();
    assertThat(course.getId()).isEqualTo(expectedId);
  }

}
