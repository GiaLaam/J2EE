package com.example.Buoi6.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String author;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    private String description;
    
    private Integer quantity;
    
    private String image;
    
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', category=" + (category != null ? category.getName() : "null") + "}";
    }
}
