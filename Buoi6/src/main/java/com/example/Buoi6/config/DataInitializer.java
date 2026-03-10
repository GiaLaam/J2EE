package com.example.Buoi6.config;

import com.example.Buoi6.entity.Account;
import com.example.Buoi6.entity.Category;
import com.example.Buoi6.entity.Product;
import com.example.Buoi6.entity.Role;
import com.example.Buoi6.repository.AccountRepository;
import com.example.Buoi6.repository.CategoryRepository;
import com.example.Buoi6.repository.ProductRepository;
import com.example.Buoi6.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            Role roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleRepository.save(roleUser);
            
            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleRepository.save(roleAdmin);
            
            System.out.println("✓ Created roles");
        }
        
        if (accountRepository.count() == 0) {
            Role roleAdmin = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
            Role roleUser = roleRepository.findByName("ROLE_USER").orElseThrow();
            
            Account admin = new Account();
            admin.setLoginName("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(roleAdmin);
            admin.setRoles(adminRoles);
            accountRepository.save(admin);
            
            Account user = new Account();
            user.setLoginName("user");
            user.setPassword(passwordEncoder.encode("user123"));
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(roleUser);
            user.setRoles(userRoles);
            accountRepository.save(user);
            
            System.out.println("✓ Created default accounts (admin/admin123, user/user123)");
        }
        
        if (categoryRepository.count() == 0) {
            Category vanHoc = new Category();
            vanHoc.setName("Văn học");
            vanHoc.setDescription("Sách văn học trong và ngoài nước");
            categoryRepository.save(vanHoc);
            
            Category khoaHoc = new Category();
            khoaHoc.setName("Khoa học");
            khoaHoc.setDescription("Sách khoa học và công nghệ");
            categoryRepository.save(khoaHoc);
            
            Category kinhTe = new Category();
            kinhTe.setName("Kinh tế");
            kinhTe.setDescription("Sách về kinh tế và kinh doanh");
            categoryRepository.save(kinhTe);
            
            System.out.println("✓ Created default categories");
            
            if (productRepository.count() == 0) {
                Product p1 = new Product();
                p1.setName("Số đỏ");
                p1.setAuthor("Vũ Trọng Phụng");
                p1.setPrice(new BigDecimal("50000"));
                p1.setQuantity(100);
                p1.setDescription("Tiểu thuyết nổi tiếng của Vũ Trọng Phụng");
                p1.setCategory(vanHoc);
                productRepository.save(p1);
                
                Product p2 = new Product();
                p2.setName("Lão Hạc");
                p2.setAuthor("Nam Cao");
                p2.setPrice(new BigDecimal("35000"));
                p2.setQuantity(150);
                p2.setDescription("Truyện ngắn nổi tiếng của Nam Cao");
                p2.setCategory(vanHoc);
                productRepository.save(p2);
                
                Product p3 = new Product();
                p3.setName("Java Programming");
                p3.setAuthor("Herbert Schildt");
                p3.setPrice(new BigDecimal("250000"));
                p3.setQuantity(50);
                p3.setDescription("Complete reference for Java programming");
                p3.setCategory(khoaHoc);
                productRepository.save(p3);
                
                Product p4 = new Product();
                p4.setName("Đắc Nhân Tâm");
                p4.setAuthor("Dale Carnegie");
                p4.setPrice(new BigDecimal("80000"));
                p4.setQuantity(200);
                p4.setDescription("Sách về kỹ năng giao tiếp và ứng xử");
                p4.setCategory(kinhTe);
                productRepository.save(p4);
                
                System.out.println("✓ Created sample products");
            }
        }
    }
}
