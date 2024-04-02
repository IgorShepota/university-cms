package ua.foxminded.universitycms.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.universitycms.model.entity.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
