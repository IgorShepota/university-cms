package ua.foxminded.universitycms.dto.coursemanagement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class CourseDTO {

  private String id;
  private String name;
  private String description;

}
