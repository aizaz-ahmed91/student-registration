package com.student.registration.repository;

import com.student.registration.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    List<Student> findStudentByCourseNameOrderByStudentNameAsc(@Param("courseName") String courseName);

    List<Student> findStudentByNotOptingCourseNameOrderByStudentNameAsc(@Param("courseName") String courseName);

    void deleteStudentByStudentName(String studentName);
}
