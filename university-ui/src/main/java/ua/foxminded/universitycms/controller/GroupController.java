package ua.foxminded.universitycms.controller;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.foxminded.universitycms.dto.GroupDTO;
import ua.foxminded.universitycms.dto.user.role.StudentResponseDTO;
import ua.foxminded.universitycms.model.entity.GroupStatus;
import ua.foxminded.universitycms.service.GroupService;
import ua.foxminded.universitycms.service.exception.GroupAlreadyExistsException;

@Controller
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

  private final GroupService groupService;

  @GetMapping
  public String listGroups(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    List<GroupDTO> groups = groupService.getGroupsBasedOnUserDetails(userDetails);
    model.addAttribute("groups", groups);
    model.addAttribute("group", new GroupDTO());
    return "groups";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/edit/{id}")
  public String editGroup(@PathVariable String id, Model model,
      RedirectAttributes redirectAttributes) {
    try {
      GroupDTO groupEditDetails = groupService.getGroupEditDetails(id);
      model.addAttribute("group", groupEditDetails);
//      model.addAttribute("availableAssignments", groupEditDetails.getAvailableCourseAssignments());
      model.addAttribute("availableStudents", groupEditDetails.getAvailableStudents());
      return "group-edit";
    } catch (EntityNotFoundException e) {
      redirectAttributes.addFlashAttribute("errorMessage", "Group not found");
      return "redirect:/groups";
    }
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/add")
  public String showAddGroupForm(@ModelAttribute("group") GroupDTO group) {
    return "groups";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/add")
  public String addGroup(@Valid @ModelAttribute("group") GroupDTO group, BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      model.addAttribute("groups", groupService.getAllGroups());
      return "groups";
    }

    try {
      groupService.addGroup(group);
    } catch (GroupAlreadyExistsException e) {
      result.rejectValue("name", "duplicate", e.getMessage());
      model.addAttribute("groups", groupService.getAllGroups());
      return "groups";
    }

    return "redirect:/groups";
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/edit/{id}/remove-courseAssignment")
  @ResponseBody
  public ResponseEntity<Void> removeCourseAssignmentFromGroup(@PathVariable String id,
      @RequestParam String assignmentId) {
    groupService.removeCourseAssignmentFromGroup(assignmentId);
    return ResponseEntity.ok().build();
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/edit/available-students")
  @ResponseBody
  public List<StudentResponseDTO> getAvailableStudents() {
    return groupService.getStudentWithNoGroup();
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/edit/{groupId}/addStudent")
  @ResponseBody
  public ResponseEntity<Void> addStudentToGroup(@PathVariable String groupId,
      @RequestParam String studentId) {
    groupService.addStudentToGroup(groupId, studentId);
    return ResponseEntity.ok().build();
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/edit/{id}/remove-student")
  @ResponseBody
  public ResponseEntity<Void> removeStudentFromGroup(@PathVariable String id,
      @RequestParam String studentId) {
    groupService.removeStudentFromGroup(studentId);
    return ResponseEntity.ok().build();
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/edit/{id}/change-status")
  @ResponseBody
  public ResponseEntity<String> changeGroupStatus(@PathVariable String id,
      @RequestParam GroupStatus status) {
    GroupStatus newStatus = groupService.changeGroupStatus(id, status);
    return ResponseEntity.ok(newStatus.name());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/edit/{groupId}/check-empty")
  @ResponseBody
  public boolean checkIfGroupIsEmpty(@PathVariable String groupId) {
    Optional<GroupDTO> group = groupService.getGroupById(groupId);
    return group.map(g -> g.getStudents().isEmpty()).orElse(false);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/delete/{id}")
  @ResponseBody
  public ResponseEntity<Void> deleteGroup(@PathVariable String id) {
    Optional<GroupDTO> groupOptional = groupService.getGroupById(id);
    if (groupOptional.isPresent()) {
      GroupDTO group = groupOptional.get();
      if (group.getStatus() == GroupStatus.DELETED) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok().build();
      }
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

}
