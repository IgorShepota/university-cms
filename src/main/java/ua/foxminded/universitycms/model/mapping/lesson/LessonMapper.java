package ua.foxminded.universitycms.model.mapping.lesson;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.universitycms.model.dto.lesson.LessonDTO;
import ua.foxminded.universitycms.model.entity.lesson.Lesson;

@Mapper(componentModel = "spring")
public interface LessonMapper {

  @Mapping(source = "classroom.id", target = "classroomId")
  @Mapping(source = "courseAssignment.id", target = "courseAssignmentId")
  LessonDTO lessonToLessonDTO(Lesson lesson);

  Lesson lessonDTOToLesson(LessonDTO lessonDTO);

}
