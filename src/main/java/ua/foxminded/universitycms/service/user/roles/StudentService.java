package ua.foxminded.universitycms.service.user.roles;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.entity.user.roles.Student;
import ua.foxminded.universitycms.repository.user.roles.StudentRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

  private final StudentRepository studentRepository;

  @Transactional
  public void addStudent(Student student) {
    studentRepository.save(student);
    log.info("Student with id {} was successfully saved.", student.getId());
  }

  public Optional<Student> getStudentById(String id) {
    log.info("Fetching student with id {}.", id);
    return studentRepository.findById(id);
  }

  public List<Student> getAllStudents() {
    log.info("Fetching all students.");
    return studentRepository.findAll();
  }

  public List<Student> getAllStudents(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of students with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);
    return studentRepository.findAll(pageable).getContent();
  }

  @Transactional
  public void updateStudent(Student student) {
    log.info("Updating student: {}", student);
    studentRepository.save(student);
  }

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
