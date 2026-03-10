package com.example.Buoi6.service;

import com.example.Buoi6.entity.Category;
import com.example.Buoi6.entity.Product;
import com.example.Buoi6.repository.CategoryRepository;
import com.example.Buoi6.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;
    
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
    
    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
    
    public List<Product> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<Product> findByAuthor(String author) {
        return productRepository.findByAuthorContainingIgnoreCase(author);
    }
    
    @Transactional
    public Product save(Product product, MultipartFile imageFile) throws IOException {
        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + product.getCategory().getId()));
        
        if (imageFile != null && !imageFile.isEmpty()) {
            String filename = fileUploadService.uploadFile(imageFile);
            product.setImage(filename);
        }
        
        product.setCategory(category);
        return productRepository.save(product);
    }
    
    @Transactional
    public Product update(Long id, Product productDetails, MultipartFile imageFile) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        Category category = categoryRepository.findById(productDetails.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + productDetails.getCategory().getId()));
        
        if (imageFile != null && !imageFile.isEmpty()) {
            if (product.getImage() != null) {
                fileUploadService.deleteFile(product.getImage());
            }
            String filename = fileUploadService.uploadFile(imageFile);
            product.setImage(filename);
        }
        
        product.setName(productDetails.getName());
        product.setAuthor(productDetails.getAuthor());
        product.setPrice(productDetails.getPrice());
        product.setDescription(productDetails.getDescription());
        product.setQuantity(productDetails.getQuantity());
        product.setCategory(category);
        
        return productRepository.save(product);
    }
    
    @Transactional
    public void deleteById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null && product.getImage() != null) {
            fileUploadService.deleteFile(product.getImage());
        }
        productRepository.deleteById(id);
    }
}
