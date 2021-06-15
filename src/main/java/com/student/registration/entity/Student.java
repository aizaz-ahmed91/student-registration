package com.student.registration.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "STUDENT")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = "Student.findStudentByCourseNameOrderByStudentNameAsc",
                query = "SELECT DISTINCT s FROM Student s JOIN FETCH s.courses cr where cr.courseName=:courseName order by s.studentName ASC")
        ,
        @NamedQuery(name = "Student.findStudentByNotOptingCourseNameOrderByStudentNameAsc",
                query = "SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.courses cr where s.courses IS EMPTY OR :courseName NOT IN(SELECT DISTINCT crs.courseName FROM s.courses crs) order by s.studentName ASC")
})
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENT_ID", unique = true, nullable = false)
    private Long studentId;

    @Column(name = "STUDENT_NAME", nullable = false)
    private String studentName;

    @JsonIgnoreProperties(value = {"student"})
    @OneToMany(targetEntity = Course.class, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "STUDENT_ID", referencedColumnName = "STUDENT_ID")
    private List<Course> courses = new ArrayList<>();

}
