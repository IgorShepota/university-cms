package ua.foxminded.universitycms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.universitycms.model.entity.CourseAssignment;

@Repository
public interface CourseAssignmentRepository extends JpaRepository<CourseAssignment, String> {

  List<CourseAssignment> findByCourseId(String courseId);

}
