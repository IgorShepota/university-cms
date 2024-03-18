package ua.foxminded.universitycms.model.entity.user.roles;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ua.foxminded.universitycms.model.entity.coursemanagement.Course;
import ua.foxminded.universitycms.model.entity.coursemanagement.CourseAssignment;
import ua.foxminded.universitycms.model.entity.user.User;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "teachers")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(setterPrefix = "with")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"courses", "courseAssignments"})
@ToString(callSuper = true, exclude = {"courses", "courseAssignments"})
public class Teacher extends User {

  @ManyToMany
  @JoinTable(name = "teachers_courses", joinColumns = @JoinColumn(name = "teacher_id"),
      inverseJoinColumns = @JoinColumn(name = "course_id"))
  private List<Course> courses;

  @OneToMany(mappedBy = "teacher")
  private List<CourseAssignment> courseAssignments;

}
