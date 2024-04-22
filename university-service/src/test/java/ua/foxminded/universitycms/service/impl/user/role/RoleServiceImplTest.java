package ua.foxminded.universitycms.service.impl.user.role;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ua.foxminded.universitycms.dto.user.role.RoleDTO;
import ua.foxminded.universitycms.mapping.user.role.RoleMapper;
import ua.foxminded.universitycms.model.entity.user.role.Role;
import ua.foxminded.universitycms.model.entity.user.role.RoleName;
import ua.foxminded.universitycms.repository.user.role.RoleRepository;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

  @Mock
  private RoleRepository roleRepository;

  @Mock
  private RoleMapper roleMapper;

  @InjectMocks
  private RoleServiceImpl roleService;

  @Test
  void addRoleShouldWorkCorrectlyIfRoleDTOCorrect() {
    Role role = Role.builder().withId("7e41c62b-222e-4a92-a3c1-f1b6b634b32d")
        .withName(RoleName.STUDENT)
        .build();
    RoleDTO roleDTO = RoleDTO.builder().id(role.getId())
        .name(role.getName().name())
        .build();

    when(roleMapper.roleDTOToRole(any(RoleDTO.class))).thenReturn(role);

    roleService.addRole(roleDTO);

    ArgumentCaptor<Role> roleCaptor = ArgumentCaptor.forClass(Role.class);
    verify(roleRepository).save(roleCaptor.capture());
    Role capturedRole = roleCaptor.getValue();

    assertThat(capturedRole.getId()).isEqualTo(roleDTO.getId());
    assertThat(capturedRole.getName().name()).isEqualTo(roleDTO.getName());
  }

  @Test
  void getRoleByIdShouldReturnRoleDTOWhenRoleExists() {
    String id = "7e41c62b-222e-4a92-a3c1-f1b6b634b32d";
    RoleName name = RoleName.STUDENT;
    Role role = Role.builder().withId(id).withName(name).build();
    RoleDTO roleDTO = RoleDTO.builder().id(id).name(name.name()).build();

    when(roleRepository.findById(id)).thenReturn(Optional.of(role));
    when(roleMapper.roleToRoleDTO(role)).thenReturn(roleDTO);

    Optional<RoleDTO> result = roleService.getRoleById(id);

    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(id);
    assertThat(result.get().getName()).isEqualTo(name.name());
  }

  @Test
  void getRoleByIdShouldReturnEmptyOptionalWhenRoleDoesNotExist() {
    String nonExistentId = "nonexistent";
    when(roleRepository.findById(nonExistentId)).thenReturn(Optional.empty());

    Optional<RoleDTO> result = roleService.getRoleById(nonExistentId);

    assertThat(result).isNotPresent();
  }

  @Test
  void getAllRolesShouldReturnListOfRoleDTOsWhenRolesExist() {
    Role role1 = Role.builder().withId("7e41c62b-222e-4a92-a3c1-f1b6b634b32d")
        .withName(RoleName.STUDENT).build();
    Role role2 = Role.builder().withId("8e41c62b-222e-4a92-a3c1-f1b6b634b32d")
        .withName(RoleName.TEACHER).build();
    RoleDTO roleDTO1 = RoleDTO.builder().id(role1.getId()).name(role1.getName().name()).build();
    RoleDTO roleDTO2 = RoleDTO.builder().id(role2.getId()).name(role2.getName().name()).build();

    when(roleRepository.findAll()).thenReturn(Arrays.asList(role1, role2));
    when(roleMapper.roleToRoleDTO(role1)).thenReturn(roleDTO1);
    when(roleMapper.roleToRoleDTO(role2)).thenReturn(roleDTO2);

    List<RoleDTO> result = roleService.getAllRoles();

    assertThat(result).hasSize(2)
        .containsExactly(roleDTO1, roleDTO2);
  }

  @Test
  void getAllRolesShouldReturnEmptyListWhenNoRolesExist() {
    when(roleRepository.findAll()).thenReturn(Collections.emptyList());

    List<RoleDTO> result = roleService.getAllRoles();

    assertThat(result).isEmpty();
  }

  @Test
  void getAllRolesShouldReturnCorrectPageOfRoleDTOs() {
    Integer page = 1;
    Integer itemsPerPage = 2;
    Role role1 = Role.builder().withId("7e41c62b-222e-4a92-a3c1-f1b6b634b32d")
        .withName(RoleName.STUDENT).build();
    Role role2 = Role.builder().withId("8e41c62b-222e-4a92-a3c1-f1b6b634b32d")
        .withName(RoleName.TEACHER).build();
    RoleDTO roleDTO1 = RoleDTO.builder().id(role1.getId()).name(role1.getName().name()).build();
    RoleDTO roleDTO2 = RoleDTO.builder().id(role2.getId()).name(role2.getName().name()).build();
    Page<Role> rolePage = new PageImpl<>(Arrays.asList(role1, role2));

    when(roleRepository.findAll(any(Pageable.class))).thenReturn(rolePage);
    when(roleMapper.roleToRoleDTO(role1)).thenReturn(roleDTO1);
    when(roleMapper.roleToRoleDTO(role2)).thenReturn(roleDTO2);

    List<RoleDTO> result = roleService.getAllRoles(page, itemsPerPage);

    assertThat(result).hasSize(2)
        .containsExactly(roleDTO1, roleDTO2);
  }

  @Test
  void updateRoleShouldCorrectlyMapAndSaveRole() {
    Role roleToUpdate = Role.builder().withId("8e41c62b-222e-4a92-a3c1-f1b6b634b32d")
        .withName(RoleName.ADMIN).build();
    RoleDTO roleDTOToUpdate = RoleDTO.builder().id(roleToUpdate.getId())
        .name(roleToUpdate.getName().name()).build();

    when(roleMapper.roleDTOToRole(any(RoleDTO.class))).thenReturn(roleToUpdate);

    roleService.updateRole(roleDTOToUpdate);

    verify(roleMapper).roleDTOToRole(roleDTOToUpdate);
    verify(roleRepository).save(roleToUpdate);
  }

  @Test
  void deleteRoleShouldReturnTrueWhenRoleExists() {
    String id = "existing-id";
    when(roleRepository.existsById(id)).thenReturn(true);

    boolean result = roleService.deleteRole(id);

    verify(roleRepository).deleteById(id);
    assertThat(result).isTrue();
  }

  @Test
  void deleteRoleShouldReturnFalseWhenRoleDoesNotExist() {
    String id = "non-existing-id";
    when(roleRepository.existsById(id)).thenReturn(false);

    boolean result = roleService.deleteRole(id);

    verify(roleRepository, never()).deleteById(id);
    assertThat(result).isFalse();
  }

}
