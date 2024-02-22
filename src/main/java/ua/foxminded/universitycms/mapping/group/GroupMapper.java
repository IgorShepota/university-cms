package ua.foxminded.universitycms.mapping.group;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.foxminded.universitycms.dto.group.GroupDTO;
import ua.foxminded.universitycms.entity.group.Group;

@Mapper
public interface GroupMapper {

  GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

  GroupDTO groupToGroupDTO(Group group);

  Group groupDTOToGroup(GroupDTO groupDTO);

}
