package ua.foxminded.universitycms.service.impl.user;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.foxminded.universitycms.dto.user.UserDTO;
import ua.foxminded.universitycms.dto.user.UserRegistrationDTO;
import ua.foxminded.universitycms.dto.user.role.StudentDTO;
import ua.foxminded.universitycms.mapping.user.UserMapper;
import ua.foxminded.universitycms.mapping.user.role.StudentMapper;
import ua.foxminded.universitycms.model.entity.user.User;
import ua.foxminded.universitycms.model.entity.user.role.Role;
import ua.foxminded.universitycms.model.entity.user.role.RoleName;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.StudentData;
import ua.foxminded.universitycms.repository.user.UserRepository;
import ua.foxminded.universitycms.repository.user.role.RoleRepository;
import ua.foxminded.universitycms.repository.user.universityuserdata.StudentDataRepository;
import ua.foxminded.universitycms.service.user.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;
  private final StudentMapper studentMapper;
  private final UserRepository userRepository;
  private final StudentDataRepository studentDataRepository;
  private final RoleRepository roleRepository;

  @Override
  public void registerUser(UserRegistrationDTO userRegistrationDTO) {
    User user = userMapper.userRegistrationDTOToUser(userRegistrationDTO);

    Optional<Role> unverifiedRoleOptional = roleRepository.findByName(RoleName.UNVERIFIED);
    Role unverifiedRole = unverifiedRoleOptional.orElseThrow(
        () -> new RuntimeException("Unverified role not found"));

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

}
