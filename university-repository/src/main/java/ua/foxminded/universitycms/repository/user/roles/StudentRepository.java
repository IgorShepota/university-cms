package ua.foxminded.universitycms.repository.user.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.universitycms.model.entity.user.roles.Student;

public interface StudentRepository extends JpaRepository<Student, String> {

}
