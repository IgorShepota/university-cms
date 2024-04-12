package ua.foxminded.universitycms.service.user.roles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.foxminded.universitycms.dto.user.roles.AdminDTO;
import ua.foxminded.universitycms.mapping.user.roles.AdminMapper;
import ua.foxminded.universitycms.repository.user.roles.AdminRepository;
import ua.foxminded.universitycms.service.impl.user.roles.AdminServiceImpl;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

  @Mock
  private AdminRepository adminRepository;

  @Mock
  private AdminMapper adminMapper;

  @InjectMocks
  private AdminServiceImpl adminService;

  @Test
  void addAdminShouldWorkCorrectlyIfAdminEntityCorrect() {
    AdminDTO adminDTO = new AdminDTO();
    Admin admin = new Admin();

    when(adminMapper.adminDTOToAdmin(any(AdminDTO.class))).thenReturn(admin);
    when(adminRepository.save(any(Admin.class))).thenReturn(admin);

    adminService.addAdmin(adminDTO);

    verify(adminMapper).adminDTOToAdmin(adminDTO);
    verify(adminRepository).save(admin);
  }

  @Test
  void getAdminByIdShouldReturnAdminDTOWhenAdminExists() {
    String id = "test-id";
    Admin admin = new Admin();
    AdminDTO expectedDTO = new AdminDTO();

    when(adminRepository.findById(id)).thenReturn(Optional.of(admin));
    when(adminMapper.adminToAdminDTO(admin)).thenReturn(expectedDTO);

    Optional<AdminDTO> result = adminService.getAdminById(id);

    assertThat(result).isPresent()
        .contains(expectedDTO);
  }

  @Test
  void getAdminByIdShouldReturnEmptyOptionalWhenAdminDoesNotExist() {
    String id = "non-existing-id";

    when(adminRepository.findById(id)).thenReturn(Optional.empty());

    Optional<AdminDTO> result = adminService.getAdminById(id);

    assertThat(result).isNotPresent();
  }

  @Test
  void getAllAdminsShouldReturnListOfAdminDTOs() {
    List<Admin> admins = Arrays.asList(new Admin(), new Admin());
    List<AdminDTO> expectedDTOs = admins.stream().map(admin -> new AdminDTO())
        .collect(Collectors.toList());

    when(adminRepository.findAll()).thenReturn(admins);
    when(adminMapper.adminToAdminDTO(any(Admin.class))).thenAnswer(i -> new AdminDTO());

    List<AdminDTO> result = adminService.getAllAdmins();

    assertThat(result).hasSize(admins.size())
        .isEqualTo(expectedDTOs);
  }

  @Test
  void getAllAdminsShouldReturnEmptyListWhenNoAdminsExist() {
    when(adminRepository.findAll()).thenReturn(Collections.emptyList());

    List<AdminDTO> result = adminService.getAllAdmins();

    assertThat(result).isEmpty();
  }

  @Test
  void getAllAdminsWithPaginationShouldReturnListOfAdminDTOs() {
    int page = 1;
    int itemsPerPage = 2;
    Pageable pageable = PageRequest.of(0, itemsPerPage);
    List<Admin> admins = Arrays.asList(new Admin(), new Admin());
    Page<Admin> pagedAdmins = new PageImpl<>(admins, pageable, admins.size());

    when(adminRepository.findAll(pageable)).thenReturn(pagedAdmins);
    when(adminMapper.adminToAdminDTO(any(Admin.class))).thenAnswer(i -> new AdminDTO());

    List<AdminDTO> result = adminService.getAllAdmins(page, itemsPerPage);

    assertThat(result).hasSize(admins.size());
  }

  @Test
  void updateAdminShouldSaveUpdatedAdmin() {
    AdminDTO adminDTO = new AdminDTO();
    Admin admin = new Admin();

    when(adminMapper.adminDTOToAdmin(adminDTO)).thenReturn(admin);
    when(adminRepository.save(admin)).thenReturn(admin);

    adminService.updateAdmin(adminDTO);

    verify(adminMapper).adminDTOToAdmin(adminDTO);
    verify(adminRepository).save(admin);
  }

  @Test
  void deleteAdminShouldWorkCorrectlyIfAdminExists() {
    String adminId = "existing-id";

    when(adminRepository.existsById(adminId)).thenReturn(true);

    boolean result = adminService.deleteAdmin(adminId);

    assertThat(result).isTrue();
    verify(adminRepository).deleteById(adminId);
  }

  @Test
  void deleteAdminShouldReturnFalseIfAdminNotExists() {
    String adminId = "non-existing-id";

    when(adminRepository.existsById(adminId)).thenReturn(false);

    boolean result = adminService.deleteAdmin(adminId);

    assertThat(result).isFalse();
  }

}
