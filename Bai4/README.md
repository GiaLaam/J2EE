# Dự án Quản lý Sản phẩm - Spring Boot

## Mô tả
Dự án này là một ứng dụng web được xây dựng với Spring Boot để quản lý sản phẩm và danh mục sản phẩm. Sử dụng kiến trúc Model-Service-Controller (MSC) để tổ chức code một cách rõ ràng và dễ bảo trì.

## Công nghệ sử dụng
- **Spring Boot 3.1.5**
- **Java 21**
- **Thymeleaf** - Template engine
- **Bootstrap 5** - CSS framework
- **Lombok** - Giảm boilerplate code
- **Jakarta Validation** - Validation framework

## Cấu trúc dự án

```
src/main/java/phattrienungdungvoij2ee/bai4_glsp/
├── controller/
│   └── ProductController.java      # Xử lý các request từ client
├── model/
│   ├── Product.java                # Model cho sản phẩm
│   └── Category.java               # Model cho danh mục
├── service/
│   ├── ProductService.java         # Business logic cho sản phẩm
│   └── CategoryService.java        # Business logic cho danh mục
└── Bai4Application.java            # Main class

src/main/resources/
├── templates/
│   ├── layout.html                 # Layout page chính
│   └── product/
│       ├── products.html           # Danh sách sản phẩm
│       ├── create.html             # Form tạo sản phẩm
│       └── edit.html               # Form chỉnh sửa sản phẩm
├── static/
│   └── images/                     # Thư mục lưu hình ảnh sản phẩm
└── application.properties          # Cấu hình ứng dụng
```

## Các tính năng chính

1. **Quản lý danh mục:**
   - Xem danh sách danh mục
   - Thêm danh mục mới
   - Chỉnh sửa danh mục

2. **Quản lý sản phẩm:**
   - Xem danh sách sản phẩm
   - Thêm sản phẩm mới với hình ảnh
   - Chỉnh sửa thông tin sản phẩm
   - Xóa sản phẩm

3. **Validation:**
   - Kiểm tra tên sản phẩm không để trống
   - Kiểm tra giá sản phẩm hợp lệ
   - Kiểm tra tên danh mục không để trống

## Hướng dẫn cài đặt

### Yêu cầu
- Java 21 trở lên
- Maven 3.6.0 trở lên

### Bước 1: Clone hoặc tải dự án
```bash
cd /Users/nguyenlam/J2EE/Bai4
```

### Bước 2: Cài đặt dependencies
```bash
mvn clean install
```

### Bước 3: Chạy ứng dụng
```bash
mvn spring-boot:run
```

Ứng dụng sẽ khởi động tại `http://localhost:8080`

## Các endpoint chính

- `GET /products` - Xem danh sách sản phẩm
- `GET /products/create` - Hiển thị form tạo sản phẩm
- `POST /products/create` - Tạo sản phẩm mới
- `GET /products/edit/{id}` - Hiển thị form chỉnh sửa sản phẩm
- `POST /products/edit` - Cập nhật sản phẩm

## Lưu ý
- Hình ảnh sản phẩm sẽ được lưu trong thư mục `static/images/`
- Dữ liệu được lưu trong bộ nhớ tạm (in-memory), sẽ bị xóa khi restart ứng dụng
- Để lưu dữ liệu vĩnh viễn, cần tích hợp database (JPA/Hibernate)

## Tác giả
Được tạo cho bài tập J2EE

## License
MIT
