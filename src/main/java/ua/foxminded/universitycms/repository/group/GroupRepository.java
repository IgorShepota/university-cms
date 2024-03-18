package ua.foxminded.universitycms.repository.group;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.universitycms.model.entity.group.Group;

public interface GroupRepository extends JpaRepository<Group, String> {

}
