package ua.foxminded.universitycms.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.foxminded.universitycms.dto.LessonDTO;
import ua.foxminded.universitycms.model.entity.Lesson;

@Mapper(componentModel = "spring")
public interface LessonMapper {

  @Mapping(source = "classroom.id", target = "classroomId")
  @Mapping(source = "courseAssignment.id", target = "courseAssignmentId")
  LessonDTO lessonToLessonDTO(Lesson lesson);

  Lesson lessonDTOToLesson(LessonDTO lessonDTO);

}
