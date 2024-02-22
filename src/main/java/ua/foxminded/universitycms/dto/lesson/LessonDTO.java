package ua.foxminded.universitycms.dto.lesson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

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
