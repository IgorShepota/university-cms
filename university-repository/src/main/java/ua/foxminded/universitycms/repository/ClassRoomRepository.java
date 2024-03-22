package ua.foxminded.universitycms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.universitycms.model.entity.ClassRoom;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, String> {

}
