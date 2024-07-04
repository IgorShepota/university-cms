package ua.foxminded.universitycms.model.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity
@Table(name = "classrooms")
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Data
@EqualsAndHashCode(exclude = "lessons")
@ToString(exclude = "lessons")
public class ClassRoom {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY)
  private List<Lesson> lessons;

}
