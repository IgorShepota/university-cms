package ua.foxminded.universitycms.service.group;

import java.util.List;
import java.util.Optional;
import ua.foxminded.universitycms.model.dto.group.GroupDTO;

public interface GroupService {

  void addGroup(GroupDTO groupDTO);

  Optional<GroupDTO> getGroupById(String id);

  List<GroupDTO> getAllGroups();

  List<GroupDTO> getAllGroups(Integer page, Integer itemsPerPage);

  void updateGroup(GroupDTO groupDTO);

  boolean deleteGroup(String id);

}
