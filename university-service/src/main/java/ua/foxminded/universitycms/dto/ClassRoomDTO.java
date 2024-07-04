package ua.foxminded.universitycms.dto;

import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassRoomDTO {

  private String id;

  @Pattern(regexp = "^\\d{3}$", message = "Name must consist of exactly 3 digits")
  private String name;

}
