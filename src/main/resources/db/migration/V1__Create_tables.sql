CREATE TABLE classrooms
(
    id   UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE CHECK (name ~ '^[0-9]{3}$')
);

CREATE TABLE groups
(
    id   UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE CHECK (name ~ '^FLA-\d{3}$')
);

CREATE TABLE courses
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    description TEXT         NOT NULL UNIQUE
);

CREATE TABLE users
(
    id         UUID PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    gender     VARCHAR(255) NOT NULL CHECK (gender IN ('Male', 'Female'))
);

CREATE TABLE admins
(
    user_id UUID PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE teachers
(
    user_id UUID PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE students
(
    user_id  UUID PRIMARY KEY,
    group_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (group_id) REFERENCES groups (id)
);

CREATE TABLE teachers_courses
(
    teacher_id UUID NOT NULL,
    course_id  UUID NOT NULL,
    PRIMARY KEY (teacher_id, course_id),
    FOREIGN KEY (teacher_id) REFERENCES teachers (user_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses (id) ON DELETE CASCADE
);

CREATE TABLE course_assignments
(
    id         UUID PRIMARY KEY,
    group_id   UUID NOT NULL,
    course_id  UUID NOT NULL,
    teacher_id UUID NOT NULL,
    FOREIGN KEY (course_id) REFERENCES courses (id),
    FOREIGN KEY (group_id) REFERENCES groups (id),
    FOREIGN KEY (teacher_id) REFERENCES teachers (user_id),
    UNIQUE (group_id, course_id, teacher_id)
);

CREATE TABLE lessons
(
    id                   UUID PRIMARY KEY,
    date                 DATE NOT NULL,
    start_time           TIME NOT NULL CHECK (start_time < end_time),
    end_time             TIME NOT NULL,
    classroom_id         UUID NOT NULL,
    course_assignment_id UUID NOT NULL,
    FOREIGN KEY (classroom_id) REFERENCES classrooms (id),
    FOREIGN KEY (course_assignment_id) REFERENCES course_assignments (id),
    UNIQUE (date, start_time, end_time, classroom_id, course_assignment_id)
);
