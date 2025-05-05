package qlvpp.model;

public class NhanVien {
    private int maNV;
    private String ho;
    private String ten;
    private String diaChi;
    private double luongThang;

    public NhanVien() {}

    public NhanVien(int maNV, String ho, String ten, String diaChi, double luongThang) {
        this.maNV = maNV;
        this.ho = ho;
        this.ten = ten;
        this.diaChi = diaChi;
        this.luongThang = luongThang;
    }

    // Getters and Setters
    public int getMaNV() { return maNV; }
    public void setMaNV(int maNV) { this.maNV = maNV; }
    public String getHo() { return ho; }
    public void setHo(String ho) { this.ho = ho; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public double getLuongThang() { return luongThang; }
    public void setLuongThang(double luongThang) { this.luongThang = luongThang; }
}