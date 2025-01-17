package ua.foxminded.universitycms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.universitycms.dto.user.UserResponseDTO;
import ua.foxminded.universitycms.model.entity.user.Gender;
import ua.foxminded.universitycms.service.user.UserService;

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  @WithMockUser
  void listStudentsShouldReturnPageWithListedTeachers() throws Exception {
    List<UserResponseDTO> teachers = Arrays.asList(
        new UserResponseDTO("1", "teacher@example.com", "John", "Doe", Gender.MALE, "TEACHER",
            LocalDateTime.now()),
        new UserResponseDTO("2", "teacher2@example.com", "Jane", "Doe", Gender.FEMALE, "TEACHER",
            LocalDateTime.now())
    );

    when(userService.getAllTeachers()).thenReturn(teachers);

    mockMvc.perform(get("/teachers"))
        .andExpect(status().isOk())
        .andExpect(view().name("teachers"))
        .andExpect(model().attributeExists("teachers"))
        .andExpect(model().attribute("teachers", teachers));
  }

}
