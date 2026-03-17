package com.example.KiemTraGiuaKy.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.KiemTraGiuaKy.model.Course;
import com.example.KiemTraGiuaKy.model.Enrollment;
import com.example.KiemTraGiuaKy.model.Student;
import com.example.KiemTraGiuaKy.repository.EnrollmentRepository;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseService courseService;
    private final StudentService studentService;

    public EnrollmentService(
        EnrollmentRepository enrollmentRepository,
        CourseService courseService,
        StudentService studentService
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    public void enroll(String username, Long courseId) {
        Student student = studentService.getStudentByUsername(username);
        Course course = courseService.getCourseById(courseId);

        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new IllegalArgumentException("Bạn đã đăng ký học phần này.");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollDate(LocalDate.now());
        enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getEnrollmentsForStudent(String username) {
        Student student = studentService.getStudentByUsername(username);
        return enrollmentRepository.findAllByStudentId(student.getId());
    }

    public void deleteEnrollment(String username, Long enrollmentId) {
        Student student = studentService.getStudentByUsername(username);
        Enrollment enrollment = enrollmentRepository.findByIdAndStudentId(enrollmentId, student.getId())
            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy học phần đã đăng ký."));
        enrollmentRepository.delete(enrollment);
    }
}
