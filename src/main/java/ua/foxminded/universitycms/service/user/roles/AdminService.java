package ua.foxminded.universitycms.service.user.roles;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.entity.user.roles.Admin;
import ua.foxminded.universitycms.repository.user.roles.AdminRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

  private final AdminRepository adminRepository;

  @Transactional
  public void addAdmin(Admin admin) {
    adminRepository.save(admin);
    log.info("Admin with id {} was successfully saved.", admin.getId());
  }

  public Optional<Admin> getAdminById(String id) {
    log.info("Fetching admin with id {}.", id);
    return adminRepository.findById(id);
  }

  public List<Admin> getAllAdmins() {
    log.info("Fetching all admins.");
    return adminRepository.findAll();
  }

  public List<Admin> getAllAdmins(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of admins with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);

    return adminRepository.findAll(pageable).getContent();
  }

  @Transactional
  public void updateAdmin(Admin admin) {
    log.info("Updating admin: {}", admin);
    adminRepository.save(admin);
  }

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
