package ua.foxminded.universitycms.repository.classroom;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.universitycms.entity.classroom.ClassRoom;

public interface ClassRoomRepository extends JpaRepository<ClassRoom, String> {

}