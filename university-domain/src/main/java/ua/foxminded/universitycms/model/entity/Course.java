package ua.foxminded.universitycms.model.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.TeacherData;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Data
@EqualsAndHashCode(exclude = {"teachers", "courseAssignments"})
@ToString(exclude = {"teachers", "courseAssignments"})
public class Course {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "description", nullable = false, unique = true)
  private String description;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private CourseStatus status = CourseStatus.ACTIVE;

  @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
  private List<TeacherData> teachers;

  @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
  private List<CourseAssignment> courseAssignments;

}
