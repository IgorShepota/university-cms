package ua.foxminded.universitycms.mapping;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import ua.foxminded.universitycms.dto.ClassRoomDTO;
import ua.foxminded.universitycms.model.entity.ClassRoom;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface ClassRoomMapper {

  ClassRoomDTO classRoomToClassRoomDTO(ClassRoom classRoom);

  ClassRoom classRoomDTOToClassRoom(ClassRoomDTO classRoomDTO);

}
