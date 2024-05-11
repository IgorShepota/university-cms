CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE classrooms
(
  id   VARCHAR(36) PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE CHECK (name ~ '^[0-9]{3}$')
);

CREATE TABLE groups
(
  id   VARCHAR(36) PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE CHECK (name ~ '^FLA-\d{3}$')
);

CREATE TABLE courses
(
  id          VARCHAR(36) PRIMARY KEY,
  name        VARCHAR(255) NOT NULL UNIQUE,
  description TEXT         NOT NULL UNIQUE
);

CREATE TABLE roles
(
  id   VARCHAR(36) PRIMARY KEY,
  name VARCHAR(255) NOT NULL CHECK (name in ('UNVERIFIED', 'STUDENT', 'TEACHER', 'ADMIN')),
  UNIQUE (name)
);

CREATE TABLE users
(
  id                 VARCHAR(36) PRIMARY KEY,
  email              VARCHAR(255)                        NOT NULL UNIQUE,
  password           VARCHAR(255)                        NOT NULL,
  first_name         VARCHAR(255)                        NOT NULL,
  last_name          VARCHAR(255)                        NOT NULL,
  gender             VARCHAR(255)                        NOT NULL CHECK (gender IN ('Male', 'Female')),
  role_id            VARCHAR(36)                         NOT NULL,
  creation_date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE university_user_data
(
  id        VARCHAR(36) PRIMARY KEY,
  data_type VARCHAR(255) NOT NULL CHECK (data_type in ('STUDENT', 'TEACHER', 'ADMIN')),
  FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE admin_data
(
  id VARCHAR(36) PRIMARY KEY,
  FOREIGN KEY (id) REFERENCES university_user_data (id) ON DELETE CASCADE
);

CREATE TABLE teacher_data
(
  id VARCHAR(36) PRIMARY KEY,
  FOREIGN KEY (id) REFERENCES university_user_data (id) ON DELETE CASCADE
);

CREATE TABLE student_data
(
  id       VARCHAR(36) PRIMARY KEY,
  group_id VARCHAR(36),
  FOREIGN KEY (id) REFERENCES university_user_data (id) ON DELETE CASCADE,
  FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE SET NULL
);

CREATE TABLE teachers_courses
(
  teacher_data_id VARCHAR(36) NOT NULL,
  course_id       VARCHAR(36) NOT NULL,
  PRIMARY KEY (teacher_data_id, course_id),
  FOREIGN KEY (teacher_data_id) REFERENCES teacher_data (id) ON DELETE CASCADE,
  FOREIGN KEY (course_id) REFERENCES courses (id) ON DELETE CASCADE
);

CREATE TABLE course_assignments
(
  id              VARCHAR(36) PRIMARY KEY,
  group_id        VARCHAR(36) NOT NULL,
  course_id       VARCHAR(36) NOT NULL,
  teacher_data_id VARCHAR(36),
  FOREIGN KEY (course_id) REFERENCES courses (id),
  FOREIGN KEY (group_id) REFERENCES groups (id),
  FOREIGN KEY (teacher_data_id) REFERENCES teacher_data (id) ON DELETE SET NULL,
  UNIQUE (group_id, course_id, teacher_data_id)
);

CREATE TABLE lessons
(
  id                   VARCHAR(36) PRIMARY KEY,
  date                 DATE        NOT NULL,
  start_time           TIME        NOT NULL CHECK (start_time < end_time),
  end_time             TIME        NOT NULL,
  classroom_id         VARCHAR(36) NOT NULL,
  course_assignment_id VARCHAR(36) NOT NULL,
  FOREIGN KEY (classroom_id) REFERENCES classrooms (id),
  FOREIGN KEY (course_assignment_id) REFERENCES course_assignments (id),
  UNIQUE (date, start_time, end_time, classroom_id, course_assignment_id)
);
