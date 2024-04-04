package ua.foxminded.universitycms.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.universitycms.dto.CourseAssignmentDTO;
import ua.foxminded.universitycms.mapping.CourseAssignmentMapper;
import ua.foxminded.universitycms.model.entity.CourseAssignment;
import ua.foxminded.universitycms.repository.CourseAssignmentRepository;
import ua.foxminded.universitycms.service.CourseAssignmentService;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseAssignmentServiceImpl implements CourseAssignmentService {

  private final CourseAssignmentRepository courseAssignmentRepository;
  private final CourseAssignmentMapper courseAssignmentMapper;

  @Override
  @Transactional
  public void addCourseAssignment(CourseAssignmentDTO courseAssignmentDTO) {
    CourseAssignment courseAssignment = courseAssignmentMapper.courseAssignmentDTOToCourseAssignment(
        courseAssignmentDTO);
    courseAssignmentRepository.save(courseAssignment);
    log.info("CourseAssignment with id {} was successfully saved.", courseAssignmentDTO.getId());
  }

  @Override
  public Optional<CourseAssignmentDTO> getCourseAssignmentById(String id) {
    log.info("Fetching CourseAssignment with id {}.", id);
    return courseAssignmentRepository.findById(id)
        .map(courseAssignmentMapper::courseAssignmentToCourseAssignmentDTO);
  }


  @Override
  public List<CourseAssignmentDTO> getAllCourseAssignments() {
    log.info("Fetching all CourseAssignments.");
    List<CourseAssignment> courseAssignments = courseAssignmentRepository.findAll();
    return courseAssignments.stream()
        .map(courseAssignmentMapper::courseAssignmentToCourseAssignmentDTO)
        .collect(Collectors.toList());
  }


  @Override
  public List<CourseAssignmentDTO> getAllCourseAssignments(Integer page, Integer itemsPerPage) {
    log.info("Fetching page {} of CourseAssignments with {} items per page.", page, itemsPerPage);
    Pageable pageable = PageRequest.of(page - 1, itemsPerPage);

    return courseAssignmentRepository.findAll(pageable).stream()
        .map(courseAssignmentMapper::courseAssignmentToCourseAssignmentDTO)
        .collect(Collectors.toList());
  }


  @Override
  @Transactional
  public void updateCourseAssignment(CourseAssignmentDTO courseAssignmentDTO) {
    log.info("Updating CourseAssignment: {}", courseAssignmentDTO);
    CourseAssignment courseAssignment = courseAssignmentMapper.courseAssignmentDTOToCourseAssignment(
        courseAssignmentDTO);
    courseAssignmentRepository.save(courseAssignment);
  }

  @Override
  @Transactional
  public boolean deleteCourseAssignment(String id) {
    if (courseAssignmentRepository.existsById(id)) {
      courseAssignmentRepository.deleteById(id);
      log.info("CourseAssignment with id {} was successfully deleted.", id);
      return true;
    } else {
      log.info("CourseAssignment with id {} not found.", id);
      return false;
    }
  }

}
