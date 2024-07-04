package ua.foxminded.universitycms.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.universitycms.dto.user.role.StudentResponseDTO;
import ua.foxminded.universitycms.service.user.UserService;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

  private final UserService userService;

  @GetMapping
  public String listStudents(@RequestParam(defaultValue = "id") String sort,
      @RequestParam(defaultValue = "asc") String order,
      Model model) {

    List<StudentResponseDTO> students = userService.getAllStudentsSorted(sort, order);
    model.addAttribute("students", students);
    model.addAttribute("sortField", sort);
    model.addAttribute("sortOrder", order);
    return "students";
  }

}
