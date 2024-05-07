package ua.foxminded.universitycms.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.universitycms.dto.user.UserDTO;
import ua.foxminded.universitycms.dto.user.UserRegistrationDTO;
import ua.foxminded.universitycms.service.user.UserService;

@WebMvcTest(UserController.class)
@WithMockUser
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
            .with(csrf())
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
            .with(csrf())
            .param("email", "invalid-email")
            .param("password", "pass")
            .param("firstName", "1234")
            .param("lastName", "5678")
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

  @Test
  void listUsersShouldReturnAscendingSortIfWasDescending() throws Exception {
    List<UserDTO> sampleUsers = Arrays.asList(
        new UserDTO("1", "user1@example.com", "Bob", "Doe", "Male", "ADMIN", LocalDateTime.now()),
        new UserDTO("2", "user2@example.com", "Jane", "Doe", "Female", "USER",
            LocalDateTime.now()));

    when(userService.getAllUsersSorted("creationDateTime", "asc")).thenReturn(sampleUsers);

    mockMvc.perform(get("/user/list")
            .param("sort", "creationDateTime")
            .param("order", "asc"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("users", "sortField", "sortOrder"))
        .andExpect(model().attribute("users", hasSize(2)))
        .andExpect(model().attribute("sortField", "creationDateTime"))
        .andExpect(model().attribute("sortOrder", "asc"))
        .andExpect(view().name("user/list"));

    verify(userService).getAllUsersSorted("creationDateTime", "asc");
  }

  @Test
  void listUsersShouldReturnDescendingSortIfWasAscending() throws Exception {
    List<UserDTO> sampleUsers = Arrays.asList(
        new UserDTO("1", "user1@example.com", "John", "Doe", "Male", "Admin", LocalDateTime.now()),
        new UserDTO("2", "user2@example.com", "Jane", "Doe", "Female", "User",
            LocalDateTime.now()));

    when(userService.getAllUsersSorted("lastName", "desc")).thenReturn(sampleUsers);

    mockMvc.perform(get("/user/list")
            .param("sort", "lastName")
            .param("order", "desc"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("sortField", "lastName"))
        .andExpect(model().attribute("sortOrder", "desc"))
        .andExpect(view().name("user/list"));

    verify(userService).getAllUsersSorted("lastName", "desc");
  }

  @Test
  void changeUserRoleShouldReturnOkIfSuccessful() throws Exception {
    doNothing().when(userService).updateUserRole(any(String.class), any(String.class));

    mockMvc.perform(post("/user/change-role")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"userId\":\"1\", \"roleName\":\"ADMIN\"}"))
        .andExpect(status().isOk());
  }

  @Test
  void changeUserRoleShouldReturnInternalServerErrorIfExceptionThrown() throws Exception {
    doThrow(new RuntimeException("Error updating role")).when(userService)
        .updateUserRole(any(String.class), any(String.class));

    mockMvc.perform(post("/user/change-role")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"userId\":\"1\", \"roleName\":\"ADMIN\"}"))
        .andExpect(status().isInternalServerError());
  }

}
