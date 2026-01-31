import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookManager {
    private List<Book> books;

    // Constructor
    public BookManager() {
        this.books = new ArrayList<>();
    }

    // 1. Thêm 1 cuốn sách
    public void themSach(Book book) {
        // Kiểm tra xem mã sách đã tồn tại chưa
        for (Book b : books) {
            if (b.getMaSach() == book.getMaSach()) {
                System.out.println("Lỗi: Mã sách đã tồn tại!");
                return;
            }
        }
        books.add(book);
        System.out.println("Thêm sách thành công!");
    }

    // 2. Xóa 1 cuốn sách
    public void xoaSach(int maSach) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getMaSach() == maSach) {
                books.remove(i);
                System.out.println("Xóa sách thành công!");
                return;
            }
        }
        System.out.println("Lỗi: Không tìm thấy sách với mã này!");
    }

    // 3. Thay đổi cuốn sách
    public void thayDoiSach(int maSach, String tenSach, String tacGia, double donGia) {
        for (Book book : books) {
            if (book.getMaSach() == maSach) {
                book.setTenSach(tenSach);
                book.setTacGia(tacGia);
                book.setDonGia(donGia);
                System.out.println("Thay đổi sách thành công!");
                return;
            }
        }
        System.out.println("Lỗi: Không tìm thấy sách với mã này!");
    }

    // 4. Xuất thông tin tất cả các cuốn sách
    public void xuatTatCaSach() {
        if (books.isEmpty()) {
            System.out.println("Danh sách sách trống!");
            return;
        }
        System.out.println("\n========== DANH SÁCH TẤT CẢ SÁCH ==========");
        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println("==========================================\n");
    }

    // 5. Tìm cuốn sách có tựa đề chứa chữ "Lập trình" (không phân biệt hoa thường)
    public void timSachLapTrinh() {
        List<Book> result = new ArrayList<>();
        String keyword = "Lập trình".toLowerCase();
        
        for (Book book : books) {
            if (book.getTenSach().toLowerCase().contains(keyword)) {
                result.add(book);
            }
        }

        if (result.isEmpty()) {
            System.out.println("Không tìm thấy sách có tựa đề chứa 'Lập trình'!");
        } else {
            System.out.println("\n========== SÁCH CÓ TỰA ĐỀ CHỨA 'LẬP TRÌNH' ==========");
            for (Book book : result) {
                System.out.println(book);
            }
            System.out.println("====================================================\n");
        }
    }

    // 6. Lấy sách: Nhập vào 1 số K và giá sách P. Lấy tối đa K cuốn sách có giá <= P
    public void laySachTheoGia(int K, double P) {
        List<Book> result = new ArrayList<>();
        
        for (Book book : books) {
            if (book.getDonGia() <= P) {
                result.add(book);
            }
        }

        if (result.isEmpty()) {
            System.out.println("Không tìm thấy sách có giá <= " + P);
        } else {
            System.out.println("\n========== SÁCH CÓ GIÁ <= " + P + " (TỐI ĐA " + K + " CUỐN) ==========");
            int count = Math.min(K, result.size());
            for (int i = 0; i < count; i++) {
                System.out.println(result.get(i));
            }
            System.out.println("=================================================================\n");
        }
    }

    // 7. Tìm sách theo danh sách tác giả
    public void timSachTheoTacGia(List<String> danhSachTacGia) {
        List<Book> result = new ArrayList<>();
        
        for (Book book : books) {
            for (String tacGia : danhSachTacGia) {
                if (book.getTacGia().equalsIgnoreCase(tacGia)) {
                    result.add(book);
                    break;
                }
            }
        }

        if (result.isEmpty()) {
            System.out.println("Không tìm thấy sách của các tác giả này!");
        } else {
            System.out.println("\n========== SÁCH CỦA CÁC TÁC GIẢ ==========");
            for (Book book : result) {
                System.out.println(book);
            }
            System.out.println("========================================\n");
        }
    }

    // Phương thức hỗ trợ kiểm tra xem sách có tồn tại không
    public Book timSachTheoMa(int maSach) {
        for (Book book : books) {
            if (book.getMaSach() == maSach) {
                return book;
            }
        }
        return null;
    }

    // Getter
    public List<Book> getBooks() {
        return books;
    }
}
