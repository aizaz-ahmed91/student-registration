package com.student.registration.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "STUDENT")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = "Student.findStudentByCourseNameOrderByStudentNameAsc",
                query = "SELECT DISTINCT s FROM Student s JOIN FETCH s.courseRegistrations cr where cr.course.courseName IN (:courseName) order by s.studentName ASC")
        ,
        @NamedQuery(name = "Student.findStudentByStudentNameAndCourseNameIn",
                query = "SELECT DISTINCT s FROM Student s JOIN FETCH s.courseRegistrations cr where s.studentName=:studentName AND cr.course.courseName IN (:courseNames) order by s.studentName ASC")
        ,
        @NamedQuery(name = "Student.findStudentByNotOptingCourseNameOrderByStudentNameAsc",
                query = "SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.courseRegistrations cr LEFT JOIN FETCH cr.course crs where s.courseRegistrations IS EMPTY OR :courseName NOT IN(SELECT DISTINCT crs.courseName FROM cr.course crs) order by s.studentName ASC")
})
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_ID", unique = true, nullable = false)
    private Long studentId;

    @Column(name = "STUDENT_NAME", nullable = false)
    private String studentName;

    @JsonIgnoreProperties(value = {"student"})
    @OneToMany(cascade = CascadeType.ALL, targetEntity = CourseRegistration.class)
    @JoinColumn(name = "STUDENT_ID", referencedColumnName = "STUDENT_ID")
    private Set<CourseRegistration> courseRegistrations = new HashSet<>();

}
