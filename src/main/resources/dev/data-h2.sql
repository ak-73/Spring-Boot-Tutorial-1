INSERT INTO courses (id, name, description)
VALUES (1, 'Spring', '10 Steps'),
(2, 'Spring MVC', '10 Examples'),
(3, 'Spring Boot', '6K Students'),
(4, 'Maven', 'Most popular maven course on the internet!');


INSERT INTO students (id, name, description)
VALUES (1, 'Martin Fowler', 'Clean Code fanatic'),
(2, 'Erich Gamma', 'Something something patterns'),
(3, 'Michael Widenius', 'He will never write any software of note');


INSERT INTO coursesteps (course_id, step_id, description)
VALUES (1, 1, 'Learn Maven'), 
(1, 2, 'Import Project'), 
(1, 3, 'First Example'), 
(1, 4, 'Second Example'), 
(2, 1, 'Learn Maven'), 
(2, 2, 'Import Project'), 
(2, 3, 'First Example'), 
(2, 4, 'Second Example'), 
(3, 1, 'Learn Maven'), 
(3, 2, 'Learn Spring'), 
(3, 3, 'Learn Spring MVC'), 
(3, 4, 'First Example'), 
(3, 5, 'Second Example'), 
(4, 1, 'Pom.xml'), 
(4, 2, 'Build Life Cycle'),
(4, 3, 'Parent POM'),
(4, 4, 'Importing into Eclipse');


INSERT INTO registered_for (student_id, course_id)
VALUES (1, 1),
(1, 2),
(1, 4),
(2, 1);

INSERT INTO guestlecturers (title, firstname, lastname, description)
VALUES ('Prof.', 'Alexander', 'Kalinowski', 'He''s a pretty smart dude'),
('Dr.', 'Peter L.', 'Einstein', 'He doesn''t keep the most sophisticated company'),
('Dr.', 'Victor', 'Frankenstein', 'What is going on in his basement at nights?');

INSERT INTO holds_guest_lecture (guest_id, course_id)
VALUES (1,1),
(2,1),
(2,2),
(2,3),
(3,4);