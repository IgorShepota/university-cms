package ua.foxminded.universitycms.mapping;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import ua.foxminded.universitycms.dto.ClassRoomDTO;
import ua.foxminded.universitycms.model.entity.ClassRoom;

class ClassRoomMapperTest {

  private final ClassRoomMapper mapper = new ClassRoomMapperImpl();

  @Test
  void classRoomToClassRoomDTOShouldWorkCorrectlyIfDataCorrect() {
    ClassRoom classRoom = new ClassRoom();
    String uuid = UUID.randomUUID().toString();
    classRoom.setId(uuid);
    classRoom.setName("101");

    ClassRoomDTO classRoomDTO = mapper.classRoomToClassRoomDTO(classRoom);

    assertThat(classRoomDTO.getId()).isEqualTo(classRoom.getId());
    assertThat(classRoomDTO.getName()).isEqualTo(classRoom.getName());
  }

  @Test
  void classRoomDTOToClassRoomShouldWorkCorrectlyIfDataCorrect() {
    ClassRoomDTO classRoomDTO = new ClassRoomDTO();
    String uuid = UUID.randomUUID().toString();
    classRoomDTO.setId(uuid);
    classRoomDTO.setName("101");

    ClassRoom classRoom = mapper.classRoomDTOToClassRoom(classRoomDTO);

    assertThat(classRoom.getId()).isEqualTo(classRoomDTO.getId());
    assertThat(classRoom.getName()).isEqualTo(classRoomDTO.getName());
  }

  @Test
  void classRoomToClassRoomDTOShouldReturnNullWhenClassRoomIsNull() {
    assertThat(mapper.classRoomToClassRoomDTO(null)).isNull();
  }

  @Test
  void classRoomDTOToClassRoomShouldReturnNullWhenClassRoomDTOIsNull() {
    assertThat(mapper.classRoomDTOToClassRoom(null)).isNull();
  }

}
