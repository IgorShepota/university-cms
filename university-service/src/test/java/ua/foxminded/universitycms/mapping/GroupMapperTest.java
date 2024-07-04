package ua.foxminded.universitycms.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import ua.foxminded.universitycms.dto.GroupDTO;
import ua.foxminded.universitycms.model.entity.CourseAssignment;
import ua.foxminded.universitycms.model.entity.Group;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.StudentData;

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

  @Test
  void setCourseAssignmentsCountShouldSetCorrectCount() {
    Group group = new Group();
    group.setCourseAssignments(new HashSet<>(
        Arrays.asList(CourseAssignment.builder().withId("1").build(),
            CourseAssignment.builder().withId("2").build())));
    GroupDTO groupDTO = new GroupDTO();

    mapper.setCourseAssignmentsCount(group, groupDTO);

    assertThat(groupDTO.getCourseAssignmentsCount()).isEqualTo(2);
  }

  @Test
  void setCourseAssignmentsCountShouldHandleNullCourseAssignments() {
    Group group = new Group();
    group.setCourseAssignments(null);
    GroupDTO groupDTO = new GroupDTO();

    mapper.setCourseAssignmentsCount(group, groupDTO);

    assertThat(groupDTO.getCourseAssignmentsCount()).isZero();
  }

  @Test
  void setStudentCountShouldSetCorrectCount() {
    Group group = new Group();
    group.setStudents(new HashSet<>(Arrays.asList(StudentData.builder().withId("1").build(),
        StudentData.builder().withId("2").build())));
    GroupDTO groupDTO = new GroupDTO();

    mapper.setStudentCount(group, groupDTO);

    assertThat(groupDTO.getStudentCount()).isEqualTo(2);
  }

  @Test
  void setStudentCountShouldHandleNullStudents() {
    Group group = new Group();
    group.setStudents(null);
    GroupDTO groupDTO = new GroupDTO();

    mapper.setStudentCount(group, groupDTO);

    assertThat(groupDTO.getStudentCount()).isZero();
  }

}
