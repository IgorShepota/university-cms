package ua.foxminded.universitycms.service.impl.user.roles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.dto.user.roles.AdminDTO;
import ua.foxminded.universitycms.mapping.user.roles.AdminMapper;
import ua.foxminded.universitycms.model.entity.user.roles.Admin;
import ua.foxminded.universitycms.repository.user.roles.AdminRepository;
import ua.foxminded.universitycms.service.user.roles.AdminService;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final AdminRepository adminRepository;
  private final AdminMapper adminMapper;

  @Override
  @Transactional
  public void addAdmin(AdminDTO adminDTO) {

    Admin admin = adminMapper.adminDTOToAdmin(adminDTO);
    admin = adminRepository.save(admin);
    log.info("Admin with id {} was successfully saved.", admin.getId());
  }

  @Override
  public Optional<AdminDTO> getAdminById(String id) {

    log.info("Fetching admin with id {}.", id);
    return adminRepository.findById(id).map(adminMapper::adminToAdminDTO);
  }

  @Override
  public List<AdminDTO> getAllAdmins() {

    log.info("Fetching all admins.");
    return adminRepository.findAll().stream()
        .map(adminMapper::adminToAdminDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<AdminDTO> getAllAdmins(Integer page, Integer itemsPerPage) {

    log.info("Fetching page {} of admins with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);
    return adminRepository.findAll(pageable).getContent().stream()
        .map(adminMapper::adminToAdminDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void updateAdmin(AdminDTO adminDTO) {

    log.info("Updating admin: {}", adminDTO);
    Admin admin = adminMapper.adminDTOToAdmin(adminDTO);
    adminRepository.save(admin);
  }

  @Override
  @Transactional
  public boolean deleteAdmin(String id) {

    if (adminRepository.existsById(id)) {
      adminRepository.deleteById(id);
      log.info("Admin with id {} was successfully deleted.", id);
      return true;
    } else {
      log.info("Admin with id {} not found.", id);
      return false;
    }
  }

}
