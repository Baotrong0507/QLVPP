package qlvpp.model;

public class SanPham {
    private int maSP;
    private String tenSP;

    public SanPham(int maSP, String tenSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }
}