package ua.foxminded.universitycms.service.user.roles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.foxminded.universitycms.dto.user.roles.StudentDTO;
import ua.foxminded.universitycms.mapping.user.roles.StudentMapper;
import ua.foxminded.universitycms.model.entity.user.roles.Student;
import ua.foxminded.universitycms.repository.user.roles.StudentRepository;
import ua.foxminded.universitycms.service.impl.user.roles.StudentServiceImpl;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

  @Mock
  private StudentRepository studentRepository;

  @Mock
  private StudentMapper studentMapper;

  @InjectMocks
  private StudentServiceImpl studentService;

  @Test
  void addStudentShouldWorkCorrectlyIfStudentEntityCorrect() {
    StudentDTO studentDTO = new StudentDTO();
    Student student = new Student();

    when(studentMapper.studentDTOToStudent(any(StudentDTO.class))).thenReturn(student);
    when(studentRepository.save(any(Student.class))).thenReturn(student);

    studentService.addStudent(studentDTO);

    verify(studentMapper).studentDTOToStudent(studentDTO);
    verify(studentRepository).save(student);
  }

  @Test
  void getStudentByIdShouldReturnStudentDTOWhenStudentExists() {
    String id = "test-id";
    Student student = new Student();
    StudentDTO expectedDTO = new StudentDTO();

    when(studentRepository.findById(id)).thenReturn(Optional.of(student));
    when(studentMapper.studentToStudentDTO(student)).thenReturn(expectedDTO);

    Optional<StudentDTO> result = studentService.getStudentById(id);

    assertThat(result).isPresent();
    assertThat(expectedDTO).isEqualTo(result.get());
  }

  @Test
  void getStudentByIdShouldReturnEmptyOptionalWhenStudentDoesNotExist() {
    String id = "non-existing-id";

    when(studentRepository.findById(id)).thenReturn(Optional.empty());

    Optional<StudentDTO> result = studentService.getStudentById(id);

    assertThat(result).isNotPresent();
  }

  @Test
  void getAllStudentsShouldReturnListOfStudentDTOsWhenStudentsExists() {
    List<Student> students = Arrays.asList(new Student(), new Student());

    when(studentRepository.findAll()).thenReturn(students);
    when(studentMapper.studentToStudentDTO(any(Student.class))).thenAnswer(
        invocation -> new StudentDTO());

    List<StudentDTO> result = studentService.getAllStudents();

    assertThat(result).hasSize(students.size());
  }

  @Test
  void getAllStudentsShouldReturnEmptyListWhenNoStudentsExist() {
    when(studentRepository.findAll()).thenReturn(Collections.emptyList());

    List<StudentDTO> result = studentService.getAllStudents();

    assertThat(result).isEmpty();
  }


  @Test
  void getAllStudentsWithPaginationShouldReturnListOfStudentDTOsWhenStudentsExists() {
    int page = 1;
    int itemsPerPage = 2;
    Pageable pageable = PageRequest.of(0, itemsPerPage);
    List<Student> students = Arrays.asList(new Student(), new Student());
    Page<Student> pagedStudents = new PageImpl<>(students, pageable, students.size());

    when(studentRepository.findAll(pageable)).thenReturn(pagedStudents);
    when(studentMapper.studentToStudentDTO(any(Student.class))).thenAnswer(
        invocation -> new StudentDTO());

    List<StudentDTO> result = studentService.getAllStudents(page, itemsPerPage);

    assertThat(result).hasSize(students.size());
  }

  @Test
  void updateStudentShouldSaveUpdatedStudent() {
    StudentDTO studentDTO = new StudentDTO();
    Student student = new Student();

    when(studentMapper.studentDTOToStudent(any(StudentDTO.class))).thenReturn(student);
    when(studentRepository.save(any(Student.class))).thenReturn(student);

    studentService.updateStudent(studentDTO);

    verify(studentMapper).studentDTOToStudent(studentDTO);
    verify(studentRepository).save(student);
  }

  @Test
  void deleteStudentShouldWorkCorrectlyIfStudentExists() {
    String studentId = "existing-id";

    when(studentRepository.existsById(studentId)).thenReturn(true);

    boolean result = studentService.deleteStudent(studentId);

    assertThat(result).isTrue();
    verify(studentRepository).deleteById(studentId);
  }

  @Test
  void deleteStudentShouldReturnFalseIfStudentNotExists() {
    String studentId = "non-existing-id";

    when(studentRepository.existsById(studentId)).thenReturn(false);

    boolean result = studentService.deleteStudent(studentId);

    assertThat(result).isFalse();
  }

}
