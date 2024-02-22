package ua.foxminded.universitycms.repository.user.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.universitycms.entity.user.roles.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, String> {

}
