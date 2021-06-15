CREATE TABLE Student
(
    StudentId   int          NOT NULL UNIQUE AUTO_INCREMENT,
    StudentName varchar(255) NOT NULL,
    PRIMARY KEY (StudentId)
);

CREATE TABLE Course
(
    CourseId    int          NOT NULL UNIQUE AUTO_INCREMENT,
    CourseName  varchar(255) NOT NULL,
    CourseScore DOUBLE,
    PRIMARY KEY (CourseId),
    FOREIGN KEY (StudentId) REFERENCES Student (StudentId)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
);
