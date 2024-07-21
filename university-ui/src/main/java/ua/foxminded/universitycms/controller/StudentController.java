package ua.foxminded.universitycms.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.universitycms.dto.user.StudentSearchCriteria;
import ua.foxminded.universitycms.dto.user.role.StudentResponseDTO;
import ua.foxminded.universitycms.model.entity.user.Gender;
import ua.foxminded.universitycms.service.user.UserService;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

  private final UserService userService;

  @GetMapping
  public String listStudents(@RequestParam(required = false) String id,
      @RequestParam(required = false) String firstName,
      @RequestParam(required = false) String lastName,
      @RequestParam(required = false) String email,
      @RequestParam(required = false) Gender gender,
      @RequestParam(required = false) String groupName,
      @RequestParam(defaultValue = "id") String sort,
      @RequestParam(defaultValue = "asc") String order,
      Model model) {

    StudentSearchCriteria searchCriteria = new StudentSearchCriteria(id, firstName, lastName, email,
        gender, groupName);
    List<StudentResponseDTO> students = userService.searchStudents(searchCriteria, sort, order);

    model.addAttribute("students", students);
    model.addAttribute("sortField", sort);
    model.addAttribute("sortOrder", order);
    model.addAttribute("searchCriteria", searchCriteria);

    return "students";
  }

}
