package com.example.KiemTraGiuaKy.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.KiemTraGiuaKy.dto.CourseForm;
import com.example.KiemTraGiuaKy.model.Course;
import com.example.KiemTraGiuaKy.service.CourseService;

@Controller
@RequestMapping("/admin/courses")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCourseController {

    private static final int PAGE_SIZE = 15;

    private final CourseService courseService;

    public AdminCourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String listCourses(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Course> coursePage = courseService.getPagedCourses(page, PAGE_SIZE);
        model.addAttribute("coursePage", coursePage);
        model.addAttribute("currentPage", page);
        return "admin/course-list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        populateForm(model, new CourseForm(), "create");
        return "admin/course-form";
    }

    @PostMapping("/create")
    public String createCourse(
        @Valid @ModelAttribute("course") CourseForm courseForm,
        BindingResult bindingResult,
        Model model,
        RedirectAttributes redirectAttributes
    ) {
        validateImage(courseForm, bindingResult, true);
        if (bindingResult.hasErrors()) {
            populateForm(model, courseForm, "create");
            return "admin/course-form";
        }

        try {
            courseService.createCourse(courseForm);
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("imageFile", "imageFile.invalid", ex.getMessage());
            populateForm(model, courseForm, "create");
            return "admin/course-form";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Thêm học phần thành công.");
        return "redirect:/admin/courses";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        populateForm(model, courseService.toForm(courseService.getCourseById(id)), "edit");
        return "admin/course-form";
    }

    @PostMapping("/{id}/edit")
    public String updateCourse(
        @PathVariable Long id,
        @Valid @ModelAttribute("course") CourseForm courseForm,
        BindingResult bindingResult,
        Model model,
        RedirectAttributes redirectAttributes
    ) {
        validateImage(courseForm, bindingResult, false);
        if (bindingResult.hasErrors()) {
            courseForm.setId(id);
            populateForm(model, courseForm, "edit");
            return "admin/course-form";
        }

        try {
            courseService.updateCourse(id, courseForm);
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("imageFile", "imageFile.invalid", ex.getMessage());
            courseForm.setId(id);
            populateForm(model, courseForm, "edit");
            return "admin/course-form";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật học phần thành công.");
        return "redirect:/admin/courses";
    }

    @PostMapping("/{id}/delete")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        courseService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa học phần thành công.");
        return "redirect:/admin/courses";
    }

    private void populateForm(Model model, CourseForm course, String mode) {
        model.addAttribute("course", course);
        model.addAttribute("categories", courseService.getAllCategories());
        model.addAttribute("mode", mode);
    }

    private void validateImage(CourseForm courseForm, BindingResult bindingResult, boolean required) {
        if (required && (courseForm.getImageFile() == null || courseForm.getImageFile().isEmpty())) {
            bindingResult.rejectValue("imageFile", "imageFile.required", "Bạn chưa chọn file ảnh.");
        }
    }
}
