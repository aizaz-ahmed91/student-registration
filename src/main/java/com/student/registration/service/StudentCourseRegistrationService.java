package com.student.registration.service;

import com.student.registration.dto.StudentDto;
import com.student.registration.entity.Course;
import com.student.registration.entity.CourseScores;
import com.student.registration.entity.Student;
import com.student.registration.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentCourseRegistrationService {

    private final StudentRepository studentRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Student addStudent(StudentDto student) {
        Student studentCreated = new Student();
        studentCreated.setStudentName(student.getStudentName());
        List<Course> courses = new ArrayList<>();
        Optional.ofNullable(student.getCourseRegistered()).orElseGet(Collections::emptyList).stream().distinct().forEach(cr -> {
            Course course = new Course();
            course.setCourseName(cr.getCourseName());
            CourseScores courseScores = new CourseScores();
            courseScores.setCourseScore(cr.getCourseScore());
            course.setCourseScores(courseScores);
            courses.add(course);
        });
        studentCreated.setCourses(courses);
        return studentRepository.save(studentCreated);

    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void deleteStudent(String studentName) {
        studentRepository.deleteStudentByStudentName(studentName);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<Student> getFilteredStudentListBasedOnCourseName(String courseName) {
        return studentRepository.findStudentByCourseNameOrderByStudentNameAsc(courseName);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<Student> getFilteredStudentListBasedOnCourseNameNotOptedFor(String courseName) {
        return studentRepository.findStudentByNotOptingCourseNameOrderByStudentNameAsc(courseName);
    }
}
