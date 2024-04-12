package ua.foxminded.universitycms.model.entity.user.universityuserdata;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import ua.foxminded.universitycms.model.entity.user_dev.User;

@Entity
@Table(name = "university_user_data")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "data_type")
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder(setterPrefix = "with")
public abstract class UniversityUserData {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @OneToOne(mappedBy = "universityUserData")
  private User user;

}
