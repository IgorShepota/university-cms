package ua.foxminded.universitycms.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.foxminded.universitycms.dto.user.role.StudentResponseDTO;
import ua.foxminded.universitycms.model.entity.user.Gender;
import ua.foxminded.universitycms.service.user.UserService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  @WithMockUser
  void listStudentsShouldReturnPageWithListedStudents() throws Exception {
    String sort = "id";
    String order = "asc";

    List<StudentResponseDTO> students = Arrays.asList(
        StudentResponseDTO.builder()
            .id("1")
            .email("john.doe@example.com")
            .firstName("John")
            .lastName("Doe")
            .gender(Gender.MALE)
            .roleName("Student")
            .groupName("Computer Science")
            .creationDateTime(LocalDateTime.now())
            .build(),
        StudentResponseDTO.builder()
            .id("2")
            .email("jane.smith@example.com")
            .firstName("Jane")
            .lastName("Smith")
            .gender(Gender.FEMALE)
            .roleName("Student")
            .groupName("Mathematics")
            .creationDateTime(LocalDateTime.now())
            .build()
    );

    Mockito.when(userService.getAllStudentsSorted(sort, order)).thenReturn(students);

    mockMvc.perform(MockMvcRequestBuilders.get("/students"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("students"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("students"))
        .andExpect(MockMvcResultMatchers.model().attribute("students", students));
  }

}
