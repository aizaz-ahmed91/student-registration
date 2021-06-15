package com.student.registration.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class CourseScores implements Serializable {
    @Column(name = "COURSE_SCORE")
    private Double courseScore;
}
