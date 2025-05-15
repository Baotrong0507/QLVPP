package qlvpp.model;

import java.sql.Date;

public class HoaDon {
    private int maHD;
    private int maKH;
    private int maNV;
    private Date ngayLap; // Đổi từ LocalDate sang java.sql.Date
    private double tongTien;

    public HoaDon() {}

    public HoaDon(int maHD, int maKH, int maNV, Date ngayLap, double tongTien) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
    }

    // Getters and Setters
    public int getMaHD() { return maHD; }
    public void setMaHD(int maHD) { this.maHD = maHD; }
    public int getMaKH() { return maKH; }
    public void setMaKH(int maKH) { this.maKH = maKH; }
    public int getMaNV() { return maNV; }
    public void setMaNV(int maNV) { this.maNV = maNV; }
    public Date getNgayLap() { return ngayLap; } // Đổi kiểu trả về thành java.sql.Date
    public void setNgayLap(Date ngayLap) { this.ngayLap = ngayLap; } // Đổi kiểu tham số thành java.sql.Date
    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
}