package com.student.registration.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "COURSE")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NamedQuery(name = "Course.getTotalNumberOfCourses", query = "SELECT COUNT(cr) FROM Course cr")
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "COURSE_ID", unique = true, nullable = false)
    private Long courseId;

    @Column(name = "COURSE_NAME", unique = true, nullable = false)
    private String courseName;

}