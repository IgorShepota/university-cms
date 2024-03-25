package ua.foxminded.universitycms.mapping.user.roles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mapstruct.factory.Mappers.getMapper;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.foxminded.universitycms.dto.user.roles.AdminDTO;
import ua.foxminded.universitycms.model.entity.user.roles.Admin;

class AdminMapperTest {

  private final AdminMapper mapper = new AdminMapperImpl();

  @Test
  void adminToAdminDTOShouldMapCorrectlyIfDataIsCorrect() {
    Admin admin = new Admin();
    admin.setId(UUID.randomUUID().toString());
    admin.setEmail("admin@example.com");
    admin.setFirstName("John");
    admin.setLastName("Doe");
    admin.setGender("Male");

    AdminDTO adminDTO = mapper.adminToAdminDTO(admin);

    assertThat(adminDTO.getId()).isEqualTo(admin.getId());
    assertThat(adminDTO.getEmail()).isEqualTo(admin.getEmail());
    assertThat(adminDTO.getFirstName()).isEqualTo(admin.getFirstName());
    assertThat(adminDTO.getLastName()).isEqualTo(admin.getLastName());
    assertThat(adminDTO.getGender()).isEqualTo(admin.getGender());
  }

  @Test
  void adminDTOToAdminShouldReturnNotNullWhenDataIsCorrect() {
    AdminDTO adminDTO = new AdminDTO();
    adminDTO.setId(UUID.randomUUID().toString());
    adminDTO.setEmail("example@admin.com");
    adminDTO.setFirstName("Jane");
    adminDTO.setLastName("Doe");
    adminDTO.setGender("Female");

    Admin admin = mapper.adminDTOToAdmin(adminDTO);

    assertThat(admin).isNotNull();
  }

  @Test
  void adminToAdminDTOShouldReturnNullWhenAdminIsNull() {
    assertThat(mapper.adminToAdminDTO(null)).isNull();
  }

  @Test
  void adminDTOToAdminShouldReturnNullWhenAdminDTOIsNull() {
    assertThat(mapper.adminDTOToAdmin(null)).isNull();
  }

}
