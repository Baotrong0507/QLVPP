package qlvpp.model;
import java.sql.Date;

public class PhieuNhap { 
    private int maPN; 
    private int maNV;
    private int maNCC;
    private Date ngayNhap;
    private double tongTien;

public PhieuNhap() {
} 
public PhieuNhap(int maPN, int maNV, int maNCC, Date ngayNhap, double tongTien) { 
    this.maPN = maPN;
    this.maNV = maNV;
    this.maNCC = maNCC;
    this.ngayNhap = ngayNhap;
    this.tongTien = tongTien;
}
public int getMaPN() {
    return maPN; 
}
public void setMaPN(int maPN) { 
    this.maPN = maPN; 
}
public int getMaNV() {
    return maNV; 
}
public void setMaNV(int maNV) {
    this.maNV = maNV; 
}
public int getMaNCC() {
    return maNCC; 
}
public void setMaNCC(int maNCC) {
    this.maNCC = maNCC; 
}
public Date getNgayNhap() {
    return ngayNhap; 
}
public void setNgayNhap(Date ngayNhap){ 
    this.ngayNhap = ngayNhap; 
}
public double getTongTien() {
    return tongTien; 
}
public void setTongTien(double tongTien) {
    this.tongTien = tongTien; 
} 
}