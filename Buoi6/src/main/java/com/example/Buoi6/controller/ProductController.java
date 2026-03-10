package com.example.Buoi6.controller;

import com.example.Buoi6.entity.Product;
import com.example.Buoi6.service.CategoryService;
import com.example.Buoi6.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;
    private final CategoryService categoryService;
    
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/list";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "product/form";
    }
    
    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product, 
                           @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            productService.save(product, imageFile);
            return "redirect:/products";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/products/add?error";
        }
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());
        return "product/form";
    }
    
    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, 
                              @ModelAttribute Product product,
                              @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            productService.update(id, product, imageFile);
            return "redirect:/products";
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/products/edit/" + id + "?error";
        }
    }
    
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }
}
