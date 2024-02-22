package ua.foxminded.universitycms.service.group;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.entity.group.Group;
import ua.foxminded.universitycms.repository.group.GroupRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

  private final GroupRepository groupRepository;

  @Transactional
  public void addGroup(Group group) {
    groupRepository.save(group);
    log.info("Group with id {} was successfully saved.", group.getId());
  }

  public Optional<Group> getGroupById(String id) {
    log.info("Fetching group with id {}.", id);
    return groupRepository.findById(id);
  }

  public List<Group> getAllGroups() {
    log.info("Fetching all groups.");
    return groupRepository.findAll();
  }

  public List<Group> getAllGroups(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of groups with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);

    return groupRepository.findAll(pageable).getContent();
  }

  @Transactional
  public void updateGroup(Group group) {
    log.info("Updating group: {}", group);
    groupRepository.save(group);
  }

  @Transactional
  public boolean deleteGroup(String id) {
    if (groupRepository.existsById(id)) {
      groupRepository.deleteById(id);
      log.info("Group with id {} was successfully deleted.", id);
      return true;
    } else {
      log.info("Group with id {} not found.", id);
      return false;
    }
  }

}
