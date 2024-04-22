package ua.foxminded.universitycms.controller;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.foxminded.universitycms.dto.user.role.StudentDTO;
import ua.foxminded.universitycms.service.user.UserService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  void testListStudents() throws Exception {
    List<StudentDTO> students = Arrays.asList(
        StudentDTO.builder()
            .id("1")
            .email("john.doe@example.com")
            .firstName("John")
            .lastName("Doe")
            .gender("Male")
            .roleName("Student")
            .groupName("Computer Science")
            .build(),
        StudentDTO.builder()
            .id("2")
            .email("jane.smith@example.com")
            .firstName("Jane")
            .lastName("Smith")
            .gender("Female")
            .roleName("Student")
            .groupName("Mathematics")
            .build()
    );

    Mockito.when(userService.getAllStudents()).thenReturn(students);

    mockMvc.perform(MockMvcRequestBuilders.get("/students"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("students"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("students"))
        .andExpect(MockMvcResultMatchers.model().attribute("students", students));
  }

}
