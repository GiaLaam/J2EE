import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        BookManager manager = new BookManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        // Thêm một số sách mẫu ban đầu
        manager.themSach(new Book(1, "Lập trình Java cơ bản", "Nguyễn Văn A", 150000));
        manager.themSach(new Book(2, "Lập trình C++", "Trần Thị B", 200000));
        manager.themSach(new Book(3, "Web Development", "Lê Văn C", 180000));
        manager.themSach(new Book(4, "Lập trình Python nâng cao", "Phạm Văn D", 220000));
        manager.themSach(new Book(5, "Database Management", "Hoàng Thị E", 250000));

        do {
            displayMenu();
            System.out.print("Nhập lựa chọn (1-7): ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    themSachMenu(manager, scanner);
                    break;
                case 2:
                    xoaSachMenu(manager, scanner);
                    break;
                case 3:
                    thayDoiSachMenu(manager, scanner);
                    break;
                case 4:
                    manager.xuatTatCaSach();
                    break;
                case 5:
                    manager.timSachLapTrinh();
                    break;
                case 6:
                    laySachTheoGiaMenu(manager, scanner);
                    break;
                case 7:
                    timSachTheoTacGiaMenu(manager, scanner);
                    break;
                case 0:
                    System.out.println("Cảm ơn bạn đã sử dụng ứng dụng!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (choice != 0);

        scanner.close();
    }

    // Hiển thị menu chính
    private static void displayMenu() {
        System.out.println("\n========== MENU QUẢN LÝ SÁCH ==========");
        System.out.println("1. Thêm 1 cuốn sách");
        System.out.println("2. Xóa 1 cuốn sách");
        System.out.println("3. Thay đổi cuốn sách");
        System.out.println("4. Xuất thông tin tất cả các cuốn sách");
        System.out.println("5. Tìm sách có tựa đề chứa 'Lập trình'");
        System.out.println("6. Lấy sách theo giá (K cuốn, giá <= P)");
        System.out.println("7. Tìm sách theo danh sách tác giả");
        System.out.println("0. Thoát");
        System.out.println("======================================");
    }

    // Menu thêm sách
    private static void themSachMenu(BookManager manager, Scanner scanner) {
        System.out.println("\n--- Thêm Sách ---");
        System.out.print("Nhập mã sách: ");
        int maSach = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nhập tên sách: ");
        String tenSach = scanner.nextLine();

        System.out.print("Nhập tác giả: ");
        String tacGia = scanner.nextLine();

        System.out.print("Nhập đơn giá: ");
        double donGia = scanner.nextDouble();
        scanner.nextLine();

        Book book = new Book(maSach, tenSach, tacGia, donGia);
        manager.themSach(book);
    }

    // Menu xóa sách
    private static void xoaSachMenu(BookManager manager, Scanner scanner) {
        System.out.println("\n--- Xóa Sách ---");
        System.out.print("Nhập mã sách cần xóa: ");
        int maSach = scanner.nextInt();
        scanner.nextLine();

        manager.xoaSach(maSach);
    }

    // Menu thay đổi sách
    private static void thayDoiSachMenu(BookManager manager, Scanner scanner) {
        System.out.println("\n--- Thay Đổi Sách ---");
        System.out.print("Nhập mã sách cần thay đổi: ");
        int maSach = scanner.nextInt();
        scanner.nextLine();

        Book book = manager.timSachTheoMa(maSach);
        if (book == null) {
            System.out.println("Lỗi: Không tìm thấy sách với mã này!");
            return;
        }

        System.out.print("Nhập tên sách mới: ");
        String tenSach = scanner.nextLine();

        System.out.print("Nhập tác giả mới: ");
        String tacGia = scanner.nextLine();

        System.out.print("Nhập đơn giá mới: ");
        double donGia = scanner.nextDouble();
        scanner.nextLine();

        manager.thayDoiSach(maSach, tenSach, tacGia, donGia);
    }

    // Menu lấy sách theo giá
    private static void laySachTheoGiaMenu(BookManager manager, Scanner scanner) {
        System.out.println("\n--- Lấy Sách Theo Giá ---");
        System.out.print("Nhập K (số lượng tối đa): ");
        int K = scanner.nextInt();

        System.out.print("Nhập P (giá tối đa): ");
        double P = scanner.nextDouble();
        scanner.nextLine();

        manager.laySachTheoGia(K, P);
    }

    // Menu tìm sách theo danh sách tác giả
    private static void timSachTheoTacGiaMenu(BookManager manager, Scanner scanner) {
        System.out.println("\n--- Tìm Sách Theo Danh Sách Tác Giả ---");
        System.out.print("Nhập số lượng tác giả: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        List<String> danhSachTacGia = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            System.out.print("Nhập tác giả thứ " + (i + 1) + ": ");
            String tacGia = scanner.nextLine();
            danhSachTacGia.add(tacGia);
        }

        manager.timSachTheoTacGia(danhSachTacGia);
    }
}
