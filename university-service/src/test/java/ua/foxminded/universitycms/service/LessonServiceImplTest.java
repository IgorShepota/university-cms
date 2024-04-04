package ua.foxminded.universitycms.service;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.foxminded.universitycms.dto.LessonDTO;
import ua.foxminded.universitycms.mapping.LessonMapper;
import ua.foxminded.universitycms.model.entity.Lesson;
import ua.foxminded.universitycms.repository.LessonRepository;
import ua.foxminded.universitycms.service.impl.LessonServiceImpl;

@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {

  @Mock
  private LessonRepository lessonRepository;

  @Mock
  private LessonMapper lessonMapper;

  @InjectMocks
  private LessonServiceImpl lessonService;

  @Test
  void addLessonShouldWorkCorrectlyIfLessonEntityCorrect() {
    LessonDTO lessonDTO = new LessonDTO();
    lessonDTO.setId("test-id");
    Lesson lesson = new Lesson();
    lesson.setId(lessonDTO.getId());

    when(lessonMapper.lessonDTOToLesson(any(LessonDTO.class))).thenReturn(lesson);
    when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

    lessonService.addLesson(lessonDTO);

    verify(lessonMapper).lessonDTOToLesson(lessonDTO);
    verify(lessonRepository).save(lesson);
  }

  @Test
  void getLessonByIdShouldReturnLessonDTOWhenLessonExists() {
    String id = "existing-id";
    Lesson lesson = new Lesson();
    LessonDTO expectedDTO = new LessonDTO();
    when(lessonRepository.findById(id)).thenReturn(Optional.of(lesson));
    when(lessonMapper.lessonToLessonDTO(lesson)).thenReturn(expectedDTO);

    Optional<LessonDTO> result = lessonService.getLessonById(id);

    assertThat(result).isPresent().
        contains(expectedDTO);
  }

  @Test
  void getLessonByIdShouldReturnEmptyOptionalWhenLessonDoesNotExist() {
    String id = "non-existing-id";
    when(lessonRepository.findById(anyString())).thenReturn(Optional.empty());

    Optional<LessonDTO> result = lessonService.getLessonById(id);

    assertThat(result).isNotPresent();
  }

  @Test
  void getAllLessonsShouldReturnListOfLessonDTOsWhenLessonsExist() {
    Lesson lesson1 = new Lesson();
    Lesson lesson2 = new Lesson();
    List<Lesson> lessons = asList(lesson1, lesson2);
    LessonDTO dto1 = new LessonDTO();
    LessonDTO dto2 = new LessonDTO();

    when(lessonRepository.findAll()).thenReturn(lessons);
    when(lessonMapper.lessonToLessonDTO(lesson1)).thenReturn(dto1);
    when(lessonMapper.lessonToLessonDTO(lesson2)).thenReturn(dto2);

    List<LessonDTO> result = lessonService.getAllLessons();

    assertThat(result).hasSize(2)
        .containsExactly(dto1, dto2);
  }

  @Test
  void getAllLessonsShouldReturnEmptyListWhenNoLessonsExist() {
    when(lessonRepository.findAll()).thenReturn(emptyList());

    List<LessonDTO> result = lessonService.getAllLessons();

    assertThat(result).isEmpty();
  }

  @Test
  void getAllLessonsWithPaginationShouldReturnListOfLessonDTOsWhenLessonsExist() {
    int page = 1;
    int itemsPerPage = 2;
    Lesson lesson1 = new Lesson();
    Lesson lesson2 = new Lesson();
    List<Lesson> lessons = Arrays.asList(lesson1, lesson2);
    Page<Lesson> pagedLessons = new PageImpl<>(lessons, PageRequest.of(0, itemsPerPage),
        lessons.size());
    LessonDTO dto1 = new LessonDTO();
    LessonDTO dto2 = new LessonDTO();

    when(lessonRepository.findAll(any(Pageable.class))).thenReturn(pagedLessons);
    when(lessonMapper.lessonToLessonDTO(lesson1)).thenReturn(dto1);
    when(lessonMapper.lessonToLessonDTO(lesson2)).thenReturn(dto2);

    List<LessonDTO> result = lessonService.getAllLessons(page, itemsPerPage);

    assertThat(result).hasSize(2)
        .containsExactly(dto1, dto2);
  }

  @Test
  void getAllLessonsWithPaginationShouldReturnEmptyListWhenNoLessonsExist() {
    int page = 1;
    int itemsPerPage = 2;
    Page<Lesson> pagedLessons = new PageImpl<>(Collections.emptyList(),
        PageRequest.of(0, itemsPerPage), 0);

    when(lessonRepository.findAll(any(Pageable.class))).thenReturn(pagedLessons);

    List<LessonDTO> result = lessonService.getAllLessons(page, itemsPerPage);

    assertThat(result).isEmpty();
  }

  @Test
  void updateLessonShouldCorrectlyMapAndSaveLesson() {
    LessonDTO lessonDTO = new LessonDTO();
    Lesson lesson = new Lesson();

    when(lessonMapper.lessonDTOToLesson(any(LessonDTO.class))).thenReturn(lesson);

    lessonService.updateLesson(lessonDTO);

    verify(lessonMapper).lessonDTOToLesson(lessonDTO);
    verify(lessonRepository).save(lesson);
  }

  @Test
  void deleteLessonShouldWorkCorrectlyIfLessonExists() {
    String lessonId = "existing-id";

    when(lessonRepository.existsById(lessonId)).thenReturn(true);

    boolean result = lessonService.deleteLesson(lessonId);

    assertThat(result).isTrue();
    verify(lessonRepository).deleteById(lessonId);
  }

  @Test
  void deleteLessonShouldReturnFalseIfLessonNotExists() {
    String lessonId = "non-existing-id";

    when(lessonRepository.existsById(lessonId)).thenReturn(false);

    boolean result = lessonService.deleteLesson(lessonId);

    assertThat(result).isFalse();
  }

}
