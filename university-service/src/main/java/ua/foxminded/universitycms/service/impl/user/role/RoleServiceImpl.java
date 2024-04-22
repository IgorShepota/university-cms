package ua.foxminded.universitycms.service.impl.user.role;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.foxminded.universitycms.dto.user.role.RoleDTO;
import ua.foxminded.universitycms.mapping.user.role.RoleMapper;
import ua.foxminded.universitycms.model.entity.user.role.Role;
import ua.foxminded.universitycms.repository.user.role.RoleRepository;
import ua.foxminded.universitycms.service.user.role.RoleService;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;
  private final RoleMapper roleMapper;

  @Override
  @Transactional
  public void addRole(RoleDTO roleDTO) {
    Role role = roleMapper.roleDTOToRole(roleDTO);
    roleRepository.save(role);
    log.info("Role with id {} was successfully saved.", roleDTO.getId());
  }

  @Override
  public Optional<RoleDTO> getRoleById(String id) {
    log.info("Fetching role with id {}.", id);
    return roleRepository.findById(id).map(roleMapper::roleToRoleDTO);
  }

  @Override
  public List<RoleDTO> getAllRoles() {
    log.info("Fetching all roles.");
    return roleRepository.findAll().stream()
        .map(roleMapper::roleToRoleDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<RoleDTO> getAllRoles(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of roles with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);
    return roleRepository.findAll(pageable).getContent().stream()
        .map(roleMapper::roleToRoleDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void updateRole(RoleDTO roleDTO) {
    log.info("Updating role: {}", roleDTO);
    Role role = roleMapper.roleDTOToRole(roleDTO);
    roleRepository.save(role);
  }

  @Override
  @Transactional
  public boolean deleteRole(String id) {
    if (roleRepository.existsById(id)) {
      roleRepository.deleteById(id);
      log.info("Role with id {} was successfully deleted.", id);
      return true;
    } else {
      log.info("Role with id {} not found.", id);
      return false;
    }
  }

}
