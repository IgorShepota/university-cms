//package ua.foxminded.universitycms.service.user;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import ua.foxminded.universitycms.model.entity.user.User;
//import ua.foxminded.universitycms.repository.user.UserRepository;
//
//@SpringBootTest
//class UserServiceImplTest {
//
//  @MockBean
//  private UserRepository userRepository;
//
//  @Autowired
//  private UserServiceImpl userServiceImpl;
//
//  @Test
//  void addUserShouldWorkCorrectlyIfUserEntityCorrect() {
//
//    User newUser = User.builder().withId("1").withEmail("test@example.com").withPassword("password")
//        .withFirstName("John").withLastName("Doe").withGender("Male").build();
//
//    userServiceImpl.addUser(newUser);
//
//    verify(userRepository).save(newUser);
//  }
//
//  @Test
//  void getUserByIdShouldReturnCorrectUserIfExists() {
//
//    String userId = "1";
//    User mockUser = User.builder().withId(userId).withEmail("test@example.com")
//        .withPassword("password").withFirstName("John").withLastName("Doe").withGender("Male")
//        .build();
//
//    when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
//
//    Optional<User> result = userServiceImpl.getUserById(userId);
//
//    assertThat(result).isPresent();
//    assertThat(mockUser).isEqualTo(result.get());
//  }
//
//  @Test
//  void getUserByIdShouldReturnEmptyIfUserDoesNotExist() {
//
//    String nonExistentId = "nonexistent";
//    when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());
//
//    Optional<User> result = userServiceImpl.getUserById(nonExistentId);
//
//    assertThat(result).isEmpty();
//  }
//
//  @Test
//  void getAllUsersShouldReturnAllUsers() {
//
//    List<User> mockUsers = Arrays.asList(
//        User.builder().withId("1").withEmail("test1@example.com").build(),
//        User.builder().withId("2").withEmail("test2@example.com").build());
//
//    when(userRepository.findAll()).thenReturn(mockUsers);
//
//    List<User> users = userServiceImpl.getAllUsers();
//
//    assertThat(users).hasSize(2).isEqualTo(mockUsers);
//  }
//
//  @Test
//  void getAllUsersWithPaginationShouldReturnCorrectData() {
//
//    User user1 = User.builder().withId("1").withEmail("test1@example.com").build();
//    User user2 = User.builder().withId("2").withEmail("test2@example.com").build();
//
//    List<User> users = Arrays.asList(user1, user2);
//    Page<User> userPage = new PageImpl<>(users);
//
//    when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);
//
//    List<User> returnedUsers = userServiceImpl.getAllUsers(1, 2);
//
//    assertThat(returnedUsers).hasSize(2).containsExactlyInAnyOrder(user1, user2);
//
//    verify(userRepository).findAll(PageRequest.of(0, 2));
//  }
//
//  @Test
//  void updateUserShouldCallSaveMethod() {
//
//    User userToUpdate = User.builder().withId("1").withEmail("update@example.com").build();
//
//    userServiceImpl.updateUser(userToUpdate);
//
//    verify(userRepository).save(userToUpdate);
//  }
//
//  @Test
//  void deleteUserShouldWorkCorrectlyIfUserExists() {
//
//    String userId = "1";
//
//    when(userRepository.existsById(userId)).thenReturn(true);
//
//    boolean result = userServiceImpl.deleteUser(userId);
//
//    assertThat(result).isTrue();
//    verify(userRepository).deleteById(userId);
//  }
//
//  @Test
//  void deleteUserShouldReturnFalseIfUserNotExists() {
//
//    String userId = "nonexistent";
//
//    when(userRepository.existsById(userId)).thenReturn(false);
//
//    boolean result = userServiceImpl.deleteUser(userId);
//
//    assertThat(result).isFalse();
//  }
//
//}
