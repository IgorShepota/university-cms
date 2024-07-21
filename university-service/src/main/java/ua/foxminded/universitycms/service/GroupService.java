package ua.foxminded.universitycms.service;

import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import ua.foxminded.universitycms.dto.GroupDTO;
import ua.foxminded.universitycms.dto.user.role.StudentResponseDTO;
import ua.foxminded.universitycms.model.entity.GroupStatus;

public interface GroupService {

  void addGroup(GroupDTO groupDTO);

  Optional<GroupDTO> getGroupById(String id);

  List<GroupDTO> getAllGroups();

  List<GroupDTO> getAllGroups(Integer page, Integer itemsPerPage);

  List<GroupDTO> getAllActiveGroups();

  List<GroupDTO> getGroupsBasedOnUserDetails(UserDetails userDetails);

  void updateGroup(GroupDTO groupDTO);

  void deleteGroup(String id);

  GroupDTO getGroupEditDetails(String groupId);

  void removeCourseAssignmentFromGroup(String assignmentId);

  List<StudentResponseDTO> getStudentWithNoGroup();

  void addStudentToGroup(String groupId, String studentId);

  void removeStudentFromGroup(String studentId);

  GroupStatus changeGroupStatus(String groupId, GroupStatus status);

}
