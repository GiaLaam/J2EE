package com.example.KiemTraGiuaKy.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.KiemTraGiuaKy.service.EnrollmentService;

@Controller
@PreAuthorize("hasRole('STUDENT') and !hasRole('ADMIN')")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enroll/{courseId}")
    public String enroll(
        @PathVariable Long courseId,
        Principal principal,
        RedirectAttributes redirectAttributes
    ) {
        try {
            enrollmentService.enroll(principal.getName(), courseId);
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký học phần thành công.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/courses";
    }

    @GetMapping("/enroll/my-courses")
    public String myCourses(Principal principal, Model model) {
        model.addAttribute("enrollments", enrollmentService.getEnrollmentsForStudent(principal.getName()));
        return "enrollment-list";
    }

    @PostMapping("/enroll/{enrollmentId}/delete")
    public String deleteEnrollment(
        @PathVariable Long enrollmentId,
        Principal principal,
        RedirectAttributes redirectAttributes
    ) {
        try {
            enrollmentService.deleteEnrollment(principal.getName(), enrollmentId);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa học phần đã đăng ký thành công.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/enroll/my-courses";
    }
}
