package ua.foxminded.universitycms.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import ua.foxminded.universitycms.dto.user.roles.StudentDTO;
import ua.foxminded.universitycms.service.user.roles.StudentService;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

  @Mock
  private StudentService studentServiceMock;

  @Mock
  private Model modelMock;

  @InjectMocks
  private StudentController studentController;

  private List<StudentDTO> studentDTOs;

  @BeforeEach
  void setUp() {
    StudentDTO studentDTO1 = StudentDTO.builder().build();
    StudentDTO studentDTO2 = StudentDTO.builder().build();
    studentDTOs = Arrays.asList(studentDTO1, studentDTO2);
  }

  @Test
  void listStudentsShouldReturnStudentsViewWithModelWhenCalled() {
    when(studentServiceMock.getAllStudents()).thenReturn(studentDTOs);

    String viewName = studentController.listStudents(modelMock);

    assertThat(viewName).isEqualTo("students");
    verify(modelMock, times(1)).addAttribute("students", studentDTOs);
  }

  @Test
  void getAllStudentsShouldReturnListOfStudentDTOsWhenCalled() {
    List<StudentDTO> expectedStudentDTOs = studentDTOs;

    when(studentServiceMock.getAllStudents()).thenReturn(expectedStudentDTOs);

    List<StudentDTO> result = studentServiceMock.getAllStudents();

    assertThat(result).isEqualTo(expectedStudentDTOs);
  }

}
