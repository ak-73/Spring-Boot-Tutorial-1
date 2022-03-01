DROP TABLE IF EXISTS students;
CREATE TABLE students (
  id int unsigned NOT NULL AUTO_INCREMENT,
  name varchar(64) NOT NULL,
  description varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS courses;
CREATE TABLE courses (
  id smallint unsigned NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL UNIQUE,
  description varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS coursesteps;
CREATE TABLE coursesteps (
  course_id smallint unsigned NOT NULL,
  step_id tinyint unsigned NOT NULL,
  description varchar(255) NOT NULL,
  PRIMARY KEY (course_id,step_id),
  CONSTRAINT coursesteps_ibfk_1 FOREIGN KEY (course_id) REFERENCES courses (id)
);


DROP TABLE IF EXISTS registered_for;
CREATE TABLE registered_for (
  student_id int unsigned NOT NULL,
  course_id smallint unsigned NOT NULL,
  PRIMARY KEY (course_id, student_id),
  CONSTRAINT registered_for_ibfk_1 FOREIGN KEY (course_id) REFERENCES courses (id),
  CONSTRAINT registered_for_ibfk_2 FOREIGN KEY (student_id) REFERENCES students (id)
);