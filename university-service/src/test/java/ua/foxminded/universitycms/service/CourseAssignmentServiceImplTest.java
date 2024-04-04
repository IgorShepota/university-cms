package ua.foxminded.universitycms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
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
import ua.foxminded.universitycms.dto.CourseAssignmentDTO;
import ua.foxminded.universitycms.mapping.CourseAssignmentMapper;
import ua.foxminded.universitycms.model.entity.CourseAssignment;
import ua.foxminded.universitycms.repository.CourseAssignmentRepository;
import ua.foxminded.universitycms.service.impl.CourseAssignmentServiceImpl;

@ExtendWith(MockitoExtension.class)
class CourseAssignmentServiceImplTest {

  @Mock
  private CourseAssignmentRepository courseAssignmentRepository;

  @Mock
  private CourseAssignmentMapper courseAssignmentMapper;

  @InjectMocks
  private CourseAssignmentServiceImpl courseAssignmentService;

  @Test
  void addCourseAssignmentShouldWorkCorrectlyIfCourseAssignmentEntityCorrect() {
    CourseAssignmentDTO courseAssignmentDTO = new CourseAssignmentDTO();
    courseAssignmentDTO.setId("some-id");
    CourseAssignment courseAssignment = new CourseAssignment();
    courseAssignment.setId(courseAssignmentDTO.getId());

    when(courseAssignmentMapper.courseAssignmentDTOToCourseAssignment(
        any(CourseAssignmentDTO.class)))
        .thenReturn(courseAssignment);

    courseAssignmentService.addCourseAssignment(courseAssignmentDTO);

    verify(courseAssignmentMapper).courseAssignmentDTOToCourseAssignment(courseAssignmentDTO);
    verify(courseAssignmentRepository).save(courseAssignment);
  }

  @Test
  void getCourseAssignmentByIdShouldReturnCourseAssignmentDTOWhenFound() {
    String id = "test-id";
    CourseAssignment courseAssignment = new CourseAssignment();
    CourseAssignmentDTO courseAssignmentDTO = new CourseAssignmentDTO();
    courseAssignmentDTO.setId(id);

    when(courseAssignmentRepository.findById(id)).thenReturn(Optional.of(courseAssignment));
    when(courseAssignmentMapper.courseAssignmentToCourseAssignmentDTO(courseAssignment)).thenReturn(
        courseAssignmentDTO);

    Optional<CourseAssignmentDTO> result = courseAssignmentService.getCourseAssignmentById(id);

    assertThat(result).isPresent();
    assertThat(courseAssignmentDTO).isEqualTo(result.get());
  }

  @Test
  void getCourseAssignmentByIdShouldReturnEmptyOptionalWhenNotFound() {
    String id = "non-existing-id";
    when(courseAssignmentRepository.findById(anyString())).thenReturn(Optional.empty());

    Optional<CourseAssignmentDTO> result = courseAssignmentService.getCourseAssignmentById(id);

    assertThat(result).isNotPresent();
  }

  @Test
  void getAllCourseAssignmentsShouldReturnListOfCourseAssignmentDTOsWhenCourseAssignmentsExist() {
    List<CourseAssignment> courseAssignments = Arrays.asList(new CourseAssignment(),
        new CourseAssignment());
    when(courseAssignmentRepository.findAll()).thenReturn(courseAssignments);
    CourseAssignmentDTO dto = new CourseAssignmentDTO();
    when(courseAssignmentMapper.courseAssignmentToCourseAssignmentDTO(
        any(CourseAssignment.class))).thenReturn(dto);

    List<CourseAssignmentDTO> result = courseAssignmentService.getAllCourseAssignments();

    assertThat(result).hasSize(2);
    assertThat(result.get(0)).isEqualTo(dto);
  }

  @Test
  void getAllCourseAssignmentsShouldReturnsEmptyListWhenNoCourseAssignmentsFound() {
    when(courseAssignmentRepository.findAll()).thenReturn(Collections.emptyList());

    List<CourseAssignmentDTO> result = courseAssignmentService.getAllCourseAssignments();

    assertThat(result).isEmpty();
  }

  @Test
  void getAllCourseAssignmentsShouldReturnCorrectDataWhenCourseAssignmentsExist() {
    Pageable pageable = PageRequest.of(0, 1);
    List<CourseAssignment> courseAssignments = Arrays.asList(new CourseAssignment(),
        new CourseAssignment());
    Page<CourseAssignment> page = new PageImpl<>(courseAssignments);
    when(courseAssignmentRepository.findAll(pageable)).thenReturn(page);
    CourseAssignmentDTO dto = new CourseAssignmentDTO();
    when(courseAssignmentMapper.courseAssignmentToCourseAssignmentDTO(
        any(CourseAssignment.class))).thenReturn(dto);

    List<CourseAssignmentDTO> result = courseAssignmentService.getAllCourseAssignments(1, 1);

    assertThat(result).hasSize(2);
    assertThat(result.get(0)).isEqualTo(dto);
  }


  @Test
  void updateCourseAssignmentShouldCorrectlyMapAndSaveCourseAssignment() {
    CourseAssignmentDTO courseAssignmentDTO = new CourseAssignmentDTO();
    CourseAssignment courseAssignment = new CourseAssignment();

    when(courseAssignmentMapper.courseAssignmentDTOToCourseAssignment(
        any(CourseAssignmentDTO.class))).thenReturn(courseAssignment);

    courseAssignmentService.updateCourseAssignment(courseAssignmentDTO);

    verify(courseAssignmentMapper).courseAssignmentDTOToCourseAssignment(courseAssignmentDTO);
    verify(courseAssignmentRepository).save(courseAssignment);
  }

  @Test
  void deleteCourseAssignmentReturnsTrueWhenCourseAssignmentExists() {
    String id = "existing-id";
    when(courseAssignmentRepository.existsById(id)).thenReturn(true);

    boolean result = courseAssignmentService.deleteCourseAssignment(id);

    verify(courseAssignmentRepository).deleteById(id);
    assertThat(result).isTrue();
  }

  @Test
  void deleteCourseAssignmentReturnsFalseWhenCourseAssignmentDoesNotExist() {
    String id = "non-existing-id";
    when(courseAssignmentRepository.existsById(id)).thenReturn(false);

    boolean result = courseAssignmentService.deleteCourseAssignment(id);

    verify(courseAssignmentRepository, never()).deleteById(id);
    assertThat(result).isFalse();
  }

}
