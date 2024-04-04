package ua.foxminded.universitycms.service.user;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.foxminded.universitycms.dto.user.UserDTO;
import ua.foxminded.universitycms.mapping.user.UserMapper;
import ua.foxminded.universitycms.model.entity.user.User;
import ua.foxminded.universitycms.repository.user.UserRepository;
import ua.foxminded.universitycms.service.impl.user.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userMapper;

  @InjectMocks
  private UserServiceImpl userService;

  @Test
  void addUserShouldWorkCorrectlyIfUserEntityCorrect() {
    UserDTO userDTO = new UserDTO();
    userDTO.setId("test-id");
    User user = new User();
    user.setId(userDTO.getId());

    when(userMapper.userDTOToUser(any(UserDTO.class))).thenReturn(user);
    when(userRepository.save(any(User.class))).thenReturn(user);

    userService.addUser(userDTO);

    verify(userMapper).userDTOToUser(userDTO);
    verify(userRepository).save(user);
  }

  @Test
  void getUserByIdShouldReturnUserDTOWhenUserExists() {
    String id = "test-id";
    User user = new User();
    UserDTO expectedDTO = new UserDTO();
    expectedDTO.setId(id);

    when(userRepository.findById(id)).thenReturn(Optional.of(user));
    when(userMapper.userToUserDTO(user)).thenReturn(expectedDTO);

    Optional<UserDTO> result = userService.getUserById(id);

    assertThat(result).isPresent()
        .contains(expectedDTO);
  }

  @Test
  void getUserByIdShouldReturnEmptyOptionalWhenUserDoesNotExist() {
    String id = "non-existing-id";

    when(userRepository.findById(id)).thenReturn(Optional.empty());

    Optional<UserDTO> result = userService.getUserById(id);

    assertThat(result).isNotPresent();
  }

  @Test
  void getAllUsersShouldReturnListOfUserDTOsWhenUsersExist() {
    User user1 = new User();
    User user2 = new User();
    List<User> users = asList(user1, user2);
    UserDTO dto1 = new UserDTO();
    UserDTO dto2 = new UserDTO();

    when(userRepository.findAll()).thenReturn(users);
    when(userMapper.userToUserDTO(user1)).thenReturn(dto1);
    when(userMapper.userToUserDTO(user2)).thenReturn(dto2);

    List<UserDTO> result = userService.getAllUsers();

    assertThat(result).hasSize(2)
        .containsExactly(dto1, dto2);
  }

  @Test
  void getAllUsersShouldReturnEmptyListWhenNoUsersExist() {
    when(userRepository.findAll()).thenReturn(emptyList());

    List<UserDTO> result = userService.getAllUsers();

    assertThat(result).isEmpty();
  }

  @Test
  void getAllUsersWithPaginationShouldReturnListOfUserDTOsWhenUsersExist() {
    int page = 1;
    int itemsPerPage = 2;
    User user1 = new User();
    User user2 = new User();
    List<User> users = asList(user1, user2);
    Page<User> pagedUsers = new PageImpl<>(users, PageRequest.of(0, itemsPerPage),
        users.size());
    UserDTO dto1 = new UserDTO();
    UserDTO dto2 = new UserDTO();

    when(userRepository.findAll(any(Pageable.class))).thenReturn(pagedUsers);
    when(userMapper.userToUserDTO(user1)).thenReturn(dto1);
    when(userMapper.userToUserDTO(user2)).thenReturn(dto2);

    List<UserDTO> result = userService.getAllUsers(page, itemsPerPage);

    assertThat(result).hasSize(2)
        .containsExactly(dto1, dto2);
  }

  @Test
  void getAllUsersWithPaginationShouldReturnEmptyListWhenNoUsersExist() {
    int page = 1;
    int itemsPerPage = 2;
    Page<User> pagedUsers = new PageImpl<>(emptyList(), PageRequest.of(0, itemsPerPage), 0);

    when(userRepository.findAll(any(Pageable.class))).thenReturn(pagedUsers);

    List<UserDTO> result = userService.getAllUsers(page, itemsPerPage);

    assertThat(result).isEmpty();
  }

  @Test
  void updateUserShouldCorrectlyMapAndSaveUser() {
    UserDTO userDTO = new UserDTO();
    User user = new User();

    when(userMapper.userDTOToUser(any(UserDTO.class))).thenReturn(user);

    userService.updateUser(userDTO);

    verify(userMapper).userDTOToUser(userDTO);
    verify(userRepository).save(user);
  }

  @Test
  void deleteUserShouldWorkCorrectlyIfUserExists() {
    String userId = "existing-id";

    when(userRepository.existsById(userId)).thenReturn(true);

    boolean result = userService.deleteUser(userId);

    assertThat(result).isTrue();
    verify(userRepository).deleteById(userId);
  }

  @Test
  void deleteUserShouldReturnFalseIfUserNotExists() {
    String userId = "non-existing-id";

    when(userRepository.existsById(userId)).thenReturn(false);

    boolean result = userService.deleteUser(userId);

    assertThat(result).isFalse();
  }

}
