package ua.foxminded.universitycms.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonDTO {

  private String id;
  private LocalDate date;
  private LocalTime startTime;
  private LocalTime endTime;
  private String classroomId;
  private String courseAssignmentId;

}
