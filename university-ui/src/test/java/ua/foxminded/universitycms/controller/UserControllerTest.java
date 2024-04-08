package ua.foxminded.universitycms.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void showRegistrationFormShouldReturnRegistrationViewWhenRequested() throws Exception {
    mockMvc.perform(get("/user/registration"))
        .andExpect(status().isOk())
        .andExpect(view().name("registration"));
  }

  @Test
  void showLoginFormShouldReturnLoginViewWhenRequested() throws Exception {
    mockMvc.perform(get("/user/login"))
        .andExpect(status().isOk())
        .andExpect(view().name("login"));
  }

}
