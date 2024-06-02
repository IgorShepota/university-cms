package ua.foxminded.universitycms.service.exception;


public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String message) {
    super(message);
  }

}
