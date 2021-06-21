package com.student.registration.repository;

import com.student.registration.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Integer getTotalNumberOfCourses();
}
