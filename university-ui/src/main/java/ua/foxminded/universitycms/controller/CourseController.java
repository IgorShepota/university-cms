package ua.foxminded.universitycms.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.foxminded.universitycms.dto.CourseDTO;
import ua.foxminded.universitycms.service.CourseService;
import ua.foxminded.universitycms.service.exception.CourseAlreadyExistsException;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

  private final CourseService courseService;

  @GetMapping
  public String listCourses(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    if (userDetails == null) {
      List<CourseDTO> courses = courseService.getAllActiveCourses();
      model.addAttribute("courses", courses);
    } else {
      boolean isAdmin = userDetails.getAuthorities().stream()
          .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

      List<CourseDTO> courses =
          isAdmin ? courseService.getAllCourses() : courseService.getAllActiveCourses();

      model.addAttribute("courses", courses);
    }
    model.addAttribute("course", new CourseDTO());
    return "courses";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/add")
  public String showAddCourseForm(@ModelAttribute("course") CourseDTO course) {
    return "courses";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/add")
  public String addCourse(@Valid @ModelAttribute("course") CourseDTO course, BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      model.addAttribute("courses", courseService.getAllCourses());
      return "courses";
    }

    try {
      courseService.addCourse(course);
    } catch (CourseAlreadyExistsException e) {
      if (e.getMessage().contains("name")) {
        result.rejectValue("name", "duplicate", e.getMessage());
      }
      if (e.getMessage().contains("description")) {
        result.rejectValue("description", "duplicate", e.getMessage());
      }
      model.addAttribute("courses", courseService.getAllCourses());
      return "courses";
    }

    return "redirect:/courses";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/update")
  @ResponseBody
  public ResponseEntity<String> updateCourse(@RequestBody CourseDTO courseDTO) {
    try {
      courseService.updateCourse(courseDTO);
      return ResponseEntity.ok().build();
    } catch (CourseAlreadyExistsException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("An unexpected error occurred");
    }
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/activate/{id}")
  @ResponseBody
  public ResponseEntity<Void> activateCourse(@PathVariable String id) {
    courseService.activateCourse(id);
    return ResponseEntity.ok().build();
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/deactivate/{id}")
  public ResponseEntity<Void> deactivateCourse(@PathVariable String id) {
    courseService.deactivateCourse(id);
    return ResponseEntity.ok().build();
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/delete/{id}")
  @ResponseBody
  public ResponseEntity<Void> deleteCourse(@PathVariable String id) {
    boolean success = courseService.deleteCourse(id);
    if (success) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }

}
