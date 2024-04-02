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
    String expectedId = "7e41c62b-222e-4a92-a3c1-f1b6b634b32d";
    Optional<ClassRoom> optionalClassRoom = classRoomRepository.findById(expectedId);
    assertThat(optionalClassRoom).isPresent();
    ClassRoom classRoom = optionalClassRoom.get();
    assertThat(classRoom.getId()).isEqualTo(expectedId);
  }

}
