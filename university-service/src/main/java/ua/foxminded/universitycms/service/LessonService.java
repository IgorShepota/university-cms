package ua.foxminded.universitycms.service;

import java.util.List;
import java.util.Optional;
import ua.foxminded.universitycms.dto.LessonDTO;

public interface LessonService {

  void addLesson(LessonDTO lessonDTO);

  Optional<LessonDTO> getLessonById(String id);

  List<LessonDTO> getAllLessons();

  List<LessonDTO> getAllLessons(Integer page, Integer itemsPerPage);

  void updateLesson(LessonDTO lessonDTO);

  boolean deleteLesson(String id);

}
