package ua.foxminded.universitycms.mapping;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mapstruct.factory.Mappers.getMapper;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.foxminded.universitycms.dto.GroupDTO;
import ua.foxminded.universitycms.model.entity.Group;

class GroupMapperTest {

  private final GroupMapper mapper = new GroupMapperImpl();

  @Test
  void groupToGroupDTOShouldWorkCorrectlyIfDataCorrect() {
    Group group = new Group();
    String groupId = UUID.randomUUID().toString();
    group.setId(groupId);
    group.setName("Group A");

    GroupDTO groupDTO = mapper.groupToGroupDTO(group);

    assertThat(groupDTO.getId()).isEqualTo(groupId);
    assertThat(groupDTO.getName()).isEqualTo("Group A");
  }

  @Test
  void groupDTOToGroupShouldWorkCorrectlyIfDataCorrect() {
    GroupDTO groupDTO = new GroupDTO();
    groupDTO.setId(UUID.randomUUID().toString());
    groupDTO.setName("Group B");

    Group group = mapper.groupDTOToGroup(groupDTO);

    assertThat(group).isNotNull();
  }

  @Test
  void groupToGroupDTOShouldReturnNullWhenGroupIsNull() {
    assertThat(mapper.groupToGroupDTO(null)).isNull();
  }

  @Test
  void groupDTOToGroupShouldReturnNullWhenGroupDTOIsNull() {
    assertThat(mapper.groupDTOToGroup(null)).isNull();
  }

}
