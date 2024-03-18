package ua.foxminded.universitycms.model.mapping.classroom;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import ua.foxminded.universitycms.model.dto.classroom.ClassRoomDTO;
import ua.foxminded.universitycms.model.entity.classroom.ClassRoom;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ClassRoomMapper {

  ClassRoomDTO classRoomToClassRoomDTO(ClassRoom classRoom);

  ClassRoom classRoomDTOToClassRoom(ClassRoomDTO classRoomDTO);

}
