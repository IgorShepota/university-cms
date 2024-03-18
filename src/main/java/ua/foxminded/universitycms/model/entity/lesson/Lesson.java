package ua.foxminded.universitycms.model.entity.lesson;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ua.foxminded.universitycms.model.entity.classroom.ClassRoom;
import ua.foxminded.universitycms.model.entity.coursemanagement.CourseAssignment;

@Entity
@Table(name = "lessons")
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Data
public class Lesson {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(name = "date", nullable = false)
  @NotNull(message = "Date cannot be null")
  private LocalDate date;

  @Column(name = "start_time", nullable = false)
  @NotNull(message = "Start time cannot be null")
  private LocalTime startTime;

  @Column(name = "end_time", nullable = false)
  @NotNull(message = "End time cannot be null")
  private LocalTime endTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "classroom_id", nullable = false)
  private ClassRoom classroom;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_assignment_id", nullable = false)
  private CourseAssignment courseAssignment;

}
