package ua.foxminded.universitycms.entity.user.roles;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.foxminded.universitycms.entity.coursemanagement.Course;
import ua.foxminded.universitycms.entity.coursemanagement.CourseAssignment;
import ua.foxminded.universitycms.entity.user.User;

@Entity
@Table(name = "teachers")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(setterPrefix = "with")
@Data
@EqualsAndHashCode(callSuper = true)
public class Teacher extends User {

  @ManyToMany
  @JoinTable(name = "teachers_courses", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
  private List<Course> courses;

  @OneToMany(mappedBy = "teacher")
  private List<CourseAssignment> courseAssignments;

}
