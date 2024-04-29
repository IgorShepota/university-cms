package ua.foxminded.universitycms.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
