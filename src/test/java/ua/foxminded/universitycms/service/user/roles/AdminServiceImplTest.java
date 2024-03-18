package ua.foxminded.universitycms.service.user.roles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.foxminded.universitycms.model.entity.user.roles.Admin;
import ua.foxminded.universitycms.repository.user.roles.AdminRepository;
import ua.foxminded.universitycms.service.user.roles.admin.AdminServiceImpl;

@SpringBootTest
class AdminServiceImplTest {

  @MockBean
  private AdminRepository adminRepository;

  @Autowired
  private AdminServiceImpl adminServiceImpl;

  @Test
  void addAdminShouldWorkCorrectlyIfAdminEntityCorrect() {

    Admin newAdmin = Admin.builder().withId("1").withEmail("admin@example.com")
        .withFirstName("Admin").withLastName("User").build();

    adminServiceImpl.addAdmin(newAdmin);

    verify(adminRepository).save(newAdmin);
  }

  @Test
  void getAdminByIdShouldReturnCorrectAdminIfExists() {

    String adminId = "1";
    Admin mockAdmin = Admin.builder().withId(adminId).withEmail("admin@example.com")
        .withFirstName("Admin").withLastName("User").build();

    when(adminRepository.findById(adminId)).thenReturn(Optional.of(mockAdmin));

    Optional<Admin> result = adminServiceImpl.getAdminById(adminId);

    assertThat(result).isPresent();
    assertThat(mockAdmin).isEqualTo(result.get());
  }

  @Test
  void getAdminByIdShouldReturnEmptyIfAdminDoesNotExist() {

    String nonExistentId = "nonexistent";
    when(adminRepository.findById(nonExistentId)).thenReturn(Optional.empty());

    Optional<Admin> result = adminServiceImpl.getAdminById(nonExistentId);

    assertThat(result).isEmpty();
  }

  @Test
  void getAllAdminsShouldReturnAllAdmins() {

    List<Admin> mockAdmins = Arrays.asList(
        Admin.builder().withId("1").withEmail("admin1@example.com").build(),
        Admin.builder().withId("2").withEmail("admin2@example.com").build());

    when(adminRepository.findAll()).thenReturn(mockAdmins);

    List<Admin> admins = adminServiceImpl.getAllAdmins();

    assertThat(admins).hasSize(2).isEqualTo(mockAdmins);
  }

  @Test
  void getAllAdminsWithPaginationShouldReturnCorrectData() {

    Admin admin1 = Admin.builder().withId("1").withEmail("admin1@example.com").build();
    Admin admin2 = Admin.builder().withId("2").withEmail("admin2@example.com").build();

    List<Admin> admins = Arrays.asList(admin1, admin2);
    Page<Admin> adminPage = new PageImpl<>(admins);

    when(adminRepository.findAll(any(Pageable.class))).thenReturn(adminPage);

    List<Admin> returnedAdmins = adminServiceImpl.getAllAdmins(1, 2);

    assertThat(returnedAdmins).hasSize(2).containsExactlyInAnyOrder(admin1, admin2);

    verify(adminRepository).findAll(PageRequest.of(0, 2));
  }

  @Test
  void updateAdminShouldCallSaveMethod() {

    Admin adminToUpdate = Admin.builder().withId("1").withEmail("update@example.com").build();

    adminServiceImpl.updateAdmin(adminToUpdate);

    verify(adminRepository).save(adminToUpdate);
  }

  @Test
  void deleteAdminShouldWorkCorrectlyIfAdminExists() {

    String adminId = "1";

    when(adminRepository.existsById(adminId)).thenReturn(true);

    boolean result = adminServiceImpl.deleteAdmin(adminId);

    assertThat(result).isTrue();
    verify(adminRepository).deleteById(adminId);
  }

  @Test
  void deleteAdminShouldReturnFalseIfAdminNotExists() {

    String adminId = "nonexistent";

    when(adminRepository.existsById(adminId)).thenReturn(false);

    boolean result = adminServiceImpl.deleteAdmin(adminId);

    assertThat(result).isFalse();
  }

}
