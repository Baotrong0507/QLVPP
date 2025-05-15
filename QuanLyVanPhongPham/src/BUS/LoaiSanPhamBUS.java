package BUS;

import java.util.ArrayList;

import DAO.LoaiSanPhamDAO;
import DTO.*;

public class LoaiSanPhamBUS {
    private LoaiSanPhamDAO loaiDAO = new LoaiSanPhamDAO();

    public ArrayList<LoaiSanPhamDTO> getDanhSachLoai() {
        return loaiDAO.getAllLoai();
    }

    public ArrayList<LoaiSanPhamDTO> timKiemLoai(String keyword) {
        return loaiDAO.timKiemLoai(keyword);
    }

    public boolean themLoai(LoaiSanPhamDTO loai) {
        return loaiDAO.themLoai(loai);
    }

    public boolean suaLoai(LoaiSanPhamDTO loai) {
        return loaiDAO.suaLoai(loai);
    }

    public boolean xoaLoai(String maLoai) {
        return loaiDAO.xoaLoai(maLoai);
    }
}
