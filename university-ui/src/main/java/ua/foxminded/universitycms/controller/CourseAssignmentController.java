package ua.foxminded.universitycms.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.foxminded.universitycms.dto.CourseAssignmentDTO;
import ua.foxminded.universitycms.service.CourseAssignmentService;
import ua.foxminded.universitycms.service.CourseService;
import ua.foxminded.universitycms.service.GroupService;
import ua.foxminded.universitycms.service.exception.CourseAssignmentAlreadyExistsException;
import ua.foxminded.universitycms.service.user.UserService;

@Controller
@RequestMapping("/assignments")
@RequiredArgsConstructor
@Slf4j
public class CourseAssignmentController {

  private final CourseAssignmentService courseAssignmentService;
  private final CourseService courseService;
  private final UserService userService;
  private final GroupService groupService;

  @GetMapping
  public String listAssignments(Model model) {
    model.addAttribute("assignments", courseAssignmentService.getAllCourseAssignments());
    model.addAttribute("activeCourses", courseService.getAllActiveCourses());
    model.addAttribute("teachers", userService.getAllTeachers());
    model.addAttribute("activeGroups", groupService.getAllActiveGroups());
    return "course-assignments";
  }

  @PostMapping("/add")
  @ResponseBody
  public ResponseEntity<String> addAssignment(@RequestBody CourseAssignmentDTO assignmentDTO) {
    try {
      courseAssignmentService.addCourseAssignment(assignmentDTO);
      return ResponseEntity.ok("Assignment added successfully");
    } catch (CourseAssignmentAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/update")
  @ResponseBody
  public ResponseEntity<String> updateAssignment(@RequestBody CourseAssignmentDTO assignmentDTO) {
    log.info("Received update request for assignment: {}", assignmentDTO);  // Добавьте это
    try {
      courseAssignmentService.updateCourseAssignment(assignmentDTO);
      return ResponseEntity.ok("Assignment updated successfully");
    } catch (CourseAssignmentAlreadyExistsException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (Exception e) {
      log.error("Error updating assignment", e);  // Добавьте это
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  @ResponseBody
  public ResponseEntity<String> deleteAssignment(@PathVariable String id) {
    try {
      boolean deleted = courseAssignmentService.deleteCourseAssignment(id);
      if (deleted) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment not found");
      }
    } catch (Exception e) {
      log.error("Error deleting assignment", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting assignment: " + e.getMessage());
    }
  }

}
