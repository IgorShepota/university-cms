package ua.foxminded.universitycms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.universitycms.service.user.roles.TeacherService;

@Controller
@RequestMapping("teachers")
@RequiredArgsConstructor
public class TeacherController {

  private final TeacherService teacherService;

  @GetMapping
  public String listTeachers(Model model) {

    model.addAttribute("teachers", teacherService.getAllTeachers());
    return "teachers";
  }

}