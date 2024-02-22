package ua.foxminded.universitycms.service.classrom;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.entity.classroom.ClassRoom;
import ua.foxminded.universitycms.repository.classroom.ClassRoomRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassRoomService {

  private final ClassRoomRepository classRoomRepository;

  @Transactional
  public void addClassRoom(ClassRoom classRoom) {
    classRoomRepository.save(classRoom);
    log.info("ClassRoom with id {} was successfully saved.", classRoom.getId());
  }

  public Optional<ClassRoom> getClassRoomById(String id) {
    log.info("Fetching classRoom with id {}.", id);
    return classRoomRepository.findById(id);
  }

  public List<ClassRoom> getAllClassRooms() {
    log.info("Fetching all classRooms.");
    return classRoomRepository.findAll();
  }

  public List<ClassRoom> getAllClassRooms(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of classRooms with {} items per page.", page, itemsPerPage);

    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);
    return classRoomRepository.findAll(pageable).getContent();
  }

  @Transactional
  public void updateClassRoom(ClassRoom classRoom) {
    log.info("Updating classRoom: {}", classRoom);
    classRoomRepository.save(classRoom);
  }

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
