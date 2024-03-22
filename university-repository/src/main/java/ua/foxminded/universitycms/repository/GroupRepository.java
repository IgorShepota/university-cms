package ua.foxminded.universitycms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.universitycms.model.entity.Group;

public interface GroupRepository extends JpaRepository<Group, String> {

}
