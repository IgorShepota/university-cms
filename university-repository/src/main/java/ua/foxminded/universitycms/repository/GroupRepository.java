package ua.foxminded.universitycms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.foxminded.universitycms.model.entity.Group;
import ua.foxminded.universitycms.model.entity.GroupStatus;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {

  boolean existsByNameIgnoreCase(String name);

  @Query("SELECT DISTINCT g FROM Group g LEFT JOIN FETCH g.students WHERE g.status = :status")
  List<Group> findAllByStatusWithStudents(@Param("status") GroupStatus status);

}
