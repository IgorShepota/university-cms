package ua.foxminded.universitycms.entity.user.roles;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ua.foxminded.universitycms.entity.group.Group;
import ua.foxminded.universitycms.entity.user.User;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(setterPrefix = "with")
@Data
@EqualsAndHashCode(callSuper = true, exclude = "ownerGroup")
@ToString(callSuper = true, exclude = "ownerGroup")
public class Student extends User {

  @ManyToOne
  @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
  private Group ownerGroup;

}
