package ua.foxminded.universitycms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ua.foxminded.universitycms.dto.ClassRoomDTO;
import ua.foxminded.universitycms.mapping.ClassRoomMapper;
import ua.foxminded.universitycms.model.entity.ClassRoom;
import ua.foxminded.universitycms.repository.ClassRoomRepository;
import ua.foxminded.universitycms.service.impl.ClassRoomServiceImpl;

@ExtendWith(MockitoExtension.class)
class ClassRoomServiceImplTest {

  @Mock
  private ClassRoomRepository classRoomRepository;

  @Mock
  private ClassRoomMapper classRoomMapper;

  @InjectMocks
  private ClassRoomServiceImpl classRoomService;

  @Test
  void addClassRoomShouldWorkCorrectlyIfClassRoomDTOCorrect() {
    ClassRoomDTO classRoomDTO = ClassRoomDTO.builder().id("7e41c62b-222e-4a92-a3c1-f1b6b634b32d")
        .build();
    ClassRoom classRoom = ClassRoom.builder().withId(classRoomDTO.getId())
        .build();

    when(classRoomMapper.classRoomDTOToClassRoom(any(ClassRoomDTO.class))).thenReturn(classRoom);

    classRoomService.addClassRoom(classRoomDTO);

    ArgumentCaptor<ClassRoom> classRoomCaptor = ArgumentCaptor.forClass(ClassRoom.class);
    verify(classRoomRepository).save(classRoomCaptor.capture());
    ClassRoom capturedClassRoom = classRoomCaptor.getValue();

    assertThat(capturedClassRoom.getId()).isEqualTo(classRoomDTO.getId());
  }

  @Test
  void getClassRoomByIdShouldReturnClassRoomDTOWhenClassRoomExists() {
    String id = "7e41c62b-222e-4a92-a3c1-f1b6b634b32d";
    String name = "101";
    ClassRoom classRoom = ClassRoom.builder().withId(id).withName(name).build();
    ClassRoomDTO classRoomDTO = ClassRoomDTO.builder().id(id).name(name).build();

    when(classRoomRepository.findById(id)).thenReturn(Optional.of(classRoom));
    when(classRoomMapper.classRoomToClassRoomDTO(classRoom)).thenReturn(classRoomDTO);

    Optional<ClassRoomDTO> result = classRoomService.getClassRoomById(id);

    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(id);
  }

  @Test
  void getClassRoomByIdShouldReturnEmptyOptionalWhenClassRoomDoesNotExist() {
    String nonExistentId = "nonexistent";
    when(classRoomRepository.findById(nonExistentId)).thenReturn(Optional.empty());

    Optional<ClassRoomDTO> result = classRoomService.getClassRoomById(nonExistentId);

    assertThat(result).isNotPresent();
  }

  @Test
  void getAllClassRoomsShouldReturnListOfClassRoomDTOsWhenClassRoomsExist() {
    ClassRoom classRoom1 = ClassRoom.builder().withId("7e41c62b-222e-4a92-a3c1-f1b6b634b32d")
        .withName("101").build();
    ClassRoom classRoom2 = ClassRoom.builder().withId("8e41c62b-222e-4a92-a3c1-f1b6b634b32d")
        .withName("102").build();
    ClassRoomDTO classRoomDTO1 = ClassRoomDTO.builder().id(classRoom1.getId())
        .name("101").build();
    ClassRoomDTO classRoomDTO2 = ClassRoomDTO.builder().id(classRoom2.getId())
        .name("101").build();

    when(classRoomRepository.findAll()).thenReturn(Arrays.asList(classRoom1, classRoom2));
    when(classRoomMapper.classRoomToClassRoomDTO(classRoom1)).thenReturn(classRoomDTO1);
    when(classRoomMapper.classRoomToClassRoomDTO(classRoom2)).thenReturn(classRoomDTO2);

    List<ClassRoomDTO> result = classRoomService.getAllClassRooms();

    assertThat(result).hasSize(2)
        .containsExactly(classRoomDTO1, classRoomDTO2);
  }

  @Test
  void getAllClassRoomsShouldReturnEmptyListWhenNoClassRoomsExist() {
    when(classRoomRepository.findAll()).thenReturn(Collections.emptyList());

    List<ClassRoomDTO> result = classRoomService.getAllClassRooms();

    assertThat(result).isEmpty();
  }

  @Test
  void getAllClassRoomsShouldReturnCorrectPageOfClassRoomDTOs() {
    Integer page = 1;
    Integer itemsPerPage = 2;
    ClassRoom classRoom1 = ClassRoom.builder().withId("7e41c62b-222e-4a92-a3c1-f1b6b634b32d")
        .withName("101").build();
    ClassRoom classRoom2 = ClassRoom.builder().withId("8e41c62b-222e-4a92-a3c1-f1b6b634b32d")
        .withName("102").build();
    ClassRoomDTO classRoomDTO1 = ClassRoomDTO.builder().id(classRoom1.getId())
        .name(classRoom1.getName()).build();
    ClassRoomDTO classRoomDTO2 = ClassRoomDTO.builder().id(classRoom2.getId())
        .name(classRoom2.getName()).build();
    Page<ClassRoom> classRoomPage = new PageImpl<>(Arrays.asList(classRoom1, classRoom2));

    when(classRoomRepository.findAll(any(Pageable.class))).thenReturn(classRoomPage);
    when(classRoomMapper.classRoomToClassRoomDTO(classRoom1)).thenReturn(classRoomDTO1);
    when(classRoomMapper.classRoomToClassRoomDTO(classRoom2)).thenReturn(classRoomDTO2);

    List<ClassRoomDTO> result = classRoomService.getAllClassRooms(page, itemsPerPage);

    assertThat(result).hasSize(2)
        .containsExactly(classRoomDTO1, classRoomDTO2);
  }

  @Test
  void updateClassRoomShouldCorrectlyMapAndSaveClassRoom() {
    ClassRoom classRoomToUpdate = ClassRoom.builder().withId("8e41c62b-222e-4a92-a3c1-f1b6b634b32d")
        .withName("1035").build();
    ClassRoomDTO classRoomDTOToUpdate = ClassRoomDTO.builder()
        .id(classRoomToUpdate.getId())
        .name(classRoomToUpdate.getName()).build();

    when(classRoomMapper.classRoomDTOToClassRoom(any(ClassRoomDTO.class))).thenReturn(
        classRoomToUpdate);

    classRoomService.updateClassRoom(classRoomDTOToUpdate);

    verify(classRoomMapper).classRoomDTOToClassRoom(classRoomDTOToUpdate);
    verify(classRoomRepository).save(classRoomToUpdate);
  }

  @Test
  void deleteClassRoomShouldReturnTrueWhenClassRoomExists() {
    String id = "existing-id";
    when(classRoomRepository.existsById(id)).thenReturn(true);

    boolean result = classRoomService.deleteClassRoom(id);

    verify(classRoomRepository).deleteById(id);
    assertThat(result).isTrue();
  }

  @Test
  void deleteClassRoomShouldReturnFalseWhenClassRoomDoesNotExist() {
    String id = "non-existing-id";
    when(classRoomRepository.existsById(id)).thenReturn(false);

    boolean result = classRoomService.deleteClassRoom(id);

    verify(classRoomRepository, never()).deleteById(id);
    assertThat(result).isFalse();
  }

}
