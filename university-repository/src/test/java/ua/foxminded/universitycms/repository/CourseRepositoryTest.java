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
    String expectedId = "8a9d5f8e-a8d8-11ed-a8fc-0242ac120002";
    Optional<Course> optionalCourse = courseRepository.findById(expectedId);
    assertThat(optionalCourse).isPresent();
    Course course = optionalCourse.get();
    assertThat(course.getId()).isEqualTo(expectedId);
  }

}
