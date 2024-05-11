package ua.foxminded.universitycms.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {

  private String id;

  @NotBlank(message = "{course.name.required}")
  @Size(min = 5, max = 100, message = "{course.name.size}")
  private String name;

  @NotBlank(message = "{description.required}")
  @Size(min = 10, max = 1000, message = "{description.size}")
  private String description;

}
