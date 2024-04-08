package ua.foxminded.universitycms.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.universitycms.dto.user.roles.TeacherDTO;
import ua.foxminded.universitycms.service.user.roles.TeacherService;

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TeacherService teacherService;

  @Test
  void listTeachersShouldReturnTeachersViewWithModelWhenCalled() throws Exception {
    List<TeacherDTO> teachers = Arrays.asList(
        new TeacherDTO(),
        new TeacherDTO()
    );

    when(teacherService.getAllTeachers()).thenReturn(teachers);

    mockMvc.perform(get("/teachers"))
        .andExpect(status().isOk())
        .andExpect(view().name("teachers"))
        .andExpect(model().attributeExists("teachers"))
        .andExpect(model().attribute("teachers", teachers));
  }

}
