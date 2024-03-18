package ua.foxminded.universitycms.service.classrom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.foxminded.universitycms.model.entity.classroom.ClassRoom;
import ua.foxminded.universitycms.repository.classroom.ClassRoomRepository;
import ua.foxminded.universitycms.service.classroom.ClassRoomServiceImpl;

@SpringBootTest
class ClassRoomServiceImplTest {

  @MockBean
  private ClassRoomRepository classRoomRepository;

  @Autowired
  private ClassRoomServiceImpl classRoomService;

  @Test
  void addClassRoomShouldWorkCorrectlyIfClassRoomEntityCorrect() {

    ClassRoom newClassRoom = ClassRoom.builder().withId("1").withName("101").build();

    classRoomService.addClassRoom(newClassRoom);

    verify(classRoomRepository).save(newClassRoom);
  }

  @Test
  void getClassRoomByIdShouldReturnCorrectClassRoomIfExists() {

    String classRoomId = "1";
    ClassRoom mockClassRoom = ClassRoom.builder().withId(classRoomId).withName("101").build();

    when(classRoomRepository.findById(classRoomId)).thenReturn(Optional.of(mockClassRoom));

    Optional<ClassRoom> result = classRoomService.getClassRoomById(classRoomId);

    assertThat(result).isPresent();
    assertThat(mockClassRoom).isEqualTo(result.get());
  }

  @Test
  void getClassRoomByIdShouldReturnEmptyIfClassRoomDoesNotExist() {

    String nonExistentId = "nonexistent";
    when(classRoomRepository.findById(nonExistentId)).thenReturn(Optional.empty());

    Optional<ClassRoom> result = classRoomService.getClassRoomById(nonExistentId);

    assertThat(result).isEmpty();
  }

  @Test
  void getAllClassRoomsShouldReturnAllClassRooms() {

    List<ClassRoom> mockClassRooms = Arrays.asList(
        ClassRoom.builder().withId("1").withName("101").build(),
        ClassRoom.builder().withId("2").withName("102").build());

    when(classRoomRepository.findAll()).thenReturn(mockClassRooms);

    List<ClassRoom> classRooms = classRoomService.getAllClassRooms();

    assertThat(classRooms).hasSize(2).isEqualTo(mockClassRooms);
  }

  @Test
  void getAllClassRoomsWithPaginationShouldReturnCorrectData() {

    ClassRoom classRoom1 = ClassRoom.builder().withId("1").withName("101").build();
    ClassRoom classRoom2 = ClassRoom.builder().withId("2").withName("102").build();

    List<ClassRoom> classRooms = Arrays.asList(classRoom1, classRoom2);
    Page<ClassRoom> classRoomPage = new PageImpl<>(classRooms);

    when(classRoomRepository.findAll(any(Pageable.class))).thenReturn(classRoomPage);

    List<ClassRoom> returnedClassRooms = classRoomService.getAllClassRooms(1, 2);

    assertThat(returnedClassRooms).hasSize(2).containsExactlyInAnyOrder(classRoom1, classRoom2);

    verify(classRoomRepository).findAll(PageRequest.of(0, 2));
  }

  @Test
  void updateClassRoomShouldCallSaveMethod() {

    ClassRoom classRoomToUpdate = ClassRoom.builder().withId("1").withName("103").build();

    classRoomService.updateClassRoom(classRoomToUpdate);

    verify(classRoomRepository).save(classRoomToUpdate);
  }

  @Test
  void deleteClassRoomShouldWorkCorrectlyIfClassRoomExists() {

    String classRoomId = "1";

    when(classRoomRepository.existsById(classRoomId)).thenReturn(true);

    boolean result = classRoomService.deleteClassRoom(classRoomId);

    assertThat(result).isTrue();
    verify(classRoomRepository).deleteById(classRoomId);
  }

  @Test
  void deleteClassRoomShouldReturnFalseIfClassRoomNotExists() {

    String classRoomId = "nonexistent";

    when(classRoomRepository.existsById(classRoomId)).thenReturn(false);

    boolean result = classRoomService.deleteClassRoom(classRoomId);

    assertThat(result).isFalse();
  }

}
