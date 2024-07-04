package ua.foxminded.universitycms.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.universitycms.dto.user.role.StudentResponseDTO;
import ua.foxminded.universitycms.model.entity.GroupStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDTO {

  private String id;

  @Pattern(regexp = "^FLA-\\d{3}$", message = "{group.name.pattern}")
  private String name;

  @NotNull(message = "{course.status.required}")
  private GroupStatus status = GroupStatus.NEW;

  @Min(value = 0, message = "{group.courseAssignmentsCount.min}")
  private int courseAssignmentsCount;

  @Min(value = 0, message = "{group.studentCount.min}")
  private int studentCount;

  private List<@Valid StudentResponseDTO> students;
  private List<@Valid CourseAssignmentDTO> courseAssignments;
  private List<@Valid CourseAssignmentDTO> availableCourseAssignments;
  private List<@Valid StudentResponseDTO> availableStudents;

}
