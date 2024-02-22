package ua.foxminded.universitycms.service.lesson;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.entity.lesson.Lesson;
import ua.foxminded.universitycms.repository.lesson.LessonRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonService {

  private final LessonRepository lessonRepository;

  @Transactional
  public void addLesson(Lesson lesson) {
    lessonRepository.save(lesson);
    log.info("Lesson with id {} was successfully saved.", lesson.getId());
  }

  public Optional<Lesson> getLessonById(String id) {
    log.info("Fetching lesson with id {}.", id);
    return lessonRepository.findById(id);
  }

  public List<Lesson> getAllLessons() {
    log.info("Fetching all lessons.");
    return lessonRepository.findAll();
  }

  public List<Lesson> getAllLessons(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of lessons with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);

    return lessonRepository.findAll(pageable).getContent();
  }

  @Transactional
  public void updateLesson(Lesson lesson) {
    log.info("Updating lesson: {}", lesson);
    lessonRepository.save(lesson);
  }

  @Transactional
  public boolean deleteLesson(String id) {
    if (lessonRepository.existsById(id)) {
      lessonRepository.deleteById(id);
      log.info("Lesson with id {} was successfully deleted.", id);
      return true;
    } else {
      log.info("Lesson with id {} not found.", id);
      return false;
    }
  }

}
