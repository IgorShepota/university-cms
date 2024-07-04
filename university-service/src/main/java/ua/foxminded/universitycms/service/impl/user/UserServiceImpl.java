package ua.foxminded.universitycms.service.impl.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.dto.user.UserResponseDTO;
import ua.foxminded.universitycms.dto.user.UserRegistrationDTO;
import ua.foxminded.universitycms.dto.user.role.StudentResponseDTO;
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
  private final PasswordEncoder passwordEncoder;

  @Override
  public void registerUser(UserRegistrationDTO userRegistrationDTO) {
    User user = userMapper.userRegistrationDTOToUser(userRegistrationDTO);

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    Optional<Role> unverifiedRoleOptional = roleRepository.findByName(RoleName.UNVERIFIED);
    Role unverifiedRole = unverifiedRoleOptional.orElseThrow(
        () -> new RoleNotFoundException("Unverified role not found"));

    user.setRole(unverifiedRole);
    userRepository.save(user);
  }

  public Optional<UserResponseDTO> getUserById(String id) {
    log.info("Fetching user with id {}.", id);
    return userRepository.findById(id).map(userMapper::userToUserResponseDTO);
  }

  @Override
  public List<UserResponseDTO> getAllUsers() {
    log.info("Fetching all users.");
    return userRepository.findAll().stream()
        .map(userMapper::userToUserResponseDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<UserResponseDTO> getAllUsersSorted(String sort, String order) {
    Sort.Direction dir = "desc".equalsIgnoreCase(order) ? Direction.DESC : Direction.ASC;
    Sort sorted = Sort.by(dir, sort);
    return userRepository.findAll(sorted).stream()
        .map(userMapper::userToUserResponseDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<StudentResponseDTO> getAllStudentsSorted(String sort, String order) {
    log.info("Fetching all students sorted by {} {}", sort, order);

    boolean isAscending = "asc".equalsIgnoreCase(order);

    List<StudentData> studentDataList = studentDataRepository.findAllWithSort(sort, isAscending);

    return studentDataList.stream()
        .map(studentMapper::mapToStudentResponseDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<UserResponseDTO> getAllTeachers() {
    log.info("Fetching all teachers.");
    return userRepository.findAllByRoleName(RoleName.TEACHER).stream()
        .map(userMapper::userToUserResponseDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<UserResponseDTO> getAllUsers(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of users with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);
    return userRepository.findAll(pageable).getContent().stream()
        .map(userMapper::userToUserResponseDTO)
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
