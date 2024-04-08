package ua.foxminded.universitycms.service.user.roles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
import ua.foxminded.universitycms.dto.user.roles.TeacherDTO;
import ua.foxminded.universitycms.mapping.user.roles.TeacherMapper;
import ua.foxminded.universitycms.model.entity.user.roles.Teacher;
import ua.foxminded.universitycms.repository.user.roles.TeacherRepository;
import ua.foxminded.universitycms.service.impl.user.roles.TeacherServiceImpl;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {

  @Mock
  private TeacherRepository teacherRepository;

  @Mock
  private TeacherMapper teacherMapper;

  @InjectMocks
  private TeacherServiceImpl teacherService;

  @Test
  void addTeacherShouldSaveTeacherSuccessfully() {
    TeacherDTO teacherDTO = new TeacherDTO();
    Teacher teacher = new Teacher();

    when(teacherMapper.teacherDTOToTeacher(any(TeacherDTO.class))).thenReturn(teacher);
    when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

    teacherService.addTeacher(teacherDTO);

    verify(teacherMapper).teacherDTOToTeacher(teacherDTO);
    verify(teacherRepository).save(teacher);
  }

  @Test
  void getTeacherByIdShouldReturnTeacherDTOWhenTeacherExists() {
    String id = "existing-id";
    Teacher teacher = new Teacher();
    TeacherDTO expectedDTO = new TeacherDTO();

    when(teacherRepository.findById(id)).thenReturn(Optional.of(teacher));
    when(teacherMapper.teacherToTeacherDTO(teacher)).thenReturn(expectedDTO);

    Optional<TeacherDTO> result = teacherService.getTeacherById(id);

    assertThat(result).isPresent()
        .contains(expectedDTO);
  }

  @Test
  void getTeacherByIdShouldReturnEmptyOptionalWhenTeacherDoesNotExist() {
    String id = "non-existing-id";

    when(teacherRepository.findById(id)).thenReturn(Optional.empty());

    Optional<TeacherDTO> result = teacherService.getTeacherById(id);

    assertThat(result).isNotPresent();
  }

  @Test
  void getAllTeachersShouldReturnListOfTeacherDTOsWhenTeachersExists() {
    List<Teacher> teachers = Arrays.asList(new Teacher(), new Teacher());

    when(teacherRepository.findAll()).thenReturn(teachers);
    when(teacherMapper.teacherToTeacherDTO(any(Teacher.class))).thenAnswer(i -> new TeacherDTO());

    List<TeacherDTO> result = teacherService.getAllTeachers();

    assertThat(result).hasSize(teachers.size());
  }

  @Test
  void getAllTeachersShouldReturnEmptyListWhenNoTeachersExist() {
    when(teacherRepository.findAll()).thenReturn(Collections.emptyList());

    List<TeacherDTO> result = teacherService.getAllTeachers();

    assertThat(result).isEmpty();
  }

  @Test
  void getAllTeachersWithPaginationShouldReturnListOfTeacherDTOsWhenTeachersExists() {
    int page = 1;
    int itemsPerPage = 2;
    Pageable pageable = PageRequest.of(0, itemsPerPage);
    List<Teacher> teachers = Arrays.asList(new Teacher(), new Teacher());
    Page<Teacher> pagedTeachers = new PageImpl<>(teachers, pageable, teachers.size());

    when(teacherRepository.findAll(pageable)).thenReturn(pagedTeachers);
    when(teacherMapper.teacherToTeacherDTO(any(Teacher.class))).thenAnswer(i -> new TeacherDTO());

    List<TeacherDTO> result = teacherService.getAllTeachers(page, itemsPerPage);

    assertThat(result).hasSize(teachers.size());
  }

  @Test
  void updateTeacherShouldSaveUpdatedTeacher() {
    TeacherDTO teacherDTO = new TeacherDTO();
    Teacher teacher = new Teacher();

    when(teacherMapper.teacherDTOToTeacher(any(TeacherDTO.class))).thenReturn(teacher);
    when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

    teacherService.updateTeacher(teacherDTO);

    verify(teacherMapper).teacherDTOToTeacher(teacherDTO);
    verify(teacherRepository).save(teacher);
  }

  @Test
  void deleteTeacherShouldWorkCorrectlyIfTeacherExists() {
    String teacherId = "existing-id";

    when(teacherRepository.existsById(teacherId)).thenReturn(true);

    boolean result = teacherService.deleteTeacher(teacherId);

    assertThat(result).isTrue();
    verify(teacherRepository).deleteById(teacherId);
  }

  @Test
  void deleteTeacherShouldReturnFalseIfTeacherNotExists() {
    String teacherId = "non-existing-id";

    when(teacherRepository.existsById(teacherId)).thenReturn(false);

    boolean result = teacherService.deleteTeacher(teacherId);

    assertThat(result).isFalse();
  }

}
