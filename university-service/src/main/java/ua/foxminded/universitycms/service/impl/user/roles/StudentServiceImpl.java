package ua.foxminded.universitycms.service.impl.user.roles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.mapping.user.roles.StudentMapper;
import ua.foxminded.universitycms.dto.user.roles.StudentDTO;
import ua.foxminded.universitycms.model.entity.user.roles.Student;
import ua.foxminded.universitycms.repository.user.roles.StudentRepository;
import ua.foxminded.universitycms.service.user.roles.StudentService;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;
  private final StudentMapper studentMapper;

  @Override
  @Transactional
  public void addStudent(StudentDTO studentDTO) {
    Student student = studentMapper.studentDTOToStudent(studentDTO);
    studentRepository.save(student);
    log.info("Student with id {} was successfully saved.", student.getId());
  }

  @Override
  public Optional<StudentDTO> getStudentById(String id) {
    log.info("Fetching student with id {}.", id);
    return studentRepository.findById(id)
        .map(studentMapper::studentToStudentDTO);
  }

  @Override
  public List<StudentDTO> getAllStudents() {
    log.info("Fetching all students.");
    List<Student> students = studentRepository.findAll();
    return students.stream()
        .map(studentMapper::studentToStudentDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<StudentDTO> getAllStudents(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of students with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);
    return studentRepository.findAll(pageable).getContent().stream()
        .map(studentMapper::studentToStudentDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void updateStudent(StudentDTO studentDTO) {
    log.info("Updating student: {}", studentDTO);
    Student student = studentMapper.studentDTOToStudent(studentDTO);
    studentRepository.save(student);
  }

  @Override
  @Transactional
  public boolean deleteStudent(String id) {
    if (studentRepository.existsById(id)) {
      studentRepository.deleteById(id);
      log.info("Student with id {} was successfully deleted.", id);
      return true;
    } else {
      log.info("Student with id {} not found.", id);
      return false;
    }
  }

}
