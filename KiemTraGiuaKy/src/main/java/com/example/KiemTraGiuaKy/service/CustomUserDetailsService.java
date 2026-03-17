package com.example.KiemTraGiuaKy.service;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.KiemTraGiuaKy.repository.StudentRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    public CustomUserDetailsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return studentRepository.findByUsername(username)
            .map(student -> User.withUsername(student.getUsername())
                .password(student.getPassword())
                .authorities(student.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                    .collect(Collectors.toSet()))
                .build())
            .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy tài khoản: " + username));
    }
}
