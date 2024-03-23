package ua.foxminded.universitycms.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import ua.foxminded.universitycms.dto.LessonDTO;
import ua.foxminded.universitycms.model.entity.ClassRoom;
import ua.foxminded.universitycms.model.entity.CourseAssignment;
import ua.foxminded.universitycms.model.entity.Lesson;

class LessonMapperTest {

  private final LessonMapper mapper = new LessonMapperImpl();

  @Test
  void lessonToLessonDTOShouldWorkCorrectlyIfDataCorrect() {
    Lesson lesson = new Lesson();
    ClassRoom classRoom = new ClassRoom();
    classRoom.setId(UUID.randomUUID().toString());
    CourseAssignment courseAssignment = new CourseAssignment();
    courseAssignment.setId(UUID.randomUUID().toString());
    lesson.setClassroom(classRoom);
    lesson.setCourseAssignment(courseAssignment);
    lesson.setId(UUID.randomUUID().toString());
    lesson.setDate(LocalDate.now());
    lesson.setStartTime(LocalTime.of(9, 0));
    lesson.setEndTime(LocalTime.of(10, 0));

    LessonDTO lessonDTO = mapper.lessonToLessonDTO(lesson);

    assertThat(lessonDTO.getClassroomId()).isEqualTo(classRoom.getId());
    assertThat(lessonDTO.getCourseAssignmentId()).isEqualTo(courseAssignment.getId());
    assertThat(lessonDTO.getId()).isEqualTo(lesson.getId());
    assertThat(lessonDTO.getDate()).isEqualTo(lesson.getDate());
    assertThat(lessonDTO.getStartTime()).isEqualTo(lesson.getStartTime());
    assertThat(lessonDTO.getEndTime()).isEqualTo(lesson.getEndTime());
  }

  @Test
  void lessonDTOToLessonShouldReturnNotNullWhenDataCorrect() {
    LessonDTO lessonDTO = new LessonDTO();
    lessonDTO.setId(UUID.randomUUID().toString());
    lessonDTO.setDate(LocalDate.now());
    lessonDTO.setStartTime(LocalTime.of(9, 30));
    lessonDTO.setEndTime(LocalTime.of(10, 30));

    Lesson lesson = mapper.lessonDTOToLesson(lessonDTO);

    assertThat(lesson).isNotNull();
  }

  @Test
  void lessonToLessonDTOShouldReturnNullWhenLessonIsNull() {
    assertThat(mapper.lessonToLessonDTO(null)).isNull();
  }

  @Test
  void lessonDTOToLessonShouldReturnNullWhenLessonDTOIsNull() {
    assertThat(mapper.lessonDTOToLesson(null)).isNull();
  }

  @Test
  void lessonToLessonDTOShouldWorkCorrectlyWhenLessonHasNullFields() {
    Lesson lesson = Lesson.builder()
        .withId(UUID.randomUUID().toString())
        .withClassroom(null)
        .withCourseAssignment(null)
        .build();

    LessonDTO lessonDTO = mapper.lessonToLessonDTO(lesson);

    assertThat(lessonDTO.getClassroomId()).isNull();
    assertThat(lessonDTO.getCourseAssignmentId()).isNull();
  }

  @Test
  void lessonToLessonDTOShouldWorkCorrectlyWhenFieldsIdsAreNull() {
    Lesson lesson = new Lesson();
    String uuid = UUID.randomUUID().toString();
    ClassRoom classRoom = new ClassRoom();
    CourseAssignment courseAssignment = new CourseAssignment();

    lesson.setId(uuid);
    lesson.setClassroom(classRoom);
    lesson.setCourseAssignment(courseAssignment);

    LessonDTO lessonDTO = mapper.lessonToLessonDTO(lesson);

    assertThat(lessonDTO.getId()).isEqualTo(lesson.getId());
    assertThat(lessonDTO.getClassroomId()).isEqualTo(classRoom.getId());
    assertThat(lessonDTO.getCourseAssignmentId()).isEqualTo(courseAssignment.getId());
  }

}
