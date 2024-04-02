package ua.foxminded.universitycms.repository.user.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.universitycms.model.entity.user.roles.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

}
