package ua.foxminded.universitycms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.foxminded.universitycms.service.user.roles.teacher.TeacherServiceImpl;

@Controller
@RequestMapping("teachers")
@RequiredArgsConstructor
public class TeacherController {

  private final TeacherServiceImpl teacherServiceImpl;

  @GetMapping
  public String listTeachers(Model model) {

    model.addAttribute("teachers", teacherServiceImpl.getAllTeachers());
    return "teachers";
  }

}
