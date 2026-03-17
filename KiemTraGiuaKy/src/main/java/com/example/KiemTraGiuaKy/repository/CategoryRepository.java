package com.example.KiemTraGiuaKy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.KiemTraGiuaKy.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
