package com.example.KiemTraGiuaKy.service;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.KiemTraGiuaKy.dto.RegistrationForm;
import com.example.KiemTraGiuaKy.model.Role;
import com.example.KiemTraGiuaKy.model.Student;
import com.example.KiemTraGiuaKy.repository.RoleRepository;
import com.example.KiemTraGiuaKy.repository.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(
        StudentRepository studentRepository,
        RoleRepository roleRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.studentRepository = studentRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerStudent(RegistrationForm form) {
        if (studentRepository.existsByUsername(form.getUsername())) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại.");
        }
        if (studentRepository.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại.");
        }

        Role studentRole = roleRepository.findByName("STUDENT")
            .orElseThrow(() -> new IllegalStateException("Role STUDENT chưa được khởi tạo."));

        Student student = new Student();
        student.setUsername(form.getUsername().trim());
        student.setPassword(passwordEncoder.encode(form.getPassword()));
        student.setEmail(form.getEmail().trim());
        student.setRoles(Set.of(studentRole));

        studentRepository.save(student);
    }

    public Student getStudentByUsername(String username) {
        return studentRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên: " + username));
    }
}
