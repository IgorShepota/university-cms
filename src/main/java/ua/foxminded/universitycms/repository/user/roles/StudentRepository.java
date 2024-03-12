package ua.foxminded.universitycms.repository.user.roles;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.foxminded.universitycms.entity.user.roles.Student;

public interface StudentRepository extends JpaRepository<Student, String> {

}
