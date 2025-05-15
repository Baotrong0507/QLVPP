package DTO;

public class SanPhamDTO {
    private int maSP;
    private String tenSP;
    private int soLuong;
    private double donGia;
    private String donViTienTe;
    private int maLoai;
    private String xuatXu;

    public SanPhamDTO() {
    }

    public SanPhamDTO(int maSP, String tenSP, int soLuong, double donGia, String donViTienTe, int maLoai, String xuatXu) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.donViTienTe = donViTienTe;
        this.maLoai = maLoai;
        this.xuatXu = xuatXu;
    }

    // Getters và Setters
    public int getMaSP() { return maSP; }
    public void setMaSP(int maSP) { this.maSP = maSP; }

    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }

    public String getDonViTienTe() { return donViTienTe; }
    public void setDonViTienTe(String donViTienTe) { this.donViTienTe = donViTienTe; }

    public int getMaLoai() { return maLoai; }
    public void setMaLoai(int maLoai) { this.maLoai = maLoai; }

    public String getXuatXu() { return xuatXu; }
    public void setXuatXu(String xuatXu) { this.xuatXu = xuatXu; }
}
