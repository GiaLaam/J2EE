package com.example.KiemTraGiuaKy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ten hoc phan khong duoc de trong")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Hinh anh khong duoc de trong")
    @Column(nullable = false)
    private String image;

    @NotNull(message = "So tin chi khong duoc de trong")
    @Min(value = 1, message = "So tin chi phai lon hon 0")
    @Column(nullable = false)
    private Integer credits;

    @NotBlank(message = "Giang vien khong duoc de trong")
    @Column(nullable = false)
    private String lecturer;

    @NotNull(message = "Danh muc khong duoc de trong")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
