package ua.foxminded.universitycms.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.foxminded.universitycms.dto.CourseAssignmentDTO;
import ua.foxminded.universitycms.model.entity.Course;
import ua.foxminded.universitycms.model.entity.CourseAssignment;
import ua.foxminded.universitycms.model.entity.Group;
import ua.foxminded.universitycms.model.entity.user.roles.Teacher;

class CourseAssignmentMapperTest {

  private final CourseAssignmentMapper mapper = new CourseAssignmentMapperImpl();
  private String groupId;
  private String courseId;
  private String teacherId;

  @BeforeEach
  void setUp() {
    groupId = UUID.randomUUID().toString();
    courseId = UUID.randomUUID().toString();
    teacherId = UUID.randomUUID().toString();
  }

  @Test
  void courseAssignmentToCourseAssignmentDTOShouldWorkCorrectlyIfDataCorrect() {
    Group group = new Group();
    group.setId(groupId);
    Course course = new Course();
    course.setId(courseId);
    Teacher teacher = new Teacher();
    teacher.setId(teacherId);
    CourseAssignment courseAssignment = CourseAssignment.builder()
        .withGroup(group)
        .withCourse(course)
        .withTeacher(teacher)
        .build();

    CourseAssignmentDTO courseAssignmentDTO = mapper.courseAssignmentToCourseAssignmentDTO(
        courseAssignment);

    assertThat(courseAssignmentDTO.getGroupId()).isEqualTo(groupId);
    assertThat(courseAssignmentDTO.getCourseId()).isEqualTo(courseId);
    assertThat(courseAssignmentDTO.getTeacherId()).isEqualTo(teacherId);
  }

  @Test
  void courseAssignmentDTOToCourseAssignmentShouldWorkCorrectlyIfDataCorrect() {
    CourseAssignmentDTO courseAssignmentDTO = CourseAssignmentDTO.builder()
        .groupId(UUID.randomUUID().toString())
        .courseId(UUID.randomUUID().toString())
        .teacherId(UUID.randomUUID().toString())
        .build();

    CourseAssignment courseAssignment = mapper.courseAssignmentDTOToCourseAssignment(
        courseAssignmentDTO);

    assertThat(courseAssignment).isNotNull();
  }

  @Test
  void courseAssignmentToCourseAssignmentShouldReturnNullWhenCourseAssignmentIsNull() {
    assertThat(mapper.courseAssignmentToCourseAssignmentDTO(null)).isNull();
  }

  @Test
  void courseAssignmentDTOToCourseAssignmentShouldReturnNullWhenCourseAssignmentDTOIsNull() {
    assertThat(mapper.courseAssignmentDTOToCourseAssignment(null)).isNull();
  }

  @Test
  void courseAssignmentToCourseAssignmentDTOShouldWorkCorrectlyWhenCourseAssignmentHasNullFields() {
    CourseAssignment courseAssignment = CourseAssignment.builder()
        .withGroup(null)
        .withCourse(null)
        .withTeacher(null)
        .build();

    CourseAssignmentDTO courseAssignmentDTO = mapper.courseAssignmentToCourseAssignmentDTO(
        courseAssignment);

    assertThat(courseAssignmentDTO.getGroupId()).isNull();
    assertThat(courseAssignmentDTO.getCourseId()).isNull();
    assertThat(courseAssignmentDTO.getTeacherId()).isNull();
  }

  @Test
  void courseAssignmentToCourseAssignmentDTOShouldWorkCorrectlyWhenFieldsIdsAreNull() {
    CourseAssignment courseAssignment = new CourseAssignment();
    Group group = new Group();
    Course course = new Course();
    Teacher teacher = new Teacher();

    courseAssignment.setGroup(group);
    courseAssignment.setCourse(course);
    courseAssignment.setTeacher(teacher);

    CourseAssignmentDTO courseAssignmentDTO = mapper.courseAssignmentToCourseAssignmentDTO(
        courseAssignment);

    assertThat(courseAssignmentDTO.getGroupId()).isEqualTo(group.getId());
    assertThat(courseAssignmentDTO.getCourseId()).isEqualTo(course.getId());
    assertThat(courseAssignmentDTO.getTeacherId()).isEqualTo(teacher.getId());
  }

}
