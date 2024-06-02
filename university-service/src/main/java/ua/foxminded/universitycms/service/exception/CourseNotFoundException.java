package ua.foxminded.universitycms.service.exception;

public class CourseNotFoundException extends RuntimeException {

  public CourseNotFoundException(String message) {
    super(message);
  }

}
