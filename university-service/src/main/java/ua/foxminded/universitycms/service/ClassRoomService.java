package ua.foxminded.universitycms.service;

import java.util.List;
import java.util.Optional;
import ua.foxminded.universitycms.dto.ClassRoomDTO;

public interface ClassRoomService {

  void addClassRoom(ClassRoomDTO classRoomDTO);

  Optional<ClassRoomDTO> getClassRoomById(String id);

  List<ClassRoomDTO> getAllClassRooms();

  List<ClassRoomDTO> getAllClassRooms(Integer page, Integer itemsPerPage);

  void updateClassRoom(ClassRoomDTO classRoomDTO);

  boolean deleteClassRoom(String id);

}
