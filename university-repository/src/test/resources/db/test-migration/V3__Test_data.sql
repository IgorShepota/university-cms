-- Таблица roles
INSERT INTO roles (id, name)
VALUES ('6a9d5f8e-a8d8-11ed-a8fc-0242ac120001', 'ADMIN'),
       ('6a9d5f8e-a8d8-11ed-a8fc-0242ac120004', 'UNVERIFIED'),
       ('6a9d5f8e-a8d8-11ed-a8fc-0242ac120003', 'STUDENT'),
       ('6a9d5f8e-a8d8-11ed-a8fc-0242ac120002', 'TEACHER');

-- Таблица groups
INSERT INTO groups (id, name)
VALUES ('7a9d5f8e-a8d8-11ed-a8fc-0242ac120001', 'FLA-101'),
       ('7a9d5f8e-a8d8-11ed-a8fc-0242ac120002', 'FLA-102'),
       ('7a9d5f8e-a8d8-11ed-a8fc-0242ac120003', 'FLA-103');

-- Таблица courses
INSERT INTO courses (id, name, description, status)
VALUES ('8a9d5f8e-a8d8-11ed-a8fc-0242ac120001', 'Java Programming', 'Introduction to Java programming language', 'ACTIVE'),
       ('8a9d5f8e-a8d8-11ed-a8fc-0242ac120002', 'Database Systems', 'Principles of database design and management', 'ACTIVE'),
       ('8a9d5f8e-a8d8-11ed-a8fc-0242ac120003', 'Web Development', 'Building modern web applications', 'INACTIVE');

-- Таблица classrooms
INSERT INTO classrooms (id, name)
VALUES ('7a9d5f8e-a8d8-11ed-a8fc-0242ac120001', '101'),
       ('9a9d5f8e-a8d8-11ed-a8fc-0242ac120002', '102'),
       ('9a9d5f8e-a8d8-11ed-a8fc-0242ac120003', '103');

-- Таблица users
INSERT INTO users (id, email, password, first_name, last_name, gender, role_id)
VALUES ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120001', 'admin1@example.com', '$2a$10$R8oW9tfadDRK9u1l1IJolOAW7dxi1wQDpq/0t5zdoen9D4qZavfm2', 'Bob', 'Dilan', 'Male', '6a9d5f8e-a8d8-11ed-a8fc-0242ac120001'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120002', 'teacher1@example.com', '$2a$10$EgWyQCP3KLUh/hpOxIwFS.wIp.kdE.OyU51azr8mJmKlvdJZ6Z.0O', 'Michael', 'Johnson', 'Male', '6a9d5f8e-a8d8-11ed-a8fc-0242ac120002'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120003', 'student1@example.com', '$2a$10$wQpzBIjnBx97ShD7ywkGY.n2Opx02LOEbwK2U3gWhCy/w6I7XQ5l.', 'Jane', 'Smith', 'Female', '6a9d5f8e-a8d8-11ed-a8fc-0242ac120003'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120004', 'user1@example.com', '$2a$10$aCErfxSNz8tTQk45ewv8..yTzfOjUfTxEnUwb1vjdrK4rtGZBw5Wi', 'John', 'Doe', 'Male', '6a9d5f8e-a8d8-11ed-a8fc-0242ac120003'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120005', 'monika@example.com', '$2a$10$OPWJGwgGKjYhasCwPvouqejfwjWKisVjxQ0OlPfnUU6trr1aypndy', 'Monica', 'Bellucci', 'Female', '6a9d5f8e-a8d8-11ed-a8fc-0242ac120004'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120006', 'orlando@example.com', '$2a$10$tMvbR/TRlnBxnB90h2k8UOcMGKajlqfCmZjaFwQIlnbDk.wvsVpfi', 'Orlando', 'Blum', 'Male', '6a9d5f8e-a8d8-11ed-a8fc-0242ac120004'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120007', 'jason@example.com', '$2a$10$/HgcaLJJ.xteitmIbwqU/.zG/rjjdhBw/Y511oBIT87onib5X49P2', 'Jason', 'Statham', 'Male', '6a9d5f8e-a8d8-11ed-a8fc-0242ac120004');

-- Таблица university_user_data
INSERT INTO university_user_data (id, data_type)
VALUES ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120001', 'ADMIN'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120002', 'TEACHER'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120003', 'STUDENT'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120004', 'STUDENT');

-- Таблица student_data
INSERT INTO student_data (id, group_id)
VALUES ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120004', '7a9d5f8e-a8d8-11ed-a8fc-0242ac120001'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120003', '7a9d5f8e-a8d8-11ed-a8fc-0242ac120002');

-- Таблица teacher_data
INSERT INTO teacher_data (id)
VALUES ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120002');

-- Таблица admin_data
INSERT INTO admin_data (id)
VALUES ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120001');

-- Таблица teachers_courses
INSERT INTO teachers_courses (teacher_data_id, course_id)
VALUES ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120002', '8a9d5f8e-a8d8-11ed-a8fc-0242ac120001'),
       ('1a9d5f8e-a8d8-11ed-a8fc-0242ac120002', '8a9d5f8e-a8d8-11ed-a8fc-0242ac120002');

-- Таблица course_assignments
INSERT INTO course_assignments (id, group_id, course_id, teacher_data_id)
VALUES ('3a9d5f8e-a8d8-11ed-a8fc-0242ac120002', '7a9d5f8e-a8d8-11ed-a8fc-0242ac120001', '8a9d5f8e-a8d8-11ed-a8fc-0242ac120001', '1a9d5f8e-a8d8-11ed-a8fc-0242ac120002'),
       ('3a9d5f8e-a8d8-11ed-a8fc-0242ac120003', '7a9d5f8e-a8d8-11ed-a8fc-0242ac120002', '8a9d5f8e-a8d8-11ed-a8fc-0242ac120002', '1a9d5f8e-a8d8-11ed-a8fc-0242ac120002'),
       ('3a9d5f8e-a8d8-11ed-a8fc-0242ac120004', '7a9d5f8e-a8d8-11ed-a8fc-0242ac120003', '8a9d5f8e-a8d8-11ed-a8fc-0242ac120003', '1a9d5f8e-a8d8-11ed-a8fc-0242ac120002');

-- Таблица lessons
INSERT INTO lessons (id, date, start_time, end_time, classroom_id, course_assignment_id)
VALUES ('4a9d5f8e-a8d8-11ed-a8fc-0242ac120002', '2023-05-01', '09:00:00', '10:30:00', '7a9d5f8e-a8d8-11ed-a8fc-0242ac120001', '3a9d5f8e-a8d8-11ed-a8fc-0242ac120002'),
       ('4a9d5f8e-a8d8-11ed-a8fc-0242ac120003', '2023-05-02', '11:00:00', '12:30:00', '9a9d5f8e-a8d8-11ed-a8fc-0242ac120002', '3a9d5f8e-a8d8-11ed-a8fc-0242ac120003'),
       ('4a9d5f8e-a8d8-11ed-a8fc-0242ac120004', '2023-05-03', '14:00:00', '15:30:00', '9a9d5f8e-a8d8-11ed-a8fc-0242ac120003', '3a9d5f8e-a8d8-11ed-a8fc-0242ac120004');
