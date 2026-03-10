package com.example.Buoi6.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Value("${upload.path}")
    private String uploadPath;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File uploadDir = new File(uploadPath);
        String uploadDirPath = uploadDir.getAbsolutePath();
        
        if (!uploadDirPath.endsWith(File.separator)) {
            uploadDirPath += File.separator;
        }
        
        registry.addResourceHandler("/uploads/images/**")
                .addResourceLocations("file:" + uploadDirPath);
    }
}
