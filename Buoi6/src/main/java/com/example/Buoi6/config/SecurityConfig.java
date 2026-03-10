package com.example.Buoi6.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                .requestMatchers("/products/delete/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/products/add", "/products/edit/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/products/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .requestMatchers("/categories/delete/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/categories/add", "/categories/edit/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/categories/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .requestMatchers("/order").hasAuthority("ROLE_USER")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/products", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
