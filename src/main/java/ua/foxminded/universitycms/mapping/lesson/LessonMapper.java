package ua.foxminded.universitycms.mapping.lesson;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.foxminded.universitycms.dto.lesson.LessonDTO;
import ua.foxminded.universitycms.entity.lesson.Lesson;

@Mapper
public interface LessonMapper {

  LessonMapper INSTANCE = Mappers.getMapper(LessonMapper.class);

  @Mapping(source = "classroom.id", target = "classroomId")
  @Mapping(source = "courseAssignment.id", target = "courseAssignmentId")
  LessonDTO lessonToLessonDTO(Lesson lesson);

  @Mapping(target = "classroom", ignore = true)
  @Mapping(target = "courseAssignment", ignore = true)
  Lesson lessonDTOToLesson(LessonDTO lessonDTO);
}
