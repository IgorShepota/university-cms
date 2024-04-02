package ua.foxminded.universitycms.repository.user.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.universitycms.model.entity.user.roles.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {

}
