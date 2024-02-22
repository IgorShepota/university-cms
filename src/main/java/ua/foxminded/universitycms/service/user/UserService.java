package ua.foxminded.universitycms.service.user;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.entity.user.User;
import ua.foxminded.universitycms.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public void addUser(User user) {
    userRepository.save(user);
    log.info("User with id {} was successfully saved.", user.getId());
  }

  public Optional<User> getUserById(String id) {
    log.info("Fetching user with id {}.", id);
    return userRepository.findById(id);
  }

  public List<User> getAllUsers() {
    log.info("Fetching all users.");
    return userRepository.findAll();
  }

  public List<User> getAllUsers(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of users with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);

    return userRepository.findAll(pageable).getContent();
  }

  @Transactional
  public void updateUser(User user) {
    log.info("Updating user: {}", user);
    userRepository.save(user);
  }

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
