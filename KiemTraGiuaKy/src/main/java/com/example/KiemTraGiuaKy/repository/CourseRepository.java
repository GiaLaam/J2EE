package com.example.KiemTraGiuaKy.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KiemTraGiuaKy.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Override
    @EntityGraph(attributePaths = "category")
    Page<Course> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "category")
    Page<Course> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = "category")
    Optional<Course> findById(Long id);
}
