package com.student.registration.controller;

import com.student.registration.dto.StudentDto;
import com.student.registration.entity.Student;
import com.student.registration.service.StudentCourseRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/v1/student-registration")
@AllArgsConstructor
public class StudentRegistrationController {

    private final StudentCourseRegistrationService studentCourseRegistrationService;

    @PostConstruct
    public void init() {
        studentCourseRegistrationService.addCourses();
    }

    @GetMapping(value = "/find-student-by-course-name")
    public List<Student> findStudentsByCourseName(@RequestParam("courseName") String courseName) {
        return studentCourseRegistrationService.getFilteredStudentListBasedOnCourseName(courseName);
    }

    @GetMapping(value = "/find-student-not-opted-course")
    public List<Student> findStudentsNotOptedCourse(@RequestParam("courseName") String courseName) {
        return studentCourseRegistrationService.getFilteredStudentListBasedOnCourseNameNotOptedFor(courseName);
    }

    @PostMapping("/add-student")
    public Student addStudent(@RequestBody StudentDto student) {
        return studentCourseRegistrationService.addStudent(student);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteStudent(@RequestBody StudentDto studentDto) {
        studentCourseRegistrationService.deleteStudent(studentDto.getStudentName());
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @PutMapping("/v1/update-course-scores")
    public ResponseEntity<?> updateStudentScores(@RequestBody StudentDto studentDto) {
        studentCourseRegistrationService.addStudentScores(studentDto);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

}
