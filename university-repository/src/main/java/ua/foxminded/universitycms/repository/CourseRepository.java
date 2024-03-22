package ua.foxminded.universitycms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.universitycms.model.entity.Course;

public interface CourseRepository extends JpaRepository<Course, String> {

}
