package qlvpp.bus;

import qlvpp.dao.KhachHangDAO;
import qlvpp.model.KhachHang;
import java.util.List;

public class KhachHangBUS {
    private KhachHangDAO khachHangDAO;

    public KhachHangBUS() {
        khachHangDAO = new KhachHangDAO();
    }

    public List<KhachHang> getAllKhachHang() {
        return khachHangDAO.getAllKhachHang();
    }

    public boolean addKhachHang(KhachHang kh) {
        return khachHangDAO.addKhachHang(kh);
    }

    public boolean updateKhachHang(KhachHang kh) {
        return khachHangDAO.updateKhachHang(kh);
    }

    public boolean deleteKhachHang(int maKH) {
        return khachHangDAO.deleteKhachHang(maKH);
    }

    public List<KhachHang> searchKhachHang(String search) {
        return khachHangDAO.searchKhachHang(search);
    }
}