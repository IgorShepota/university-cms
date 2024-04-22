package ua.foxminded.universitycms.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.foxminded.universitycms.model.entity.ClassRoom;

class ClassRoomRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private ClassRoomRepository classRoomRepository;

  @Test
  void findByIdShouldReturnCorrectClassRoomWhenIdExists() {
    String expectedId = "9a9d5f8e-a8d8-11ed-a8fc-0242ac120002";
    Optional<ClassRoom> optionalClassRoom = classRoomRepository.findById(expectedId);
    assertThat(optionalClassRoom).isPresent();
    ClassRoom classRoom = optionalClassRoom.get();
    assertThat(classRoom.getId()).isEqualTo(expectedId);
  }

}
