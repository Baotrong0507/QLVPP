package qlvpp.bus;

import qlvpp.dao.NhanVienDAO;
import qlvpp.model.NhanVien;
import java.util.List;

public class NhanVienBUS {
    private NhanVienDAO nhanVienDAO;

    public NhanVienBUS() {
        nhanVienDAO = new NhanVienDAO();
    }

    public List<NhanVien> getAllNhanVien() {
        return nhanVienDAO.getAllNhanVien();
    }

    public boolean addNhanVien(NhanVien nv) {
        return nhanVienDAO.addNhanVien(nv);
    }

    public boolean updateNhanVien(NhanVien nv) {
        return nhanVienDAO.updateNhanVien(nv);
    }

    public boolean deleteNhanVien(int maNV) {
        return nhanVienDAO.deleteNhanVien(maNV);
    }

    public List<NhanVien> searchNhanVien(String search) {
        return nhanVienDAO.searchNhanVien(search);
    }
}