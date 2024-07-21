package ua.foxminded.universitycms.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.foxminded.universitycms.model.entity.user.Gender;

@Data
@AllArgsConstructor
public class StudentSearchCriteria {

  private String id;
  private String firstName;
  private String lastName;
  private String email;
  private Gender gender;
  private String groupName;

}
