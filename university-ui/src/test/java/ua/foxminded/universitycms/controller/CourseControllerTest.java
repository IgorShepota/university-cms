package ua.foxminded.universitycms.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ua.foxminded.universitycms.controller.config.SecurityConfig;
import ua.foxminded.universitycms.dto.CourseDTO;
import ua.foxminded.universitycms.service.CourseService;

@WebMvcTest(CourseController.class)
@Import(SecurityConfig.class)
class CourseControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CourseService courseService;

  @Test
  @WithAnonymousUser
  void listCoursesShouldReturnAllActiveCoursesWhenUserDetailsIsNull() throws Exception {
    List<CourseDTO> courses = Arrays.asList(new CourseDTO(), new CourseDTO());
    when(courseService.getAllActiveCourses()).thenReturn(courses);

    mockMvc.perform(get("/courses"))
        .andExpect(status().isOk())
        .andExpect(view().name("courses"))
        .andExpect(model().attribute("courses", courses))
        .andExpect(model().attributeExists("course"));

    verify(courseService, times(1)).getAllActiveCourses();
    verify(courseService, never()).getAllCourses();
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void listCoursesShouldReturnAllCoursesWhenUserIsAdmin() throws Exception {
    List<CourseDTO> allCourses = Arrays.asList(new CourseDTO(), new CourseDTO());
    when(courseService.getAllCourses()).thenReturn(allCourses);

    mockMvc.perform(get("/courses"))
        .andExpect(status().isOk())
        .andExpect(view().name("courses"))
        .andExpect(model().attribute("courses", allCourses))
        .andExpect(model().attributeExists("course"));

    verify(courseService, times(1)).getAllCourses();
    verify(courseService, times(0)).getAllActiveCourses();
  }

  @Test
  @WithMockUser(roles = "USER")
  void listCoursesShouldReturnAllActiveCoursesWhenUserIsNotAdmin() throws Exception {
    List<CourseDTO> activeCourses = Arrays.asList(new CourseDTO(), new CourseDTO());
    when(courseService.getAllActiveCourses()).thenReturn(activeCourses);

    mockMvc.perform(get("/courses"))
        .andExpect(status().isOk())
        .andExpect(view().name("courses"))
        .andExpect(model().attribute("courses", activeCourses))
        .andExpect(model().attributeExists("course"));

    verify(courseService, times(1)).getAllActiveCourses();
    verify(courseService, times(0)).getAllCourses();
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void showAddCourseFormShouldReturnCoursesViewWhenUserIsAdmin() throws Exception {
    mockMvc.perform(get("/courses/add"))
        .andExpect(status().isOk())
        .andExpect(view().name("courses"))
        .andExpect(model().attributeExists("course"));
  }

  @Test
  @WithMockUser(roles = "USER")
  void showAddCourseFormShouldReturnForbiddenWhenUserIsNotAdmin() throws Exception {
    mockMvc.perform(get("/courses/add"))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithAnonymousUser
  void showAddCourseFormShouldRedirectToLoginWhenUserIsAnonymous() throws Exception {
    mockMvc.perform(get("/courses/add"))
        .andExpect(status().isFound()) // 302 Redirect
        .andExpect(redirectedUrl("http://localhost/user/login")); // Redirect URL
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void addCourseShouldRedirectToCoursesWhenAdminAndValidCourse() throws Exception {
    mockMvc.perform(post("/courses/add")
            .param("name", "Test Course")
            .param("description", "Test Description")
            .param("status", "ACTIVE")
            .with(csrf()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/courses"));

    verify(courseService, times(1)).addCourse(any(CourseDTO.class));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void addCourseShouldReturnToCoursesViewWhenAdminAndInvalidCourse() throws Exception {
    mockMvc.perform(post("/courses/add")
            .param("name", "")  // Invalid name
            .param("description", "Test Description")
            .param("status", "ACTIVE")
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(view().name("courses"))
        .andExpect(model().attributeHasFieldErrors("course", "name"));

    verify(courseService, times(0)).addCourse(any(CourseDTO.class));
  }

  @Test
  @WithMockUser(roles = "USER")
  void addCourseShouldReturnForbiddenWhenUserIsNotAdmin() throws Exception {
    mockMvc.perform(post("/courses/add")
            .param("name", "Test Course")
            .param("description", "Test Description")
            .param("status", "ACTIVE")
            .with(csrf()))
        .andExpect(status().isForbidden());

    verify(courseService, times(0)).addCourse(any(CourseDTO.class));
  }

  @Test
  @WithAnonymousUser
  void addCourseShouldReturnUnauthorizedWhenUserIsAnonymous() throws Exception {
    mockMvc.perform(post("/courses/add")
            .param("name", "Test Course")
            .param("description", "Test Description")
            .param("status", "ACTIVE")
            .with(csrf()))
        .andExpect(status().isFound()) // 302 Redirect
        .andExpect(redirectedUrl("http://localhost/user/login")); // Redirect URL

    verify(courseService, times(0)).addCourse(any(CourseDTO.class));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void updateCourseShouldReturnOkWhenAdminAndValidCourse() throws Exception {
    CourseDTO courseDTO = new CourseDTO();
    courseDTO.setId("1");
    courseDTO.setName("Updated Course");
    courseDTO.setDescription("Updated Description");

    doNothing().when(courseService).updateCourse(any(CourseDTO.class));

    mockMvc.perform(post("/courses/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\"id\":\"1\",\"name\":\"Updated Course\",\"description\":\"Updated Description\"}")
            .with(csrf()))
        .andExpect(status().isOk());

    verify(courseService, times(1)).updateCourse(any(CourseDTO.class));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void updateCourseShouldReturnInternalServerErrorWhenServiceThrowsException() throws Exception {
    CourseDTO courseDTO = new CourseDTO();
    courseDTO.setId("1");
    courseDTO.setName("Updated Course");
    courseDTO.setDescription("Updated Description");

    doThrow(new RuntimeException("Error updating course")).when(courseService)
        .updateCourse(any(CourseDTO.class));

    mockMvc.perform(post("/courses/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\"id\":\"1\",\"name\":\"Updated Course\",\"description\":\"Updated Description\"}")
            .with(csrf()))
        .andExpect(status().isInternalServerError());

    verify(courseService, times(1)).updateCourse(any(CourseDTO.class));
  }

  @Test
  @WithMockUser(roles = "USER")
  void updateCourseShouldReturnForbiddenWhenUserIsNotAdmin() throws Exception {
    CourseDTO courseDTO = new CourseDTO();
    courseDTO.setId("1");
    courseDTO.setName("Updated Course");
    courseDTO.setDescription("Updated Description");

    mockMvc.perform(post("/courses/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\"id\":\"1\",\"name\":\"Updated Course\",\"description\":\"Updated Description\"}")
            .with(csrf()))
        .andExpect(status().isForbidden());

    verify(courseService, times(0)).updateCourse(any(CourseDTO.class));
  }

  @Test
  @WithAnonymousUser
  void updateCourseShouldReturnUnauthorizedWhenUserIsAnonymous() throws Exception {
    CourseDTO courseDTO = new CourseDTO();
    courseDTO.setId("1");
    courseDTO.setName("Updated Course");
    courseDTO.setDescription("Updated Description");

    mockMvc.perform(post("/courses/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\"id\":\"1\",\"name\":\"Updated Course\",\"description\":\"Updated Description\"}")
            .with(csrf()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("http://localhost/user/login"));

    verify(courseService, times(0)).updateCourse(any(CourseDTO.class));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void activateCourseShouldReturnOkWhenAdmin() throws Exception {
    doNothing().when(courseService).activateCourse(anyString());

    mockMvc.perform(post("/courses/activate/1")
            .contentType(MediaType.APPLICATION_JSON)
            .with(csrf()))
        .andExpect(status().isOk());

    verify(courseService, times(1)).activateCourse("1");
  }

  @Test
  @WithMockUser(roles = "USER")
  void activateCourseShouldReturnForbiddenWhenUserIsNotAdmin() throws Exception {
    mockMvc.perform(post("/courses/activate/1")
            .contentType(MediaType.APPLICATION_JSON)
            .with(csrf()))
        .andExpect(status().isForbidden());

    verify(courseService, times(0)).activateCourse(anyString());
  }

  @Test
  @WithAnonymousUser
  void activateCourseShouldReturnUnauthorizedWhenUserIsAnonymous() throws Exception {
    mockMvc.perform(post("/courses/activate/1")
            .contentType(MediaType.APPLICATION_JSON)
            .with(csrf()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("http://localhost/user/login"));

    verify(courseService, times(0)).activateCourse(anyString());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void deactivateCourseShouldReturnOkWhenAdmin() throws Exception {
    doNothing().when(courseService).deactivateCourse(anyString());

    mockMvc.perform(post("/courses/deactivate/1")
            .contentType(MediaType.APPLICATION_JSON)
            .with(csrf()))
        .andExpect(status().isOk());

    verify(courseService, times(1)).deactivateCourse("1");
  }

  @Test
  @WithMockUser(roles = "USER")
  void deactivateCourseShouldReturnForbiddenWhenUserIsNotAdmin() throws Exception {
    mockMvc.perform(post("/courses/deactivate/1")
            .contentType(MediaType.APPLICATION_JSON)
            .with(csrf()))
        .andExpect(status().isForbidden());

    verify(courseService, times(0)).deactivateCourse(anyString());
  }

  @Test
  @WithAnonymousUser
  void deactivateCourseShouldReturnUnauthorizedWhenUserIsAnonymous() throws Exception {
    mockMvc.perform(post("/courses/deactivate/1")
            .contentType(MediaType.APPLICATION_JSON)
            .with(csrf()))
        .andExpect(status().isFound()) // 302 Redirect
        .andExpect(redirectedUrl("http://localhost/user/login"));

    verify(courseService, times(0)).deactivateCourse(anyString());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void deleteCourseShouldReturnOkWhenAdminAndDeleteSuccessful() throws Exception {
    when(courseService.deleteCourse(anyString())).thenReturn(true);

    mockMvc.perform(delete("/courses/delete/1")
            .contentType(MediaType.APPLICATION_JSON)
            .with(csrf()))
        .andExpect(status().isOk());

    verify(courseService, times(1)).deleteCourse("1");
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void deleteCourseShouldReturnConflictWhenAdminAndDeleteUnsuccessful() throws Exception {
    when(courseService.deleteCourse(anyString())).thenReturn(false);

    mockMvc.perform(delete("/courses/delete/1")
            .contentType(MediaType.APPLICATION_JSON)
            .with(csrf()))
        .andExpect(status().isConflict());

    verify(courseService, times(1)).deleteCourse("1");
  }

  @Test
  @WithMockUser(roles = "USER")
  void deleteCourseShouldReturnForbiddenWhenUserIsNotAdmin() throws Exception {
    mockMvc.perform(delete("/courses/delete/1")
            .contentType(MediaType.APPLICATION_JSON)
            .with(csrf()))
        .andExpect(status().isForbidden());

    verify(courseService, times(0)).deleteCourse(anyString());
  }

  @Test
  @WithAnonymousUser
  void deleteCourseShouldReturnUnauthorizedWhenUserIsAnonymous() throws Exception {
    mockMvc.perform(delete("/courses/delete/1")
            .contentType(MediaType.APPLICATION_JSON)
            .with(csrf()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("http://localhost/user/login"));

    verify(courseService, times(0)).deleteCourse(anyString());
  }

}
