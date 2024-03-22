package ua.foxminded.universitycms.mapping;

import org.mapstruct.Mapper;
import ua.foxminded.universitycms.dto.GroupDTO;
import ua.foxminded.universitycms.model.entity.Group;

@Mapper(componentModel = "spring")
public interface GroupMapper {

  GroupDTO groupToGroupDTO(Group group);

  Group groupDTOToGroup(GroupDTO groupDTO);

}
