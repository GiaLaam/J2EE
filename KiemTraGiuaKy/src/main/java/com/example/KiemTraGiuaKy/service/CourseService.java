package com.example.KiemTraGiuaKy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.KiemTraGiuaKy.dto.CourseForm;
import com.example.KiemTraGiuaKy.model.Category;
import com.example.KiemTraGiuaKy.model.Course;
import com.example.KiemTraGiuaKy.repository.CategoryRepository;
import com.example.KiemTraGiuaKy.repository.CourseRepository;
import com.example.KiemTraGiuaKy.repository.EnrollmentRepository;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final FileStorageService fileStorageService;

    public CourseService(
        CourseRepository courseRepository,
        CategoryRepository categoryRepository,
        EnrollmentRepository enrollmentRepository,
        FileStorageService fileStorageService
    ) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.fileStorageService = fileStorageService;
    }

    public Page<Course> getPagedCourses(int page, int size) {
        return courseRepository.findAll(PageRequest.of(page, size, Sort.by("id").ascending()));
    }

    public Page<Course> searchPagedCourses(String keyword, int page, int size) {
        String normalizedKeyword = keyword == null ? "" : keyword.trim();
        if (normalizedKeyword.isEmpty()) {
            return getPagedCourses(page, size);
        }
        return courseRepository.findByNameContainingIgnoreCase(
            normalizedKeyword,
            PageRequest.of(page, size, Sort.by("id").ascending())
        );
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll(Sort.by("name").ascending());
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy học phần với id = " + id));
    }

    public Course createCourse(CourseForm form) {
        Course course = new Course();
        applyForm(course, form, true);
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, CourseForm form) {
        Course course = getCourseById(id);
        applyForm(course, form, false);
        return courseRepository.save(course);
    }

    @Transactional
    public void deleteById(Long id) {
        Course course = getCourseById(id);
        enrollmentRepository.deleteAllByCourseId(id);
        fileStorageService.delete(course.getImage());
        courseRepository.delete(course);
    }

    public CourseForm toForm(Course course) {
        CourseForm form = new CourseForm();
        form.setId(course.getId());
        form.setName(course.getName());
        form.setCredits(course.getCredits());
        form.setLecturer(course.getLecturer());
        form.setCategoryId(course.getCategory().getId());
        form.setCurrentImage(course.getImage());
        return form;
    }

    private void applyForm(Course course, CourseForm form, boolean imageRequired) {
        Category managedCategory = categoryRepository.findById(form.getCategoryId())
            .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy danh mục với id = " + form.getCategoryId()));

        course.setName(form.getName());
        course.setCredits(form.getCredits());
        course.setLecturer(form.getLecturer());
        course.setCategory(managedCategory);

        if (form.getImageFile() != null && !form.getImageFile().isEmpty()) {
            fileStorageService.delete(course.getImage());
            course.setImage(fileStorageService.store(form.getImageFile()));
        } else if (imageRequired && (course.getImage() == null || course.getImage().isBlank())) {
            throw new IllegalArgumentException("Bạn chưa chọn file ảnh.");
        }
    }
}
