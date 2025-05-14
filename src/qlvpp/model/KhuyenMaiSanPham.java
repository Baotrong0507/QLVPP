package qlvpp.model;

public class KhuyenMaiSanPham {
    private int maKM;
    private int maSP;
    private double phanTramGiam;

    public KhuyenMaiSanPham(int maKM, int maSP, double phanTramGiam) {
        this.maKM = maKM;
        this.maSP = maSP;
        this.phanTramGiam = phanTramGiam;
    }

    public int getMaKM() {
        return maKM;
    }

    public void setMaKM(int maKM) {
        this.maKM = maKM;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public double getPhanTramGiam() {
        return phanTramGiam;
    }

    public void setPhanTramGiam(double phanTramGiam) {
        this.phanTramGiam = phanTramGiam;
    }
}