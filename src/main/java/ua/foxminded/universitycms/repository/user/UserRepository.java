package ua.foxminded.universitycms.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.universitycms.entity.user.User;

public interface UserRepository extends JpaRepository<User, String> {

}
