package ua.foxminded.universitycms.service.impl.user.roles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.dto.user.roles.TeacherDTO;
import ua.foxminded.universitycms.mapping.user.roles.TeacherMapper;
import ua.foxminded.universitycms.model.entity.user.roles.Teacher;
import ua.foxminded.universitycms.repository.user.roles.TeacherRepository;
import ua.foxminded.universitycms.service.user.roles.TeacherService;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherServiceImpl implements TeacherService {

  private final TeacherRepository teacherRepository;
  private final TeacherMapper teacherMapper;

  @Override
  @Transactional
  public void addTeacher(TeacherDTO teacherDTO) {
    Teacher teacher = teacherMapper.teacherDTOToTeacher(teacherDTO);
    teacher = teacherRepository.save(teacher);
    log.info("Teacher with id {} was successfully saved.", teacher.getId());
  }

  @Override
  public Optional<TeacherDTO> getTeacherById(String id) {
    log.info("Fetching teacher with id {}.", id);
    return teacherRepository.findById(id)
        .map(teacherMapper::teacherToTeacherDTO);
  }

  @Override
  public List<TeacherDTO> getAllTeachers() {
    log.info("Fetching all teachers.");
    List<Teacher> teachers = teacherRepository.findAll();
    return teachers.stream()
        .map(teacherMapper::teacherToTeacherDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<TeacherDTO> getAllTeachers(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of teachers with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);
    return teacherRepository.findAll(pageable).getContent().stream()
        .map(teacherMapper::teacherToTeacherDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void updateTeacher(TeacherDTO teacherDTO) {
    log.info("Updating teacher: {}", teacherDTO);
    Teacher teacher = teacherMapper.teacherDTOToTeacher(teacherDTO);
    teacherRepository.save(teacher);
  }

  @Override
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
