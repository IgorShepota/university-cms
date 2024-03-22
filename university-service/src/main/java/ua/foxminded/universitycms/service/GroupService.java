package ua.foxminded.universitycms.service;

import java.util.List;
import java.util.Optional;
import ua.foxminded.universitycms.dto.GroupDTO;

public interface GroupService {

  void addGroup(GroupDTO groupDTO);

  Optional<GroupDTO> getGroupById(String id);

  List<GroupDTO> getAllGroups();

  List<GroupDTO> getAllGroups(Integer page, Integer itemsPerPage);

  void updateGroup(GroupDTO groupDTO);

  boolean deleteGroup(String id);

}
