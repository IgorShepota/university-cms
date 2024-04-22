package ua.foxminded.universitycms.repository.user.role;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.universitycms.model.entity.user.role.Role;
import ua.foxminded.universitycms.model.entity.user.role.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

  Optional<Role> findByName(RoleName name);

}
