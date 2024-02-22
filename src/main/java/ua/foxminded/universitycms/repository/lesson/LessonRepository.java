package ua.foxminded.universitycms.repository.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.universitycms.entity.lesson.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, String> {

}
