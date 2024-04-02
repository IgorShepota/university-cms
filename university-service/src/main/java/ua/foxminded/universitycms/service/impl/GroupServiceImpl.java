package ua.foxminded.universitycms.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.dto.GroupDTO;
import ua.foxminded.universitycms.mapping.GroupMapper;
import ua.foxminded.universitycms.model.entity.Group;
import ua.foxminded.universitycms.repository.GroupRepository;
import ua.foxminded.universitycms.service.GroupService;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService {

  private final GroupRepository groupRepository;
  private final GroupMapper groupMapper;

  @Override
  @Transactional
  public void addGroup(GroupDTO groupDTO) {
    Group group = groupMapper.groupDTOToGroup(groupDTO);
    group = groupRepository.save(group);
    log.info("Group with id {} was successfully saved.", group.getId());
  }

  @Override
  public Optional<GroupDTO> getGroupById(String id) {
    log.info("Fetching group with id {}.", id);
    return groupRepository.findById(id).map(groupMapper::groupToGroupDTO);
  }

  @Override
  public List<GroupDTO> getAllGroups() {
    log.info("Fetching all groups.");
    return groupRepository.findAll().stream()
        .map(groupMapper::groupToGroupDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<GroupDTO> getAllGroups(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of groups with {} items per page.", page, itemsPerPage);
    Pageable pageable = Pageable.ofSize(itemsPerPage).withPage(page - 1);
    return groupRepository.findAll(pageable).getContent().stream()
        .map(groupMapper::groupToGroupDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void updateGroup(GroupDTO groupDTO) {
    log.info("Updating group: {}", groupDTO);
    Group group = groupMapper.groupDTOToGroup(groupDTO);
    groupRepository.save(group);
  }

  @Override
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
