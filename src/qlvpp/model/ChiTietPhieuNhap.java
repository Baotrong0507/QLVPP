package qlvpp.model;

public class ChiTietPhieuNhap { 
    private int maPN; 
    private int maSP; 
    private int soLuong; 
    private double donGia; 
    private double thanhTien;

public ChiTietPhieuNhap() {} 
public ChiTietPhieuNhap(int maPN, int maSP, int soLuong, double donGia, double thanhTien) { 
    this.maPN = maPN; 
    this.maSP = maSP; 
    this.soLuong = soLuong; 
    this.donGia = donGia; 
    this.thanhTien = thanhTien; 
} 
public int getMaPN() { 
    return maPN; 
} 
public void setMaPN(int maPN) { 
    this.maPN = maPN; 
} public int getMaSP() { 
    return maSP; 
} public void setMaSP(int maSP) { 
    this.maSP = maSP; 
} 
public int getSoLuong() { 
    return soLuong; 
} 
public void setSoLuong(int soLuong) { 
    this.soLuong = soLuong; 
} 
public double getDonGia() { 
    return donGia; 
}
public void setDonGia(double donGia) {
    this.donGia = donGia; 
}
public double getThanhTien() {
    return thanhTien; 
}
public void setThanhTien(double thanhTien) { 
    this.thanhTien = thanhTien; 
} 
}