package com.student.registration.repository;

import com.student.registration.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findStudentByStudentNameAndCourseNameIn(@Param("studentName") String studentName, @Param("courseNames") Set<String> courseName);

    List<Student> findStudentByCourseNameOrderByStudentNameAsc(@Param("courseName") String courseName);

    List<Student> findStudentByNotOptingCourseNameOrderByStudentNameAsc(@Param("courseName") String courseName);

    void deleteStudentByStudentName(String studentName);

}
