package ua.foxminded.universitycms.entity.user.roles;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.foxminded.universitycms.entity.group.Group;
import ua.foxminded.universitycms.entity.user.User;

@Entity
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(setterPrefix = "with")
@Data
@EqualsAndHashCode(callSuper = true)
public class Student extends User {

  @ManyToOne
  @JoinColumn(name = "group_id", nullable = false)
  private Group ownerGroup;

}
