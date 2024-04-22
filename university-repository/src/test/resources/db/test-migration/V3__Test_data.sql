-- Таблица roles
INSERT INTO roles (id, name)
VALUES ('6a9d5f8e-a8d8-11ed-a8fc-0242ac120002', 'UNVERIFIED'),
       ('6a9d5f8e-a8d8-11ed-a8fc-0242ac120003', 'STUDENT'),
       ('6a9d5f8e-a8d8-11ed-a8fc-0242ac120004', 'TEACHER'),
       ('6a9d5f8e-a8d8-11ed-a8fc-0242ac120005', 'ADMIN');

-- Таблица groups
INSERT INTO groups (id, name)
VALUES ('7a9d5f8e-a8d8-11ed-a8fc-0242ac120002', 'FLA-101'),
       ('7a9d5f8e-a8d8-11ed-a8fc-0242ac120003', 'FLA-102'),
       ('7a9d5f8e-a8d8-11ed-a8fc-0242ac120004', 'FLA-103');

-- Таблица courses
INSERT INTO courses (id, name, description)
VALUES ('8a9d5f8e-a8d8-11ed-a8fc-0242ac120002', 'Java Programming', 'Introduction to Java programming language'),
       ('8a9d5f8e-a8d8-11ed-a8fc-0242ac120003', 'Database Systems', 'Principles of database design and management'),
       ('8a9d5f8e-a8d8-11ed-a8fc-0242ac120004', 'Web Development', 'Building modern web applications');

-- Таблица classrooms
INSERT INTO classrooms (id, name)
VALUES ('9a9d5f8e-a8d8-11ed-a8fc-0242ac120002', '101'),
       ('9a9d5f8e-a8d8-11ed-a8fc-0242ac120003', '102'),
       ('9a9d5f8e-a8d8-11ed-a8fc-0242ac120004', '103');

-- Таблица users
INSERT INTO users (id, email, password, first_name, last_name, gender, role_id, university_user_data_id)
VALUES ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120002', 'user1@example.com', 'password1', 'John', 'Doe', 'Male', '6a9d5f8e-a8d8-11ed-a8fc-0242ac120003', '1a9d5f8e-a8d8-11ed-a8fc-0242ac120002'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120003', 'student1@example.com', 'password2', 'Jane', 'Smith', 'Female', '6a9d5f8e-a8d8-11ed-a8fc-0242ac120003', '1a9d5f8e-a8d8-11ed-a8fc-0242ac120003'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120004', 'teacher1@example.com', 'password3', 'Michael', 'Johnson', 'Male', '6a9d5f8e-a8d8-11ed-a8fc-0242ac120004', '1a9d5f8e-a8d8-11ed-a8fc-0242ac120004'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120005', 'admin1@example.com', 'password4', 'Don', 'Bronson', 'Male', '6a9d5f8e-a8d8-11ed-a8fc-0242ac120005', '1a9d5f8e-a8d8-11ed-a8fc-0242ac120005');

-- Таблица university_user_data
INSERT INTO university_user_data (id, data_type)
VALUES ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120002', 'STUDENT'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120003', 'STUDENT'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120004', 'TEACHER'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120005', 'ADMIN');

-- Таблица admin_data
INSERT INTO admin_data (id)
VALUES ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120005');

-- Таблица student_data
INSERT INTO student_data (id, group_id)
VALUES ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120002', '7a9d5f8e-a8d8-11ed-a8fc-0242ac120002'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120003', '7a9d5f8e-a8d8-11ed-a8fc-0242ac120003');

-- Таблица teacher_data
INSERT INTO teacher_data (id)
VALUES ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120004');

-- Таблица teachers_courses
INSERT INTO teachers_courses (teacher_data_id, course_id)
VALUES ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120004', '8a9d5f8e-a8d8-11ed-a8fc-0242ac120002'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120004', '8a9d5f8e-a8d8-11ed-a8fc-0242ac120003');

-- Таблица course_assignments
INSERT INTO course_assignments (id, group_id, course_id, teacher_data_id)
VALUES ('3a9d5f8e-a8d8-11ed-a8fc-0242ac120002', '7a9d5f8e-a8d8-11ed-a8fc-0242ac120002', '8a9d5f8e-a8d8-11ed-a8fc-0242ac120002', '1a9d5f8e-a8d8-11ed-a8fc-0242ac120004'),
       ('3a9d5f8e-a8d8-11ed-a8fc-0242ac120003', '7a9d5f8e-a8d8-11ed-a8fc-0242ac120003', '8a9d5f8e-a8d8-11ed-a8fc-0242ac120003', '1a9d5f8e-a8d8-11ed-a8fc-0242ac120004'),
       ('3a9d5f8e-a8d8-11ed-a8fc-0242ac120004', '7a9d5f8e-a8d8-11ed-a8fc-0242ac120004', '8a9d5f8e-a8d8-11ed-a8fc-0242ac120004', '1a9d5f8e-a8d8-11ed-a8fc-0242ac120004');

-- Таблица lessons
INSERT INTO lessons (id, date, start_time, end_time, classroom_id, course_assignment_id)
VALUES ('4a9d5f8e-a8d8-11ed-a8fc-0242ac120002', '2023-05-01', '09:00:00', '10:30:00', '9a9d5f8e-a8d8-11ed-a8fc-0242ac120002', '3a9d5f8e-a8d8-11ed-a8fc-0242ac120002'),
       ('4a9d5f8e-a8d8-11ed-a8fc-0242ac120003', '2023-05-02', '11:00:00', '12:30:00', '9a9d5f8e-a8d8-11ed-a8fc-0242ac120003', '3a9d5f8e-a8d8-11ed-a8fc-0242ac120003'),
       ('4a9d5f8e-a8d8-11ed-a8fc-0242ac120004', '2023-05-03', '14:00:00', '15:30:00', '9a9d5f8e-a8d8-11ed-a8fc-0242ac120004', '3a9d5f8e-a8d8-11ed-a8fc-0242ac120004');