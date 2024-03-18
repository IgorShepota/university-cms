package ua.foxminded.universitycms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.universitycms.service.user.roles.student.StudentServiceImpl;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

  private final StudentServiceImpl studentServiceImpl;

  @GetMapping
  public String listStudents(Model model) {

    model.addAttribute("students", studentServiceImpl.getAllStudents());
    return "students";
  }

}
