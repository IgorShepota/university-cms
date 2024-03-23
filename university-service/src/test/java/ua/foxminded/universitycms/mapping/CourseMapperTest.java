package ua.foxminded.universitycms.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import ua.foxminded.universitycms.dto.CourseDTO;
import ua.foxminded.universitycms.model.entity.Course;

class CourseMapperTest {

  private final CourseMapper mapper = new CourseMapperImpl();

  @Test
  void courseToCourseDTOShouldWorkCorrectlyIfDataCorrect() {
    Course course = new Course();
    String courseId = UUID.randomUUID().toString();
    course.setId(courseId);
    course.setName("Mathematics");
    course.setDescription("Mathematics course description");

    CourseDTO courseDTO = mapper.courseToCourseDTO(course);

    assertThat(courseDTO.getId()).isEqualTo(courseId);
    assertThat(courseDTO.getName()).isEqualTo(course.getName());
    assertThat(courseDTO.getDescription()).isEqualTo(course.getDescription());
  }

  @Test
  void courseDTOToCourseShouldWorkCorrectlyIfDataCorrect() {
    CourseDTO courseDTO = new CourseDTO();
    String courseDTOId = UUID.randomUUID().toString();
    courseDTO.setId(courseDTOId);
    courseDTO.setName("Physics");
    courseDTO.setDescription("Physics course description");

    Course course = mapper.courseDTOToCourse(courseDTO);

    assertThat(course.getId()).isEqualTo(courseDTOId);
    assertThat(course.getName()).isEqualTo(courseDTO.getName());
    assertThat(course.getDescription()).isEqualTo(courseDTO.getDescription());
  }

  @Test
  void courseDTOToCourseShouldReturnNullWhenCourseIsNull() {
    CourseDTO result = mapper.courseToCourseDTO(null);

    assertThat(result).isNull();
  }

  @Test
  void courseDTOToCourseShouldReturnNullWhenCourseDTOIsNull() {
    Course result = mapper.courseDTOToCourse(null);

    assertThat(result).isNull();
  }

}
