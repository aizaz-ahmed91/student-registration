package com.student.registration.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "COURSE_REGISTRATION")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CourseRegistration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REGISTRATION_ID")
    private Long registrationId;

    @Column(name = "COURSE_ID", nullable = false)
    private Long courseReferenceId;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "COURSE_ID", referencedColumnName = "COURSE_ID", updatable = false, insertable = false)
    private Course course;

    @OneToOne(cascade = CascadeType.MERGE, targetEntity = CourseScores.class)
    @JoinColumn(name = "COURSE_SCORE_ID", referencedColumnName = "COURSE_SCORE_ID", insertable = false)
    private CourseScores courseScores;

}
