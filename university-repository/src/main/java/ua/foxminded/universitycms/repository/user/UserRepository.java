package ua.foxminded.universitycms.repository.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.universitycms.model.entity.user.User;
import ua.foxminded.universitycms.model.entity.user.role.RoleName;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  List<User> findAllByRoleName(RoleName roleName);

  Optional<User> findByEmail(String email);

}
