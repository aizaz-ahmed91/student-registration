package com.student.registration.service;

import com.student.registration.dto.CourseDto;
import com.student.registration.dto.StudentDto;
import com.student.registration.entity.Course;
import com.student.registration.entity.CourseRegistration;
import com.student.registration.entity.CourseScores;
import com.student.registration.entity.Student;
import com.student.registration.repository.CourseRepository;
import com.student.registration.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentCourseRegistrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentCourseRegistrationService.class);

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Student addStudent(StudentDto student) {
        LOGGER.debug("StudentCourseRegistrationService::addStudent: reached with: {}", student);
        Student studentCreated = new Student();
        studentCreated.setStudentName(student.getStudentName());
        Set<CourseRegistration> courseRegistrations = new HashSet<>();
        Map<String, Course> courseMap = getAllCourse().stream().collect(Collectors.toMap(Course::getCourseName, cr -> cr, (crOld, crNew) -> crNew));
        Optional.ofNullable(student.getCourseRegistered()).orElseGet(Collections::emptyList).stream().distinct().forEach(cr -> {
            CourseRegistration courseRegistration = new CourseRegistration();
            Course courseSelection = courseMap.get(cr.getCourseName());
            if (Objects.isNull(courseSelection)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course Name would be exactly same from the list" + courseMap.keySet());
            }
            courseRegistration.setCourseReferenceId(courseSelection.getCourseId());
            courseRegistrations.add(courseRegistration);
        });
        studentCreated.setCourseRegistrations(courseRegistrations);
        LOGGER.debug("StudentCourseRegistrationService::addStudent: after adding course registration: {}", studentCreated);
        return studentRepository.save(studentCreated);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Student addStudentScores(StudentDto student) {
        LOGGER.debug("StudentCourseRegistrationService::addStudentScores: reached with: {}", student);
        Student studentNeedsToBeUpdated = studentRepository.findStudentByStudentNameAndCourseNameIn(student.getStudentName(), student.getCourseRegistered().stream().map(CourseDto::getCourseName).collect(Collectors.toSet()));
        if(Objects.isNull(studentNeedsToBeUpdated))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No student found with the details");
        }
        Map<String, CourseDto> courseScoreMap = student.getCourseRegistered().stream().collect(Collectors.toMap(CourseDto::getCourseName, cr -> cr, (crOld, crNew) -> crNew));
        studentNeedsToBeUpdated.getCourseRegistrations().stream().filter(Objects::nonNull).filter(cr -> courseScoreMap.keySet().contains(cr.getCourse().getCourseName())).forEach(courseRegistration ->
        {
            CourseDto courseDto = courseScoreMap.get(courseRegistration.getCourse().getCourseName());
            CourseScores courseScores = new CourseScores();
            courseScores.setObtainedMarks(courseDto.getMarksObtained());
            courseScores.setTotalMarks(courseDto.getTotalMarks());
            courseRegistration.setCourseScores(courseScores);
        });
        return studentRepository.save(studentNeedsToBeUpdated);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void deleteStudent(String studentName) {
        LOGGER.debug("StudentCourseRegistrationService::deleteStudent: going to delete Student with name: {}", studentName);
        studentRepository.deleteStudentByStudentName(studentName);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<Student> getFilteredStudentListBasedOnCourseName(String courseName) {
        LOGGER.debug("StudentCourseRegistrationService::getFilteredStudentListBasedOnCourseName: fetching student for course: {}", courseName);
        return studentRepository.findStudentByCourseNameOrderByStudentNameAsc(courseName);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<Student> getFilteredStudentListBasedOnCourseNameNotOptedFor(String courseName) {
        LOGGER.debug("StudentCourseRegistrationService::getFilteredStudentListBasedOnCourseNameNotOptedFor: fetching student for course: {}", courseName);
        return studentRepository.findStudentByNotOptingCourseNameOrderByStudentNameAsc(courseName);
    }

    @Cacheable(cacheNames = "courseCache", key = "#courseReferenceDetailKey")
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<Course> getAllCourse() {
        LOGGER.debug("StudentCourseRegistrationService::getAllCourse: fetching all courses");
        return courseRepository.findAll();
    }

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<Course> addCourses() {
        if (courseRepository.getTotalNumberOfCourses() == 0) {
            LOGGER.debug("StudentCourseRegistrationService::addCourses: no courses registered");
            List<Course> courses = new ArrayList<>();
            List<String> courseList = Arrays.asList("B.Tech", "B.Sc.", "B.Pharm.", "B.A.", "B.Com.", "BA. LLB", "M.A", "M.Tech", "M.Sc.", "M.Pharm.", "M.Com");
            for (String cr : courseList) {
                Course course = new Course();
                course.setCourseName(cr);
                courses.add(course);
            }
            return courseRepository.saveAll(courses);
        }
        LOGGER.debug("StudentCourseRegistrationService::addCourses: already courses registered");
        return List.of();
    }
}
