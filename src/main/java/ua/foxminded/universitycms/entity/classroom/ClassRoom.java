package ua.foxminded.universitycms.entity.classroom;

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
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ua.foxminded.universitycms.entity.lesson.Lesson;

@Entity
@Table(name = "classrooms")
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Data
public class ClassRoom {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(name = "name", nullable = false, unique = true)
  @Pattern(regexp = "^[0-9]{3}$", message = "Name must consist of exactly 3 digits")
  private String name;

  @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY)
  private List<Lesson> lessons;

}
