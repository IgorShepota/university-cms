package ua.foxminded.universitycms.service.user.roles;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.entity.user.roles.Teacher;
import ua.foxminded.universitycms.repository.user.roles.TeacherRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherService {

  private final TeacherRepository teacherRepository;

  @Transactional
  public void addTeacher(Teacher teacher) {
    teacherRepository.save(teacher);
    log.info("Teacher with id {} was successfully saved.", teacher.getId());
  }

  public Optional<Teacher> getTeacherById(String id) {
    log.info("Fetching teacher with id {}.", id);
    return teacherRepository.findById(id);
  }

  public List<Teacher> getAllTeachers() {
    log.info("Fetching all teachers.");
    return teacherRepository.findAll();
  }

  public List<Teacher> getAllTeachers(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of teachers with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);

    return teacherRepository.findAll(pageable).getContent();
  }

  @Transactional
  public void updateTeacher(Teacher teacher) {
    log.info("Updating teacher: {}", teacher);
    teacherRepository.save(teacher);
  }

  @Transactional
  public boolean deleteTeacher(String id) {
    if (teacherRepository.existsById(id)) {
      teacherRepository.deleteById(id);
      log.info("Teacher with id {} was successfully deleted.", id);
      return true;
    } else {
      log.info("Teacher with id {} not found.", id);
      return false;
    }
  }

}
