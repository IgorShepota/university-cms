package ua.foxminded.universitycms.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.foxminded.universitycms.model.entity.CourseAssignment;

@Repository
public interface CourseAssignmentRepository extends JpaRepository<CourseAssignment, String> {

  List<CourseAssignment> findByCourseId(String courseId);

  @Query("SELECT c FROM CourseAssignment c WHERE c.group IS NULL AND c.course IS NOT NULL AND c.teacherData IS NOT NULL")
  Set<CourseAssignment> findCourseAssignmentsWithNoGroup();

}
