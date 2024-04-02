package ua.foxminded.universitycms.model.entity.user.roles;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.foxminded.universitycms.model.entity.user.User;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "admins")
@NoArgsConstructor
@SuperBuilder(setterPrefix = "with")
@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {

}
