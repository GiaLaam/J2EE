public class Book {
    private int maSach;
    private String tenSach;
    private String tacGia;
    private double donGia;

    // Constructor
    public Book(int maSach, String tenSach, String tacGia, double donGia) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.donGia = donGia;
    }

    // Getters
    public int getMaSach() {
        return maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public double getDonGia() {
        return donGia;
    }

    // Setters
    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    // toString method
    @Override
    public String toString() {
        return String.format("Mã: %d | Tên: %s | Tác giả: %s | Giá: %.2f", 
                             maSach, tenSach, tacGia, donGia);
    }
}
