package ua.foxminded.universitycms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.universitycms.model.entity.CourseAssignment;

public interface CourseAssignmentRepository extends JpaRepository<CourseAssignment, String> {

}
