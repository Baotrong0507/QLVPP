package qlvpp.bus;

import qlvpp.dao.KhuyenMaiDAO;
import qlvpp.model.KhuyenMai;
import qlvpp.model.KhuyenMaiSanPham;
import qlvpp.model.KhuyenMaiHoaDon;
import qlvpp.model.SanPham;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class KhuyenMaiBUS {
    private KhuyenMaiDAO khuyenMaiDAO;

    public KhuyenMaiBUS() {
        khuyenMaiDAO = new KhuyenMaiDAO();
    }

    public List<KhuyenMai> getAllKhuyenMai() {
        return khuyenMaiDAO.getAllKhuyenMai();
    }

    public KhuyenMai getKhuyenMaiById(int maKM) {
        return khuyenMaiDAO.getKhuyenMaiById(maKM);
    }

    public boolean addKhuyenMai(KhuyenMai km, List<KhuyenMaiSanPham> kmspList, List<KhuyenMaiHoaDon> kmhdList) {
        return khuyenMaiDAO.addKhuyenMai(km, kmspList, kmhdList);
    }

    public boolean updateKhuyenMai(KhuyenMai km, List<KhuyenMaiSanPham> kmspList, List<KhuyenMaiHoaDon> kmhdList) {
        return khuyenMaiDAO.updateKhuyenMai(km, kmspList, kmhdList);
    }

    public boolean deleteKhuyenMai(int maKM) {
        return khuyenMaiDAO.deleteKhuyenMai(maKM);
    }

    public List<KhuyenMai> searchKhuyenMai(String maKM, String ngayBatDau, String ngayKetThuc) {
    return khuyenMaiDAO.searchKhuyenMai(maKM, ngayBatDau, ngayKetThuc);
}

    public List<SanPham> getAllSanPham() {
        return khuyenMaiDAO.getAllSanPham();
    }

    public int getNextMaKM() {
    return khuyenMaiDAO.getNextMaKM();
}

    public SanPham getSanPhamById(int maSP) {
        return khuyenMaiDAO.getSanPhamById(maSP);
    }

    public List<KhuyenMaiSanPham> getKhuyenMaiSanPham(int maKM) {
        return khuyenMaiDAO.getKhuyenMaiSanPham(maKM);
    }

    public List<KhuyenMaiHoaDon> getKhuyenMaiHoaDon(int maKM) {
        return khuyenMaiDAO.getKhuyenMaiHoaDon(maKM);
    }

    public String tinhTrangThai(String ngayBatDau, String ngayKetThuc) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start = sdf.parse(ngayBatDau);
            Date end = sdf.parse(ngayKetThuc);
            Date now = new Date();

            if (now.before(start)) {
                return "Sắp diễn ra";
            } else if (now.after(end)) {
                return "Đã kết thúc";
            } else {
                return "Đang diễn ra";
            }
        } catch (Exception e) {
            return "Không xác định";
        }
    }
}