package ua.foxminded.universitycms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.universitycms.model.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {

}
