package ua.foxminded.universitycms.model.entity.user.universityuserdata;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "admin_data")
@PrimaryKeyJoinColumn(name = "id")
@DiscriminatorValue("STUDENT")
@NoArgsConstructor
@SuperBuilder(setterPrefix = "with")
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminData extends UniversityUserData {

}
