package ua.foxminded.universitycms.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.universitycms.model.entity.Lesson;

class LessonRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private LessonRepository lessonRepository;

  @Test
  void findByIdShouldReturnLessonWhenIdExists() {
    String expectedId = "4a9d5f8e-a8d8-11ed-a8fc-0242ac120002";
    Optional<Lesson> optionalLesson = lessonRepository.findById(
        expectedId);
    assertThat(optionalLesson).isPresent();
    Lesson lesson = optionalLesson.get();
    assertThat(lesson.getId()).isEqualTo(expectedId);
  }

}
