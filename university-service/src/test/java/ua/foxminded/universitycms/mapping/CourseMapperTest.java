package ua.foxminded.universitycms.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import ua.foxminded.universitycms.dto.CourseDTO;
import ua.foxminded.universitycms.model.entity.Course;
import ua.foxminded.universitycms.model.entity.CourseStatus;

class CourseMapperTest {

  private final CourseMapper mapper = new CourseMapperImpl();

  @Test
  void courseToCourseDTOShouldWorkCorrectlyIfDataCorrect() {
    Course course = new Course();
    String courseId = UUID.randomUUID().toString();
    course.setId(courseId);
    course.setName("Mathematics");
    course.setDescription("Mathematics course description");
    course.setStatus(CourseStatus.ACTIVE);

    CourseDTO courseDTO = mapper.courseToCourseDTO(course);

    assertThat(courseDTO).isNotNull();
    assertThat(courseDTO.getId()).isEqualTo(courseId);
    assertThat(courseDTO.getName()).isEqualTo("Mathematics");
    assertThat(courseDTO.getDescription()).isEqualTo("Mathematics course description");
    assertThat(courseDTO.getStatus()).isEqualTo(CourseStatus.ACTIVE);
  }

  @Test
  void courseToCourseDTOShouldWorkCorrectlyIfStatusIsNotNull() {
    Course course = new Course();
    String courseId = UUID.randomUUID().toString();
    course.setId(courseId);
    course.setName("Mathematics");
    course.setDescription("Mathematics course description");
    course.setStatus(CourseStatus.ACTIVE);

    CourseDTO courseDTO = mapper.courseToCourseDTO(course);

    assertThat(courseDTO).isNotNull();
    assertThat(courseDTO.getStatus()).isEqualTo(CourseStatus.ACTIVE);
  }

  @Test
  void courseToCourseDTOShouldHandleNullStatus() {
    Course course = new Course();
    String courseId = UUID.randomUUID().toString();
    course.setId(courseId);
    course.setName("Mathematics");
    course.setDescription("Mathematics course description");
    course.setStatus(null);

    CourseDTO courseDTO = mapper.courseToCourseDTO(course);

    assertThat(courseDTO).isNotNull();
    assertThat(courseDTO.getStatus()).isNull();
  }

  @Test
  void courseDTOToCourseShouldWorkCorrectlyIfDataCorrect() {
    CourseDTO courseDTO = new CourseDTO();
    String courseDTOId = UUID.randomUUID().toString();
    courseDTO.setId(courseDTOId);
    courseDTO.setName("Physics");
    courseDTO.setDescription("Physics course description");
    courseDTO.setStatus(CourseStatus.ACTIVE);

    Course course = mapper.courseDTOToCourse(courseDTO);

    assertThat(course).isNotNull();
    assertThat(course.getId()).isEqualTo(courseDTOId);
    assertThat(course.getName()).isEqualTo("Physics");
    assertThat(course.getDescription()).isEqualTo("Physics course description");
    assertThat(course.getStatus()).isEqualTo(CourseStatus.ACTIVE);
  }

  @Test
  void courseDTOToCourseShouldWorkCorrectlyIfStatusIsNotNull() {
    CourseDTO courseDTO = new CourseDTO();
    String courseDTOId = UUID.randomUUID().toString();
    courseDTO.setId(courseDTOId);
    courseDTO.setName("Physics");
    courseDTO.setDescription("Physics course description");
    courseDTO.setStatus(CourseStatus.ACTIVE);

    Course course = mapper.courseDTOToCourse(courseDTO);

    assertThat(course).isNotNull();
    assertThat(course.getStatus()).isEqualTo(CourseStatus.ACTIVE);
  }

  @Test
  void courseDTOToCourseShouldHandleNullStatus() {
    CourseDTO courseDTO = new CourseDTO();
    String courseDTOId = UUID.randomUUID().toString();
    courseDTO.setId(courseDTOId);
    courseDTO.setName("Physics");
    courseDTO.setDescription("Physics course description");
    courseDTO.setStatus(null);

    Course course = mapper.courseDTOToCourse(courseDTO);

    assertThat(course).isNotNull();
    assertThat(course.getId()).isEqualTo(courseDTOId);
    assertThat(course.getName()).isEqualTo(courseDTO.getName());
    assertThat(course.getDescription()).isEqualTo(courseDTO.getDescription());
    assertThat(course.getStatus()).isNull();
  }

  @Test
  void courseToCourseDTOShouldReturnNullWhenCourseIsNull() {
    CourseDTO result = mapper.courseToCourseDTO(null);

    assertThat(result).isNull();
  }

  @Test
  void courseDTOToCourseShouldReturnNullWhenCourseDTOIsNull() {
    Course result = mapper.courseDTOToCourse(null);

    assertThat(result).isNull();
  }

}
