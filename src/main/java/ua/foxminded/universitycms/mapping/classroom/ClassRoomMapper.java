package ua.foxminded.universitycms.mapping.classroom;

import org.mapstruct.factory.Mappers;
import ua.foxminded.universitycms.dto.classroom.ClassRoomDTO;
import ua.foxminded.universitycms.entity.classroom.ClassRoom;

public interface ClassRoomMapper {

  ClassRoomMapper INSTANCE = Mappers.getMapper(ClassRoomMapper.class);

  ClassRoomDTO classRoomToClassRoomDTO(ClassRoom classRoom);

  ClassRoom classRoomDTOToClassRoom(ClassRoomDTO classRoomDTO);

}
