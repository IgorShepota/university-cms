package ua.foxminded.universitycms.model.entity;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
  private String name;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private GroupStatus status = GroupStatus.NEW;

  @OneToMany(mappedBy = "ownerGroup", fetch = FetchType.EAGER)
  private Set<StudentData> students = new LinkedHashSet<>();

  @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
  private Set<CourseAssignment> courseAssignments = new LinkedHashSet<>();

}
