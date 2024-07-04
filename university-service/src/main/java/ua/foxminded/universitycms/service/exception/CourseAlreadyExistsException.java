package ua.foxminded.universitycms.service.exception;

public class CourseAlreadyExistsException extends RuntimeException {

  public CourseAlreadyExistsException(String message) {
    super(message);
  }

}
