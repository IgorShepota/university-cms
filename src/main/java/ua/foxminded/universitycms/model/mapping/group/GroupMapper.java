package ua.foxminded.universitycms.model.mapping.group;

import org.mapstruct.Mapper;
import ua.foxminded.universitycms.model.dto.group.GroupDTO;
import ua.foxminded.universitycms.model.entity.group.Group;

@Mapper(componentModel = "spring")
public interface GroupMapper {

  GroupDTO groupToGroupDTO(Group group);

  Group groupDTOToGroup(GroupDTO groupDTO);

}
