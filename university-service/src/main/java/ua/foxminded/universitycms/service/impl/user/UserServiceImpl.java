package ua.foxminded.universitycms.service.impl.user;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.dto.user.UserDTO;
import ua.foxminded.universitycms.dto.user.UserRegistrationDTO;
import ua.foxminded.universitycms.dto.user.role.StudentDTO;
import ua.foxminded.universitycms.mapping.user.UserMapper;
import ua.foxminded.universitycms.mapping.user.role.StudentMapper;
import ua.foxminded.universitycms.model.entity.user.User;
import ua.foxminded.universitycms.model.entity.user.role.Role;
import ua.foxminded.universitycms.model.entity.user.role.RoleName;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.AdminData;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.StudentData;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.TeacherData;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.UniversityUserData;
import ua.foxminded.universitycms.repository.user.UserRepository;
import ua.foxminded.universitycms.repository.user.role.RoleRepository;
import ua.foxminded.universitycms.repository.user.universityuserdata.StudentDataRepository;
import ua.foxminded.universitycms.repository.user.universityuserdata.UniversityUserDataRepository;
import ua.foxminded.universitycms.service.exception.InvalidRoleNameException;
import ua.foxminded.universitycms.service.exception.RoleNotFoundException;
import ua.foxminded.universitycms.service.exception.UnsupportedRoleException;
import ua.foxminded.universitycms.service.exception.UserNotFoundException;
import ua.foxminded.universitycms.service.user.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;
  private final StudentMapper studentMapper;
  private final UserRepository userRepository;
  private final UniversityUserDataRepository universityUserDataRepository;
  private final StudentDataRepository studentDataRepository;
  private final RoleRepository roleRepository;

  @Override
  public void registerUser(UserRegistrationDTO userRegistrationDTO) {
    User user = userMapper.userRegistrationDTOToUser(userRegistrationDTO);

    Optional<Role> unverifiedRoleOptional = roleRepository.findByName(RoleName.UNVERIFIED);
    Role unverifiedRole = unverifiedRoleOptional.orElseThrow(
        () -> new RoleNotFoundException("Unverified role not found"));

    user.setRole(unverifiedRole);
    userRepository.save(user);
  }

  public Optional<UserDTO> getUserById(String id) {
    log.info("Fetching user with id {}.", id);
    return userRepository.findById(id).map(userMapper::userToUserDTO);
  }

  @Override
  public List<UserDTO> getAllUsers() {
    log.info("Fetching all users.");
    return userRepository.findAll().stream()
        .map(userMapper::userToUserDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<UserDTO> getAllUsersSorted(String sort, String order) {
    Sort.Direction dir = "desc".equalsIgnoreCase(order) ? Direction.DESC : Direction.ASC;
    Sort sorted = Sort.by(dir, sort);
    return userRepository.findAll(sorted).stream()
        .map(userMapper::userToUserDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<StudentDTO> getAllStudents() {
    log.info("Fetching all students.");
    List<User> studentUsers = userRepository.findAllByRoleName(RoleName.STUDENT);
    List<StudentData> studentDataList = studentDataRepository.findAll();

    return studentUsers.stream()
        .map(studentUser -> {
          String userId = studentUser.getId();
          Optional<StudentData> studentDataOptional = studentDataList.stream()
              .filter(studentData -> studentData.getId().equals(userId))
              .findFirst();

          return studentDataOptional.map(
                  studentData -> studentMapper.mapToStudentDTO(studentUser, studentData))
              .orElse(null);
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  @Override
  public List<UserDTO> getAllTeachers() {
    log.info("Fetching all teachers.");
    return userRepository.findAllByRoleName(RoleName.TEACHER).stream()
        .map(userMapper::userToUserDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<UserDTO> getAllUsers(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of users with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);
    return userRepository.findAll(pageable).getContent().stream()
        .map(userMapper::userToUserDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void updateUserRole(String userId, String newRoleName) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("Invalid user ID"));
    Role role = roleRepository.findByName(RoleName.valueOf(newRoleName))
        .orElseThrow(() -> new InvalidRoleNameException("Invalid role name: " + newRoleName));

    if (user.getRole().getName().name().equals("UNVERIFIED")
        && user.getUniversityUserData() == null) {
      UniversityUserData newUserData = createUniversityUserDataForRole(newRoleName, user);
      universityUserDataRepository.save(newUserData);
      user.setUniversityUserData(newUserData);
      userRepository.saveAndFlush(user);
    }

    user.setRole(role);
    userRepository.save(user);
    log.info("Role for user {} updated to {}", userId, newRoleName);
  }

  private UniversityUserData createUniversityUserDataForRole(String roleName, User user) {
    switch (roleName) {
      case "ADMIN":
        return AdminData.builder().withUser(user).build();
      case "STUDENT":
        return StudentData.builder().withUser(user).build();
      case "TEACHER":
        return TeacherData.builder().withUser(user).build();
      default:
        throw new UnsupportedRoleException("Unsupported role for user data creation:" + roleName);
    }
  }

}
