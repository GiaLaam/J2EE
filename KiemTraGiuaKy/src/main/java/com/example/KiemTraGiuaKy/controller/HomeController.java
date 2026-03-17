package com.example.KiemTraGiuaKy.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.KiemTraGiuaKy.model.Course;
import com.example.KiemTraGiuaKy.service.CourseService;

@Controller
public class HomeController {

    private static final int PAGE_SIZE = 15;

    private final CourseService courseService;

    public HomeController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping({"/home", "/courses"})
    public String home(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "") String keyword,
        Model model
    ) {
        Page<Course> coursePage = courseService.searchPagedCourses(keyword, page, PAGE_SIZE);
        model.addAttribute("coursePage", coursePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword.trim());
        return "home";
    }
}
