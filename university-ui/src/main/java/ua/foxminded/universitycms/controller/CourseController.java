package ua.foxminded.universitycms.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.foxminded.universitycms.dto.CourseDTO;
import ua.foxminded.universitycms.service.CourseService;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

  private final CourseService courseService;

  @GetMapping
  public String listCourses(Model model) {
    model.addAttribute("courses", courseService.getAllCourses());
    model.addAttribute("course", new CourseDTO());  // Ensure this is always added
    return "courses";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/add")
  public String showAddCourseForm(@ModelAttribute("course") CourseDTO course) {

    return "courses";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/add")
  public String addCourse(@Valid @ModelAttribute("course") CourseDTO course, BindingResult result) {
    if (result.hasErrors()) {
      return "courses";
    }

    courseService.addCourse(course);

    return "redirect:/courses";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/update")
  @ResponseBody
  public ResponseEntity<?> updateCourse(@Valid @RequestBody CourseDTO course,
      BindingResult result) {
    if (result.hasErrors()) {
      return  ResponseEntity.badRequest().body(result.getAllErrors());
    }

    courseService.updateCourse(course);

    return ResponseEntity.ok().build();
  }

}
