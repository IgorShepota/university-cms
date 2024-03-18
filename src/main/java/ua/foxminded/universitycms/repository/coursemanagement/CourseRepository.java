package ua.foxminded.universitycms.repository.coursemanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.universitycms.model.entity.coursemanagement.Course;

public interface CourseRepository extends JpaRepository<Course, String> {

}
