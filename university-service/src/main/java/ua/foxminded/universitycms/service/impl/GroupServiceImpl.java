package ua.foxminded.universitycms.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.dto.CourseAssignmentDTO;
import ua.foxminded.universitycms.dto.GroupDTO;
import ua.foxminded.universitycms.dto.user.role.StudentResponseDTO;
import ua.foxminded.universitycms.mapping.CourseAssignmentMapper;
import ua.foxminded.universitycms.mapping.GroupMapper;
import ua.foxminded.universitycms.mapping.user.role.StudentMapper;
import ua.foxminded.universitycms.model.entity.CourseAssignment;
import ua.foxminded.universitycms.model.entity.Group;
import ua.foxminded.universitycms.model.entity.GroupStatus;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.StudentData;
import ua.foxminded.universitycms.repository.CourseAssignmentRepository;
import ua.foxminded.universitycms.repository.GroupRepository;
import ua.foxminded.universitycms.repository.user.universityuserdata.StudentDataRepository;
import ua.foxminded.universitycms.service.GroupService;
import ua.foxminded.universitycms.service.exception.GroupAlreadyExistsException;
import ua.foxminded.universitycms.service.exception.GroupNotFoundException;
import ua.foxminded.universitycms.service.exception.InvalidCourseAssignmentException;
import ua.foxminded.universitycms.service.exception.InvalidGroupStatusException;
import ua.foxminded.universitycms.service.exception.InvalidStudentException;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService {

  private final GroupRepository groupRepository;
  private final GroupMapper groupMapper;
  private final CourseAssignmentRepository courseAssignmentRepository;
  private final CourseAssignmentMapper courseAssignmentMapper;
  private final StudentDataRepository studentDataRepository;
  private final StudentMapper studentMapper;

  @Override
  @Transactional
  public void addGroup(GroupDTO groupDTO) {
    if (groupRepository.existsByNameIgnoreCase(groupDTO.getName())) {
      throw new GroupAlreadyExistsException(
          "Group with name " + groupDTO.getName() + " already exists");
    }
    Group group = groupMapper.groupDTOToGroup(groupDTO);
    group = groupRepository.save(group);
    log.info("Group with id {} was successfully saved.", group.getId());
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<GroupDTO> getGroupById(String id) {
    log.info("Fetching group with id {}.", id);
    return groupRepository.findById(id).map(group -> {
      log.info("Group {} has {} students.", group.getName(), group.getStudents().size());
      group.getStudents().forEach(student -> log.info("Student ID: {}, Name: {} {}",
          student.getUser().getId(), student.getUser().getFirstName(),
          student.getUser().getLastName()));
      return groupMapper.groupToGroupDTO(group);
    });
  }

  @Override
  @Transactional(readOnly = true)
  public List<GroupDTO> getAllGroups() {
    log.info("Fetching all groups.");
    return groupRepository.findAll().stream()
        .map(groupMapper::groupToGroupDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<GroupDTO> getAllGroups(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of groups with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);
    return groupRepository.findAll(pageable).getContent().stream()
        .map(groupMapper::groupToGroupDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<GroupDTO> getAllActiveGroups() {
    log.info("Fetching all active groups.");
    return groupRepository.findAllByStatusWithStudents(GroupStatus.ACTIVE).stream()
        .distinct()
        .map(groupMapper::groupToGroupDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<GroupDTO> getGroupsBasedOnUserDetails(UserDetails userDetails) {
    if (userDetails == null) {
      return getAllActiveGroups();
    } else {
      boolean isAdmin = userDetails.getAuthorities().stream()
          .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
      return isAdmin ? getAllGroups() : getAllActiveGroups();
    }
  }

  @Override
  @Transactional
  public void updateGroup(GroupDTO groupDTO) {
    log.info("Updating group: {}", groupDTO);
    Group group = groupMapper.groupDTOToGroup(groupDTO);
    groupRepository.save(group);
  }

  @Override
  @Transactional
  public void deleteGroup(String groupId) {
    log.info("Attempting to delete group with id={}", groupId);
    Group group = groupRepository.findById(groupId)
        .orElseThrow(() -> new GroupNotFoundException("Group not found with ID: " + groupId));

    if (group.getStatus() != GroupStatus.DELETED) {
      log.error("Group status must be DELETED to delete the group: id={}, status={}", groupId,
          group.getStatus());
      throw new InvalidGroupStatusException("Group status must be DELETED to delete the group");
    }

    groupRepository.delete(group);
    log.info("Group deleted: id={}", groupId);
  }

  @Override
  @Transactional(readOnly = true)
  public GroupDTO getGroupEditDetails(String groupId) {
    GroupDTO group = getGroupById(groupId).orElseThrow(
        () -> new GroupNotFoundException("Group not found with ID: " + groupId));
    List<CourseAssignmentDTO> availableAssignments = getCourseAssignmentWithNoGroup();
    List<StudentResponseDTO> availableStudents = getStudentWithNoGroup();
    group.setAvailableCourseAssignments(availableAssignments);
    group.setAvailableStudents(availableStudents);
    return group;
  }

  @Override
  @Transactional(readOnly = true)
  public List<CourseAssignmentDTO> getCourseAssignmentWithNoGroup() {
    log.info("Fetching course assignments with no group");
    return courseAssignmentRepository.findCourseAssignmentsWithNoGroup().stream()
        .map(courseAssignmentMapper::courseAssignmentToCourseAssignmentDTO)
        .collect(Collectors.toList());
  }

  @Transactional
  @Override
  public void addCourseAssignmentToGroup(String groupId, String assignmentId) {
    Group group = groupRepository.findById(groupId)
        .orElseThrow(() -> new GroupNotFoundException("Group not found with ID: " + groupId));
    CourseAssignment assignment = courseAssignmentRepository.findById(assignmentId)
        .orElseThrow(() -> new InvalidCourseAssignmentException(
            "Invalid course assignment ID: " + assignmentId));

    if (group.getCourseAssignments().contains(assignment)) {
      log.info("Course assignment with id {} is already in group {}", assignmentId, groupId);
      return;
    }

    assignment.setGroup(group);
    courseAssignmentRepository.save(assignment);

    group.getCourseAssignments().add(assignment);
    groupRepository.save(group);

    log.info("Course assignment with id {} added to group {}", assignmentId, groupId);
  }

  @Override
  @Transactional
  public void removeCourseAssignmentFromGroup(String assignmentId) {
    CourseAssignment assignment = courseAssignmentRepository.findById(assignmentId)
        .orElseThrow(() -> new InvalidCourseAssignmentException(
            "Invalid course assignment ID: " + assignmentId));
    assignment.setGroup(null);
    courseAssignmentRepository.save(assignment);
    log.info("Course assignment with id {} removed from group", assignmentId);
  }

  @Override
  @Transactional(readOnly = true)
  public List<StudentResponseDTO> getStudentWithNoGroup() {
    log.info("Fetching students with no group");
    return studentDataRepository.findStudentsWithNoGroup().stream()
        .map(studentMapper::mapToStudentResponseDTO)
        .collect(Collectors.toList());
  }

  @Transactional
  @Override
  public void addStudentToGroup(String groupId, String studentId) {
    Group group = groupRepository.findById(groupId)
        .orElseThrow(() -> new GroupNotFoundException("Group not found with ID: " + groupId));
    StudentData studentData = studentDataRepository.findById(studentId)
        .orElseThrow(() -> new InvalidStudentException("Invalid student ID: " + studentId));

    if (studentData.getOwnerGroup() != null && studentData.getOwnerGroup().equals(group)) {
      log.info("Student with id {} is already in group {}", studentId, groupId);
      return;
    }

    studentData.setOwnerGroup(group);
    studentDataRepository.save(studentData);

    group.getStudents().add(studentData);
    groupRepository.save(group);

    log.info("Student with id {} added to group {}", studentId, groupId);
  }

  @Override
  @Transactional
  public void removeStudentFromGroup(String studentId) {
    StudentData studentData = studentDataRepository.findById(studentId)
        .orElseThrow(() -> new InvalidStudentException("Invalid student ID: " + studentId));
    if (studentData.getOwnerGroup() != null) {
      studentData.setOwnerGroup(null);
      studentDataRepository.save(studentData);
      log.info("Student with id {} removed from group", studentId);
    } else {
      log.info("Student with id {} is not assigned to any group", studentId);
    }
  }

  @Override
  @Transactional
  public GroupStatus changeGroupStatus(String groupId, GroupStatus newStatus) {
    Group group = groupRepository.findById(groupId)
        .orElseThrow(() -> new GroupNotFoundException("Group not found with ID: " + groupId));

    GroupStatus oldStatus = group.getStatus();

    if (oldStatus != newStatus) {
      group.setStatus(newStatus);
      groupRepository.save(group);
      log.info("Changed status of group with ID {} from {} to {}", groupId, oldStatus, newStatus);
    } else {
      log.info("Status of group with ID {} remains unchanged: {}", groupId, oldStatus);
    }

    return group.getStatus();
  }

}
