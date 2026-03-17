package com.example.KiemTraGiuaKy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KiemTraGiuaKy.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<Student> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
