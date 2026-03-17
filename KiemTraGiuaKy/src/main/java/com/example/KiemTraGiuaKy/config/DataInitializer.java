package com.example.KiemTraGiuaKy.config;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.KiemTraGiuaKy.model.Category;
import com.example.KiemTraGiuaKy.model.Course;
import com.example.KiemTraGiuaKy.model.Role;
import com.example.KiemTraGiuaKy.model.Student;
import com.example.KiemTraGiuaKy.repository.CategoryRepository;
import com.example.KiemTraGiuaKy.repository.CourseRepository;
import com.example.KiemTraGiuaKy.repository.RoleRepository;
import com.example.KiemTraGiuaKy.repository.StudentRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedData(
        RoleRepository roleRepository,
        StudentRepository studentRepository,
        CategoryRepository categoryRepository,
        CourseRepository courseRepository,
        PasswordEncoder passwordEncoder
    ) {
        return args -> {
            Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> roleRepository.save(new Role(null, "ADMIN")));
            Role studentRole = roleRepository.findByName("STUDENT")
                .orElseGet(() -> roleRepository.save(new Role(null, "STUDENT")));

            Student admin = studentRepository.findByUsername("admin").orElseGet(Student::new);
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123"));
            admin.setEmail("admin@example.com");
            admin.setRoles(Set.of(adminRole));

            Student student = studentRepository.findByUsername("student").orElseGet(Student::new);
            student.setUsername("student");
            student.setPassword(passwordEncoder.encode("123"));
            student.setEmail("student@example.com");
            student.setRoles(Set.of(studentRole));

            studentRepository.saveAll(List.of(admin, student));

            if (courseRepository.count() > 0) {
                return;
            }

            Category web = categoryRepository.save(new Category(null, "Phat trien web"));
            Category database = categoryRepository.save(new Category(null, "Co so du lieu"));
            Category backend = categoryRepository.save(new Category(null, "Lap trinh backend"));

            Course javaCore = new Course(null, "Java Core", "/images/course-placeholder.svg", 3, "Nguyen Van A", backend);
            Course springBoot = new Course(null, "Spring Boot", "/images/course-placeholder.svg", 4, "Tran Thi B", backend);
            Course sql = new Course(null, "SQL Server", "/images/course-placeholder.svg", 3, "Le Minh C", database);
            Course htmlCss = new Course(null, "HTML CSS", "/images/course-placeholder.svg", 2, "Pham Thi D", web);
            Course javascript = new Course(null, "JavaScript", "/images/course-placeholder.svg", 3, "Hoang Van E", web);
            Course restApi = new Course(null, "REST API", "/images/course-placeholder.svg", 3, "Vo Thi F", backend);

            courseRepository.saveAll(List.of(javaCore, springBoot, sql, htmlCss, javascript, restApi));
        };
    }
}
