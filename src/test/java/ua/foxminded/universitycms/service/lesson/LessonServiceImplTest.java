package ua.foxminded.universitycms.service.lesson;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.foxminded.universitycms.model.entity.lesson.Lesson;
import ua.foxminded.universitycms.repository.lesson.LessonRepository;

@SpringBootTest
class LessonServiceImplTest {

  @MockBean
  private LessonRepository lessonRepository;

  @Autowired
  private LessonServiceImpl lessonServiceImpl;

  @Test
  void addLessonShouldWorkCorrectlyIfLessonEntityCorrect() {

    Lesson newLesson = Lesson.builder().withId("1").withDate(LocalDate.now())
        .withStartTime(LocalTime.of(9, 0)).withEndTime(LocalTime.of(10, 0)).build();

    lessonServiceImpl.addLesson(newLesson);

    verify(lessonRepository).save(newLesson);
  }

  @Test
  void getLessonByIdShouldReturnCorrectLessonIfExists() {

    String lessonId = "1";
    Lesson mockLesson = Lesson.builder().withId(lessonId).withDate(LocalDate.now())
        .withStartTime(LocalTime.of(9, 0)).withEndTime(LocalTime.of(10, 0)).build();

    when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(mockLesson));

    Optional<Lesson> result = lessonServiceImpl.getLessonById(lessonId);

    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(mockLesson);
  }

  @Test
  void getLessonByIdShouldReturnEmptyIfLessonDoesNotExist() {

    String nonExistentId = "nonexistent";
    when(lessonRepository.findById(nonExistentId)).thenReturn(Optional.empty());

    Optional<Lesson> result = lessonServiceImpl.getLessonById(nonExistentId);

    assertThat(result).isEmpty();
  }

  @Test
  void getAllLessonsShouldReturnAllLessons() {

    List<Lesson> mockLessons = Arrays.asList(
        Lesson.builder().withId("1").withDate(LocalDate.now()).withStartTime(LocalTime.of(9, 0))
            .withEndTime(LocalTime.of(10, 0)).build(),
        Lesson.builder().withId("2").withDate(LocalDate.now()).withStartTime(LocalTime.of(10, 0))
            .withEndTime(LocalTime.of(11, 0)).build());

    when(lessonRepository.findAll()).thenReturn(mockLessons);

    List<Lesson> lessons = lessonServiceImpl.getAllLessons();

    assertThat(lessons).hasSize(2).isEqualTo(mockLessons);
  }

  @Test
  void getAllLessonsWithPaginationShouldReturnCorrectData() {

    Lesson lesson1 = Lesson.builder().withId("1").withDate(LocalDate.now())
        .withStartTime(LocalTime.of(9, 0)).withEndTime(LocalTime.of(10, 0)).build();
    Lesson lesson2 = Lesson.builder().withId("2").withDate(LocalDate.now())
        .withStartTime(LocalTime.of(10, 0)).withEndTime(LocalTime.of(11, 0)).build();

    List<Lesson> lessons = Arrays.asList(lesson1, lesson2);
    Page<Lesson> lessonPage = new PageImpl<>(lessons);

    when(lessonRepository.findAll(any(Pageable.class))).thenReturn(lessonPage);

    List<Lesson> returnedLessons = lessonServiceImpl.getAllLessons(1, 2);

    assertThat(returnedLessons).hasSize(2).containsExactlyInAnyOrder(lesson1, lesson2);

    verify(lessonRepository).findAll(PageRequest.of(0, 2));
  }

  @Test
  void updateLessonShouldCallSaveMethod() {

    Lesson lessonToUpdate = Lesson.builder().withId("1").withDate(LocalDate.now())
        .withStartTime(LocalTime.of(9, 0)).withEndTime(LocalTime.of(10, 0)).build();

    lessonServiceImpl.updateLesson(lessonToUpdate);

    verify(lessonRepository).save(lessonToUpdate);
  }

  @Test
  void deleteLessonShouldWorkCorrectlyIfLessonExists() {

    String lessonId = "1";

    when(lessonRepository.existsById(lessonId)).thenReturn(true);

    boolean result = lessonServiceImpl.deleteLesson(lessonId);

    assertThat(result).isTrue();
    verify(lessonRepository).deleteById(lessonId);
  }

  @Test
  void deleteLessonShouldReturnFalseIfLessonNotExists() {

    String lessonId = "nonexistent";

    when(lessonRepository.existsById(lessonId)).thenReturn(false);

    boolean result = lessonServiceImpl.deleteLesson(lessonId);

    assertThat(result).isFalse();
  }

}
