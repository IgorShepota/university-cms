package ua.foxminded.universitycms.model.entity.user.universityuserdata;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ua.foxminded.universitycms.model.entity.user.User;

@Entity
@Table(name = "university_user_data")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "data_type", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder(setterPrefix = "with")
public abstract class UniversityUserData {

  @Id
  private String id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;

}
