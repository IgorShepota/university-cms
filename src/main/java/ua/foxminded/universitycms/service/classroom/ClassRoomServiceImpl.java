package ua.foxminded.universitycms.service.classroom;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.model.mapping.classroom.ClassRoomMapper;
import ua.foxminded.universitycms.model.dto.classroom.ClassRoomDTO;
import ua.foxminded.universitycms.model.entity.classroom.ClassRoom;
import ua.foxminded.universitycms.repository.classroom.ClassRoomRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassRoomServiceImpl implements ClassRoomService {

  private final ClassRoomRepository classRoomRepository;
  private final ClassRoomMapper classRoomMapper;

  @Override
  @Transactional
  public void addClassRoom(ClassRoomDTO classRoomDTO) {
    ClassRoom classRoom = classRoomMapper.classRoomDTOToClassRoom(classRoomDTO);
    classRoomRepository.save(classRoom);
    log.info("ClassRoom with id {} was successfully saved.", classRoomDTO.getId());
  }

  @Override
  public Optional<ClassRoomDTO> getClassRoomById(String id) {
    log.info("Fetching classRoom with id {}.", id);
    return classRoomRepository.findById(id).map(classRoomMapper::classRoomToClassRoomDTO);
  }

  @Override
  public List<ClassRoomDTO> getAllClassRooms() {
    log.info("Fetching all classRooms.");
    return classRoomRepository.findAll().stream()
        .map(classRoomMapper::classRoomToClassRoomDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<ClassRoomDTO> getAllClassRooms(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of classRooms with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);
    return classRoomRepository.findAll(pageable).getContent().stream()
        .map(classRoomMapper::classRoomToClassRoomDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void updateClassRoom(ClassRoomDTO classRoomDTO) {
    log.info("Updating classRoom: {}", classRoomDTO);
    ClassRoom classRoom = classRoomMapper.classRoomDTOToClassRoom(classRoomDTO);
    classRoomRepository.save(classRoom);
  }

  @Override
  @Transactional
  public boolean deleteClassRoom(String id) {
    if (classRoomRepository.existsById(id)) {
      classRoomRepository.deleteById(id);
      log.info("ClassRoom with id {} was successfully deleted.", id);
      return true;
    } else {
      log.info("ClassRoom with id {} not found.", id);
      return false;
    }
  }

}
