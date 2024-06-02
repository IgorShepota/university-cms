package ua.foxminded.universitycms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.universitycms.model.entity.Course;
import ua.foxminded.universitycms.model.entity.CourseStatus;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

  List<Course> findByStatus(CourseStatus status);

}
