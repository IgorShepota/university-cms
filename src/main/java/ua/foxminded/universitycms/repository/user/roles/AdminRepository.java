package ua.foxminded.universitycms.repository.user.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.universitycms.entity.user.roles.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {

}
