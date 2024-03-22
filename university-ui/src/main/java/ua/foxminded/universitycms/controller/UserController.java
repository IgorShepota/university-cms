package ua.foxminded.universitycms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

  @GetMapping("/registration")
  public String showRegistrationForm() {
    return "registration";
  }

  @GetMapping("/login")
  public String showLoginForm() {
    return "login";
  }
}
