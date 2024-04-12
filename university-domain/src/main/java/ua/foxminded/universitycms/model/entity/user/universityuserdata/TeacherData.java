package ua.foxminded.universitycms.model.entity.user.universityuserdata;

import java.util.List;
import javax.persistence.DiscriminatorValue;
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
import ua.foxminded.universitycms.model.entity.Course;
import ua.foxminded.universitycms.model.entity.CourseAssignment;

@Entity
@Table(name = "teacher_data")
@PrimaryKeyJoinColumn(name = "id")
@DiscriminatorValue("TEACHER")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(setterPrefix = "with")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"courses", "courseAssignments"})
@ToString(callSuper = true, exclude = {"courses", "courseAssignments"})
public class TeacherData extends UniversityUserData {

  @ManyToMany
  @JoinTable(name = "teachers_courses", joinColumns = @JoinColumn(name = "teacher_data_id"),
      inverseJoinColumns = @JoinColumn(name = "course_id"))
  private List<Course> courses;

  @OneToMany(mappedBy = "teacher")
  private List<CourseAssignment> courseAssignments;

}
