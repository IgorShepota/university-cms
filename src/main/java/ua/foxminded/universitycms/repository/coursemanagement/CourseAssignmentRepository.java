package ua.foxminded.universitycms.repository.coursemanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.universitycms.entity.coursemanagement.CourseAssignment;

public interface CourseAssignmentRepository extends JpaRepository<CourseAssignment, String> {

}
