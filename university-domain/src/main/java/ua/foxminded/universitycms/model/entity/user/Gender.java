package ua.foxminded.universitycms.model.entity.user;

public enum Gender {
  MALE("Male"),
  FEMALE("Female");

  private final String displayName;

  Gender(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
