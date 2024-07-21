package ua.foxminded.universitycms.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.universitycms.dto.user.role.TeacherResponseDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseAssignmentDTO {

  private String id;
  private String courseId;
  private String teacherId;
  private String groupId;

  @Pattern(regexp = "^FLA-\\d{3}$", message = "{group.name.pattern}")
  private String groupName;

  @NotBlank(message = "{course.name.required}")
  @Size(min = 5, max = 100, message = "{course.name.size}")
  private String courseName;

  @NotBlank(message = "{teacher.fullName.required}")
  @Size(min = 2, max = 100, message = "{teacher.fullName.size}")
  private String teacherFullName;

  private List<TeacherResponseDTO> availableTeachers;

}
