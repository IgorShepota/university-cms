package ua.foxminded.universitycms.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.foxminded.universitycms.model.entity.Course;
import ua.foxminded.universitycms.model.entity.CourseAssignment;
import ua.foxminded.universitycms.model.entity.Group;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.TeacherData;

@Repository
public interface CourseAssignmentRepository extends JpaRepository<CourseAssignment, String> {

  boolean existsById(String id);

  boolean existsByCourseAndTeacherData(Course course, TeacherData teacherData);

  boolean existsByCourseAndTeacherDataAndGroup(Course course, TeacherData teacherData, Group group);

  boolean existsByCourseAndTeacherDataAndGroupAndIdNot(Course course, TeacherData teacherData,
      Group group, String id);

  List<CourseAssignment> findByCourseId(String courseId);

  @Query("SELECT c FROM CourseAssignment c WHERE c.group IS NULL AND c.course IS NOT NULL AND c.teacherData IS NOT NULL")
  Set<CourseAssignment> findCourseAssignmentsWithNoGroup();

  long countByCourseAndTeacherData(Course course, TeacherData teacherData);

  void deleteByCourseId(String courseId);

}
