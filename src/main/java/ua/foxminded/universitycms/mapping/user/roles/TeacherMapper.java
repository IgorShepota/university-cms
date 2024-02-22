package ua.foxminded.universitycms.mapping.user.roles;

import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ua.foxminded.universitycms.dto.user.roles.TeacherDTO;
import ua.foxminded.universitycms.entity.coursemanagement.Course;
import ua.foxminded.universitycms.entity.coursemanagement.CourseAssignment;
import ua.foxminded.universitycms.entity.user.roles.Teacher;

@Mapper
public interface TeacherMapper {

  TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

  @Mapping(source = "courses", target = "courseIds", qualifiedByName = "coursesToCourseIds")
  @Mapping(source = "courseAssignments", target = "courseAssignmentIds", qualifiedByName = "courseAssignmentsToCourseAssignmentIds")
  TeacherDTO teacherToTeacherDTO(Teacher teacher);

  @Named("coursesToCourseIds")
  static List<String> coursesToCourseIds(List<Course> courses) {
    return courses.stream().map(Course::getId).collect(Collectors.toList());
  }

  @Named("courseAssignmentsToCourseAssignmentIds")
  static List<String> courseAssignmentsToCourseAssignmentIds(List<CourseAssignment> courseAssignments) {
    return courseAssignments.stream().map(CourseAssignment::getId).collect(Collectors.toList());
  }

}
