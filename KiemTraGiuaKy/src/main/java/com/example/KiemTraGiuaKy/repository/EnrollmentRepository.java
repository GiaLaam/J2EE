package com.example.KiemTraGiuaKy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.KiemTraGiuaKy.model.Course;
import com.example.KiemTraGiuaKy.model.Enrollment;
import com.example.KiemTraGiuaKy.model.Student;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentAndCourse(Student student, Course course);

    @Query("""
        select e from Enrollment e
        join fetch e.course c
        join fetch c.category
        where e.student.id = :studentId
        order by e.enrollDate desc, e.id desc
    """)
    List<Enrollment> findAllByStudentId(Long studentId);

    Optional<Enrollment> findByIdAndStudentId(Long id, Long studentId);

    void deleteAllByCourseId(Long courseId);
}
