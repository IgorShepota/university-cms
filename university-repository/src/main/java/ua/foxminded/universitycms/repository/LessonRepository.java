package ua.foxminded.universitycms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.universitycms.model.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, String> {

}
