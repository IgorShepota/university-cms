package ua.foxminded.universitycms.mapping.user.role;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ua.foxminded.universitycms.dto.user.role.RoleDTO;
import ua.foxminded.universitycms.model.entity.user.role.Role;
import ua.foxminded.universitycms.model.entity.user.role.RoleName;

class   RoleMapperTest {

  private final RoleMapper mapper = Mappers.getMapper(RoleMapper.class);

  @Test
  void roleToRoleDTOShouldWorkCorrectlyIfDataCorrect() {
    Role role = new Role();
    String uuid = UUID.randomUUID().toString();
    role.setId(uuid);
    role.setName(RoleName.STUDENT);

    RoleDTO roleDTO = mapper.roleToRoleDTO(role);

    assertThat(roleDTO.getId()).isEqualTo(role.getId());
    assertThat(roleDTO.getName()).isEqualTo(role.getName().name());
  }

  @Test
  void roleToRoleDTOShouldMapRoleWhenRoleNameIsNull() {
    Role role = new Role();
    role.setId("1");
    role.setName(null);

    RoleDTO roleDTO = mapper.roleToRoleDTO(role);

    assertThat(roleDTO).isNotNull();
    assertThat(roleDTO.getId()).isEqualTo("1");
    assertThat(roleDTO.getName()).isNull();
  }

  @Test
  void roleDTOToRoleShouldWorkCorrectlyIfDataCorrect() {
    RoleDTO roleDTO = new RoleDTO();
    String uuid = UUID.randomUUID().toString();
    roleDTO.setId(uuid);
    roleDTO.setName("STUDENT");

    Role role = mapper.roleDTOToRole(roleDTO);

    assertThat(role.getId()).isEqualTo(roleDTO.getId());
    assertThat(role.getName().name()).isEqualTo(roleDTO.getName());
  }

  @Test
  void roleDTOToRoleShouldMapRoleDTOWhenRoleNameIsNull() {
    RoleDTO roleDTO = new RoleDTO();
    roleDTO.setId("1");
    roleDTO.setName(null);

    Role role = mapper.roleDTOToRole(roleDTO);

    assertThat(role).isNotNull();
    assertThat(role.getId()).isEqualTo("1");
    assertThat(role.getName()).isEqualTo(RoleName.UNVERIFIED);
  }

  @Test
  void roleToRoleDTOShouldReturnNullWhenRoleIsNull() {
    assertThat(mapper.roleToRoleDTO(null)).isNull();
  }

  @Test
  void roleDTOToRoleShouldReturnNullWhenRoleDTOIsNull() {
    assertThat(mapper.roleDTOToRole(null)).isNull();
  }

}
