package ua.foxminded.universitycms.entity.coursemanagement;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ua.foxminded.universitycms.entity.user.roles.Teacher;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Data
public class Course {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(name = "name", nullable = false, unique = true)
  @NotBlank(message = "Course name cannot be blank")
  private String name;

  @Column(name = "description", nullable = false, unique = true)
  private String description;

  @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
  private List<Teacher> teachers;

  @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
  private List<CourseAssignment> courseAssignments;

}
