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
import ua.foxminded.universitycms.dto.user.UserDTO;
import ua.foxminded.universitycms.service.user.UserService;

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  void testListTeachers() throws Exception {
    List<UserDTO> teachers = Arrays.asList(
        new UserDTO("1", "john.doe@example.com", "John", "Doe", "Male", "Professor"),
        new UserDTO("2", "jane.doe@example.com", "Jane", "Doe", "Female", "Assistant Professor")
    );

    Mockito.when(userService.getAllTeachers()).thenReturn(teachers);

    mockMvc.perform(MockMvcRequestBuilders.get("/teachers"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("teachers"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("teachers"))
        .andExpect(MockMvcResultMatchers.model().attribute("teachers", teachers));
  }

}
