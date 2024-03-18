package ua.foxminded.universitycms.service.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.model.mapping.user.UserMapper;
import ua.foxminded.universitycms.model.dto.user.UserDTO;
import ua.foxminded.universitycms.model.entity.user.User;
import ua.foxminded.universitycms.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  @Transactional
  public void addUser(UserDTO userDTO) {
    User user = userMapper.userDTOToUser(userDTO);
    user = userRepository.save(user);
    log.info("User with id {} was successfully saved.", user.getId());
  }

  @Override
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
  public List<UserDTO> getAllUsers(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of users with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);
    return userRepository.findAll(pageable).getContent().stream()
        .map(userMapper::userToUserDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void updateUser(UserDTO userDTO) {
    log.info("Updating user: {}", userDTO);
    User user = userMapper.userDTOToUser(userDTO);
    userRepository.save(user);
  }

  @Override
  @Transactional
  public boolean deleteUser(String id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteById(id);
      log.info("User with id {} was successfully deleted.", id);
      return true;
    } else {
      log.info("User with id {} not found.", id);
      return false;
    }
  }

}
