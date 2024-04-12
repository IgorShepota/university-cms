package ua.foxminded.universitycms.model.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.StudentData;

@Entity
@Table(name = "groups")
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Data
@EqualsAndHashCode(exclude = {"students", "courseAssignments"})
@ToString(exclude = {"students", "courseAssignments"})
public class Group {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(name = "name", nullable = false, unique = true)
  @Pattern(regexp = "^FLA-\\d{3}$", message = "Name must follow the 'FLA-XXX' pattern where XXX are digits")
  private String name;

  @OneToMany(mappedBy = "ownerGroup", fetch = FetchType.LAZY)
  private List<StudentData> students;

  @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
  private List<CourseAssignment> courseAssignments;

}
