package com.example.KiemTraGiuaKy.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseForm {

    private Long id;

    @NotBlank(message = "Tên học phần không được để trống")
    private String name;

    @NotNull(message = "Số tín chỉ không được để trống")
    @Min(value = 1, message = "Số tín chỉ phải lớn hơn 0")
    private Integer credits;

    @NotBlank(message = "Giảng viên không được để trống")
    private String lecturer;

    @NotNull(message = "Danh mục không được để trống")
    private Long categoryId;

    private String currentImage;

    private MultipartFile imageFile;
}
