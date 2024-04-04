package ua.foxminded.universitycms.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.dto.LessonDTO;
import ua.foxminded.universitycms.mapping.LessonMapper;
import ua.foxminded.universitycms.model.entity.Lesson;
import ua.foxminded.universitycms.repository.LessonRepository;
import ua.foxminded.universitycms.service.LessonService;

@Service
@RequiredArgsConstructor
@Slf4j
public class LessonServiceImpl implements LessonService {

  private final LessonRepository lessonRepository;
  private final LessonMapper lessonMapper;

  @Override
  @Transactional
  public void addLesson(LessonDTO lessonDTO) {
    Lesson lesson = lessonMapper.lessonDTOToLesson(lessonDTO);
    lesson = lessonRepository.save(lesson);
    log.info("Lesson with id {} was successfully saved.", lesson.getId());
  }

  @Override
  public Optional<LessonDTO> getLessonById(String id) {
    log.info("Fetching lesson with id {}.", id);
    return lessonRepository.findById(id)
        .map(lessonMapper::lessonToLessonDTO);
  }

  @Override
  public List<LessonDTO> getAllLessons() {
    log.info("Fetching all lessons.");
    List<Lesson> lessons = lessonRepository.findAll();
    return lessons.stream()
        .map(lessonMapper::lessonToLessonDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<LessonDTO> getAllLessons(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of lessons with {} items per page.", page, itemsPerPage);
    Pageable pageable = PageRequest.of(page - 1, itemsPerPage);

    return lessonRepository.findAll(pageable).stream()
        .map(lessonMapper::lessonToLessonDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void updateLesson(LessonDTO lessonDTO) {
    Lesson lesson = lessonMapper.lessonDTOToLesson(lessonDTO);
    lessonRepository.save(lesson);
    log.info("Updating lesson: {}", lessonDTO);
  }

  @Override
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
