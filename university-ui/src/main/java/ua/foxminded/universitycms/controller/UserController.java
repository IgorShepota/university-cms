package ua.foxminded.universitycms.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.foxminded.universitycms.dto.user.ChangeRoleRequest;
import ua.foxminded.universitycms.dto.user.UserDTO;
import ua.foxminded.universitycms.dto.user.UserRegistrationDTO;
import ua.foxminded.universitycms.service.user.UserService;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/login")
  public String showLoginForm() {
    return "user/login";
  }

  @GetMapping("/registration")
  public String showRegistrationForm(@ModelAttribute("user") UserRegistrationDTO user) {
    return "user/registration";
  }

  @PostMapping("/registration")
  public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDTO user,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "user/registration";
    }

    userService.registerUser(user);

    return "redirect:/user/login";
  }

  @GetMapping("/list")
  public String listUsers(Model model,
      @RequestParam(required = false, defaultValue = "creationDateTime") String sort,
      @RequestParam(required = false, defaultValue = "asc") String order) {
    List<UserDTO> users = userService.getAllUsersSorted(sort, order);

    model.addAttribute("users", users);
    model.addAttribute("sortField", sort);
    model.addAttribute("sortOrder", order);
    return "user/list";
  }

  @PostMapping("/change-role")
  @ResponseBody
  public ResponseEntity<?> changeUserRole(@RequestBody ChangeRoleRequest changeRoleRequest) {
    try {
      userService.updateUserRole(changeRoleRequest.getUserId(), changeRoleRequest.getRoleName());
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating role");
    }
  }

}
