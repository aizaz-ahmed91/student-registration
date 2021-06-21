use
STUDENT_REGISTRATION_DB;

drop table if exists STUDENT_REGISTRATION_DB.STUDENT;

create table STUDENT_REGISTRATION_DB.STUDENT
(
    STUDENT_ID   bigint       not null auto_increment comment 'Unique ID',
    STUDENT_NAME varchar(250) not null comment 'Student name',
    primary key (STUDENT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

alter table STUDENT_REGISTRATION_DB.STUDENT comment 'Student Information Table';


drop table if exists STUDENT_REGISTRATION_DB.COURSE;

create table STUDENT_REGISTRATION_DB.COURSE
(
    COURSE_ID   bigint       not null auto_increment comment 'Unique ID',
    COURSE_NAME varchar(250) not null comment 'Course name',
    primary key (COURSE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

alter table STUDENT_REGISTRATION_DB.COURSE comment 'COURSE Reference Table';



drop table if exists STUDENT_REGISTRATION_DB.COURSE_REGISTRATION;

create table STUDENT_REGISTRATION_DB.COURSE_REGISTRATION
(
    REGISTRATION_ID bigint not null auto_increment comment 'Unique ID',
    COURSE_ID       bigint not null comment 'Reference Course Id',
    STUDENT_ID      bigint not null comment 'Student Id',
    COURSE_SCORE_ID bigint not null comment 'Course Score Id',
    primary key (REGISTRATION_ID),
    key             AK_CODR_IDX1 (COURSE_ID),
    key             AK_CODR_IDX2 (STUDENT_ID),
    key             AK_CODR_IDX3 (SCOURSE_SCORE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

alter table STUDENT_REGISTRATION_DB.COURSE_REGISTRATION comment 'COURSE Registration Table';

drop table if exists STUDENT_REGISTRATION_DB.COURSE_SCORES;

create table STUDENT_REGISTRATION_DB.COURSE_SCORES
(
    COURSE_SCORE_ID bigint not null auto_increment comment 'Unique ID',
    MARKS_OBTAINED  double not null comment 'Marks Obtained in Course',
    TOTAL_MARKS     bigint not null comment 'Total Marks'
        primary key (COURSE_SCORE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

alter table STUDENT_REGISTRATION_DB.COURSE_SCORES comment 'COURSE Scores Table';