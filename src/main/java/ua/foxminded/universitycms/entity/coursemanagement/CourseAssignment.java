package ua.foxminded.universitycms.entity.coursemanagement;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ua.foxminded.universitycms.entity.group.Group;
import ua.foxminded.universitycms.entity.lesson.Lesson;
import ua.foxminded.universitycms.entity.user.roles.Teacher;

@Entity
@Table(name = "course_assignments")
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Data
public class CourseAssignment {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "group_id", nullable = false)
  private Group group;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teacher_id", nullable = false)
  private Teacher teacher;

  @OneToMany(mappedBy = "courseAssignment", fetch = FetchType.LAZY)
  private List<Lesson> lessons;

}