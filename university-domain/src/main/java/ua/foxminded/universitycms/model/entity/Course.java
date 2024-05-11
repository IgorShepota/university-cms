package ua.foxminded.universitycms.model.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
  @NotBlank(message = "Course name is required.")
  @Size(min = 5, max = 100, message = "Course name must be between 5 and 100 characters.")
  private String name;

  @Column(name = "description", nullable = false, unique = true)
  @NotBlank(message = "Description is required.")
  @Size(min = 10, max = 100, message = "Description must be between 10 and 1000 characters.")
  private String description;

  @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
  private List<TeacherData> teachers;

  @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
  private List<CourseAssignment> courseAssignments;

}
