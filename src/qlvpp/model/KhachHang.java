package qlvpp.model;

public class KhachHang {
    private int maKH;
    private String ho;
    private String ten;
    private String diaChi;
    private String dienThoai;

    public KhachHang() {}

    public KhachHang(int maKH, String ho, String ten, String diaChi, String dienThoai) {
        this.maKH = maKH;
        this.ho = ho;
        this.ten = ten;
        this.diaChi = diaChi;
        this.dienThoai = dienThoai;
    }

    // Getters and Setters
    public int getMaKH() { return maKH; }
    public void setMaKH(int maKH) { this.maKH = maKH; }
    public String getHo() { return ho; }
    public void setHo(String ho) { this.ho = ho; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }
}