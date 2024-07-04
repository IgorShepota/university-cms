package ua.foxminded.universitycms.model.entity;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.TeacherData;

@Entity
@Table(name = "course_assignments")
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Data
@EqualsAndHashCode(exclude = "lessons")
@ToString(exclude = "lessons")
public class CourseAssignment {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @ManyToOne
  @JoinColumn(name = "group_id")
  private Group group;

  @ManyToOne
  @JoinColumn(name = "course_id")
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  private Course course;

  @ManyToOne
  @JoinColumn(name = "teacher_data_id")
  private TeacherData teacherData;

  @OneToMany(mappedBy = "courseAssignment", fetch = FetchType.LAZY)
  private List<Lesson> lessons;

}
