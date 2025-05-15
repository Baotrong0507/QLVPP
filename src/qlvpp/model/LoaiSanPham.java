
package qlvpp.model;

public class LoaiSanPham {
    private int maLoai; // Đổi từ String sang int để khớp với INT trong SQL
    private String tenLoai;
    private String chatLieu; // Thêm thuộc tính chatLieu để khớp với cột Chatlieu

    public LoaiSanPham() {}

    public LoaiSanPham(int maLoai, String tenLoai, String chatLieu) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.chatLieu = chatLieu;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(String chatLieu) {
        this.chatLieu = chatLieu;
    }
}
