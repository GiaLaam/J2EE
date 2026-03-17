package com.example.KiemTraGiuaKy.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif", "webp");

    private final Path uploadPath;

    public FileStorageService(@Value("${upload.dir}") String uploadDir) throws IOException {
        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.uploadPath);
    }

    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Bạn chưa chọn file ảnh.");
        }

        String originalName = StringUtils.cleanPath(file.getOriginalFilename() == null ? "" : file.getOriginalFilename());
        String extension = getExtension(originalName);
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("Chỉ chấp nhận file ảnh jpg, jpeg, png, gif hoặc webp.");
        }

        String fileName = UUID.randomUUID() + "." + extension;
        Path destination = uploadPath.resolve(fileName);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException("Không thể lưu file ảnh.", e);
        }

        return "/uploads/images/" + fileName;
    }

    public void delete(String imagePath) {
        if (imagePath == null || !imagePath.startsWith("/uploads/images/")) {
            return;
        }

        Path fileName = Paths.get(imagePath).getFileName();
        if (fileName == null) {
            return;
        }

        try {
            Files.deleteIfExists(uploadPath.resolve(fileName.toString()));
        } catch (IOException e) {
            throw new IllegalStateException("Không thể xóa file ảnh cũ.", e);
        }
    }

    private String getExtension(String filename) {
        int lastDot = filename.lastIndexOf('.');
        if (lastDot < 0 || lastDot == filename.length() - 1) {
            throw new IllegalArgumentException("File ảnh phải có định dạng hợp lệ.");
        }
        return filename.substring(lastDot + 1).toLowerCase();
    }
}
