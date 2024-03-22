package ua.foxminded.universitycms.service;

import java.util.List;
import java.util.Optional;
import ua.foxminded.universitycms.model.entity.Lesson;

public interface LessonService {

  void addLesson(Lesson lesson);

  Optional<Lesson> getLessonById(String id);

  List<Lesson> getAllLessons();

  List<Lesson> getAllLessons(Integer page, Integer itemsPerPage);

  void updateLesson(Lesson lesson);

  boolean deleteLesson(String id);

}
