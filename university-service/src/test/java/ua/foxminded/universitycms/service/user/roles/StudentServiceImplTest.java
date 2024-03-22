//package ua.foxminded.universitycms.service.user.roles;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import ua.foxminded.universitycms.model.entity.Group;
//import ua.foxminded.universitycms.model.entity.user.roles.Student;
//import ua.foxminded.universitycms.repository.user.roles.StudentRepository;
//import ua.foxminded.universitycms.service.impl.user.roles.StudentServiceImpl;
//
//@SpringBootTest
//class StudentServiceImplTest {
//
//  @MockBean
//  private StudentRepository studentRepository;
//
//  @Autowired
//  private StudentServiceImpl studentServiceImpl;
//
//  @Test
//  void addStudentShouldWorkCorrectlyIfStudentEntityCorrect() {
//
//    Group group = new Group();
//    Student newStudent = Student.builder().withId("1").withFirstName("John").withLastName("Doe")
//        .withEmail("john.doe@example.com").withGender("Male").withOwnerGroup(group).build();
//
//    studentServiceImpl.addStudent(newStudent);
//
//    verify(studentRepository).save(newStudent);
//  }
//
//  @Test
//  void getStudentByIdShouldReturnCorrectStudentIfExists() {
//
//    String studentId = "1";
//    Group group = new Group();
//    Student mockStudent = Student.builder().withId(studentId).withFirstName("John")
//        .withLastName("Doe").withEmail("john.doe@example.com").withGender("Male")
//        .withOwnerGroup(group).build();
//
//    when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));
//
//    Optional<Student> result = studentServiceImpl.getStudentById(studentId);
//
//    assertThat(result).isPresent();
//    assertThat(mockStudent).isEqualTo(result.get());
//  }
//
//  @Test
//  void getStudentByIdShouldReturnEmptyIfStudentDoesNotExist() {
//
//    String nonExistentId = "nonexistent";
//    when(studentRepository.findById(nonExistentId)).thenReturn(Optional.empty());
//
//    Optional<Student> result = studentServiceImpl.getStudentById(nonExistentId);
//
//    assertThat(result).isEmpty();
//  }
//
//  @Test
//  void getAllStudentsShouldReturnAllStudents() {
//
//    Group group = new Group();
//    List<Student> mockStudents = Arrays.asList(
//        Student.builder().withId("1").withFirstName("John").withLastName("Doe")
//            .withOwnerGroup(group).build(),
//        Student.builder().withId("2").withFirstName("Jane").withLastName("Doe")
//            .withOwnerGroup(group).build());
//
//    when(studentRepository.findAll()).thenReturn(mockStudents);
//
//    List<Student> students = studentServiceImpl.getAllStudents();
//
//    assertThat(students).hasSize(2).isEqualTo(mockStudents);
//  }
//
//  @Test
//  void getAllStudentsWithPaginationShouldReturnCorrectData() {
//
//    Group group = new Group();
//    Student student1 = Student.builder().withId("1").withFirstName("John").withLastName("Doe")
//        .withOwnerGroup(group).build();
//    Student student2 = Student.builder().withId("2").withFirstName("Jane").withLastName("Doe")
//        .withOwnerGroup(group).build();
//
//    List<Student> students = Arrays.asList(student1, student2);
//    Page<Student> studentPage = new PageImpl<>(students);
//
//    when(studentRepository.findAll(any(Pageable.class))).thenReturn(studentPage);
//
//    List<Student> returnedStudents = studentServiceImpl.getAllStudents(1, 2);
//
//    assertThat(returnedStudents).hasSize(2).containsExactlyInAnyOrder(student1, student2);
//
//    verify(studentRepository).findAll(PageRequest.of(0, 2));
//  }
//
//  @Test
//  void updateStudentShouldCallSaveMethod() {
//
//    Group group = new Group();
//    Student studentToUpdate = Student.builder().withId("1").withFirstName("John")
//        .withLastName("Doe").withEmail("update@example.com").withGender("Male")
//        .withOwnerGroup(group).build();
//
//    studentServiceImpl.updateStudent(studentToUpdate);
//
//    verify(studentRepository).save(studentToUpdate);
//  }
//
//  @Test
//  void deleteStudentShouldWorkCorrectlyIfStudentExists() {
//
//    String studentId = "1";
//
//    when(studentRepository.existsById(studentId)).thenReturn(true);
//
//    boolean result = studentServiceImpl.deleteStudent(studentId);
//
//    assertThat(result).isTrue();
//    verify(studentRepository).deleteById(studentId);
//  }
//
//  @Test
//  void deleteStudentShouldReturnFalseIfStudentNotExists() {
//
//    String studentId = "nonexistent";
//
//    when(studentRepository.existsById(studentId)).thenReturn(false);
//
//    boolean result = studentServiceImpl.deleteStudent(studentId);
//
//    assertThat(result).isFalse();
//  }
//
//}
