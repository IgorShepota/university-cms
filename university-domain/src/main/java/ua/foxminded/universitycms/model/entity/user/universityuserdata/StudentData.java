package ua.foxminded.universitycms.model.entity.user.universityuserdata;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.foxminded.universitycms.model.entity.Group;

@Entity
@Table(name = "student_data")
@PrimaryKeyJoinColumn(name = "id")
@DiscriminatorValue("STUDENT")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(setterPrefix = "with")
@Data
public class StudentData extends UniversityUserData {

  @ManyToOne
  @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
  private Group ownerGroup;

}
