package ua.foxminded.universitycms.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.foxminded.universitycms.dto.CourseAssignmentDTO;
import ua.foxminded.universitycms.model.entity.Course;
import ua.foxminded.universitycms.model.entity.CourseAssignment;
import ua.foxminded.universitycms.model.entity.Group;
import ua.foxminded.universitycms.model.entity.user.User;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.TeacherData;

class CourseAssignmentMapperTest {

  private final CourseAssignmentMapper mapper = new CourseAssignmentMapperImpl();
  private String groupName;
  private String courseName;
  private String teacherFullName;

  @BeforeEach
  void setUp() {
    groupName = "FLA-101";
    courseName = "Java Programming";
    teacherFullName = "Michael Johnson";
  }

  @Test
  void courseAssignmentToCourseAssignmentDTOShouldWorkCorrectlyIfDataCorrect() {
    User user = User.builder()
        .firstName("Michael")
        .lastName("Johnson")
        .build();

    Group group = new Group();
    group.setName(groupName);
    Course course = new Course();
    course.setName(courseName);
    TeacherData teacherData = new TeacherData();
    teacherData.setUser(user);
    CourseAssignment courseAssignment = CourseAssignment.builder()
        .withGroup(group)
        .withCourse(course)
        .withTeacherData(teacherData)
        .build();

    CourseAssignmentDTO courseAssignmentDTO = mapper.courseAssignmentToCourseAssignmentDTO(
        courseAssignment);

    assertThat(courseAssignmentDTO.getGroupName()).isEqualTo(groupName);
    assertThat(courseAssignmentDTO.getCourseName()).isEqualTo(courseName);
    assertThat(courseAssignmentDTO.getTeacherFullName()).isEqualTo(teacherFullName);
  }

  @Test
  void courseAssignmentDTOToCourseAssignmentShouldWorkCorrectlyIfDataCorrect() {
    CourseAssignmentDTO courseAssignmentDTO = CourseAssignmentDTO.builder()
        .groupName("TestGroup")
        .courseName("TestCourse")
        .teacherFullName("Test Teacher")
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
        .withTeacherData(null)
        .build();

    CourseAssignmentDTO courseAssignmentDTO = mapper.courseAssignmentToCourseAssignmentDTO(
        courseAssignment);

    assertThat(courseAssignmentDTO.getGroupName()).isNull();
    assertThat(courseAssignmentDTO.getCourseName()).isNull();
    assertThat(courseAssignmentDTO.getTeacherFullName()).isNull();
  }

  @Test
  void courseAssignmentToCourseAssignmentDTOShouldWorkCorrectlyWhenFieldsIdsAreNull() {
    CourseAssignment courseAssignment = new CourseAssignment();
    Group group = new Group();
    Course course = new Course();
    TeacherData teacherData = new TeacherData();

    courseAssignment.setGroup(group);
    courseAssignment.setCourse(course);
    courseAssignment.setTeacherData(teacherData);

    CourseAssignmentDTO courseAssignmentDTO = mapper.courseAssignmentToCourseAssignmentDTO(
        courseAssignment);

    assertThat(courseAssignmentDTO.getGroupName()).isEqualTo(group.getId());
    assertThat(courseAssignmentDTO.getCourseName()).isEqualTo(course.getId());
    assertThat(courseAssignmentDTO.getTeacherFullName()).isEqualTo(teacherData.getId());
  }

}
