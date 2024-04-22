package ua.foxminded.universitycms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseAssignmentDTO {

  private String id;
  private String groupId;
  private String courseId;
  private String teacherDataId;

}
