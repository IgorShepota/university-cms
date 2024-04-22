package ua.foxminded.universitycms.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.universitycms.dto.user.UserRegistrationDTO;
import ua.foxminded.universitycms.service.user.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  void showRegistrationFormShouldRenderRegistrationPage() throws Exception {
    mockMvc.perform(get("/user/registration"))
        .andExpect(status().isOk())
        .andExpect(view().name("user/registration"))
        .andExpect(model().attributeExists("user"));
  }

  @Test
  void registerUserShouldRedirectToLoginIfRegistrationWasSuccessful() throws Exception {
    mockMvc.perform(post("/user/registration")
            .param("email", "test@example.com")
            .param("password", "Password123!")
            .param("firstName", "John")
            .param("lastName", "Doe")
            .param("gender", "Male"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/user/login"));

    verify(userService).registerUser(any(UserRegistrationDTO.class));
  }

  @Test
  void registerUserShouldReturnRegistrationPageIfRegistrationFailed() throws Exception {
    mockMvc.perform(post("/user/registration")
            .param("email", "invalid-email")
            .param("password", "pass") // Invalid password
            .param("firstName", "1234") // Invalid first name
            .param("lastName", "5678") // Invalid last name
            .param("gender", "Male"))
        .andExpect(status().isOk())
        .andExpect(view().name("user/registration"));

    verify(userService, never()).registerUser(any(UserRegistrationDTO.class));
  }

  @Test
  void showLoginFormShouldRenderLoginPage() throws Exception {
    mockMvc.perform(get("/user/login"))
        .andExpect(status().isOk())
        .andExpect(view().name("user/login"));
  }

}
