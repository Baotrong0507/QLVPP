package qlvpp.model;

public class KhuyenMaiHoaDon {
    private int maKM;
    private double soTienHoaDon;
    private double phanTramGiam;

    public KhuyenMaiHoaDon(int maKM, double soTienHoaDon, double phanTramGiam) {
        this.maKM = maKM;
        this.soTienHoaDon = soTienHoaDon;
        this.phanTramGiam = phanTramGiam;
    }

    public int getMaKM() {
        return maKM;
    }

    public void setMaKM(int maKM) {
        this.maKM = maKM;
    }

    public double getSoTienHoaDon() {
        return soTienHoaDon;
    }

    public void setSoTienHoaDon(double soTienHoaDon) {
        this.soTienHoaDon = soTienHoaDon;
    }

    public double getPhanTramGiam() {
        return phanTramGiam;
    }

    public void setPhanTramGiam(double phanTramGiam) {
        this.phanTramGiam = phanTramGiam;
    }
}