package ua.foxminded.universitycms.repository.coursemanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.universitycms.model.entity.coursemanagement.CourseAssignment;

public interface CourseAssignmentRepository extends JpaRepository<CourseAssignment, String> {

}
