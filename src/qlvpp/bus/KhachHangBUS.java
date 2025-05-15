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

    public void themKhachHang(String maKH, String ho, String ten, String diaChi, String dienThoai) throws Exception {
        // Kiểm tra và parse dữ liệu
        int ma;
        try {
            ma = Integer.parseInt(maKH);
        } catch (NumberFormatException e) {
            throw new Exception("Mã KH phải là số hợp lệ!");
        }

        // Kiểm tra dữ liệu trống
        if (ho.trim().isEmpty() || ten.trim().isEmpty() || diaChi.trim().isEmpty() || dienThoai.trim().isEmpty()) {
            throw new Exception("Các trường không được để trống!");
        }

        // Tạo đối tượng KhachHang
        KhachHang kh = new KhachHang(ma, ho, ten, diaChi, dienThoai);
        if (!khachHangDAO.addKhachHang(kh)) {
            throw new Exception("Thêm khách hàng thất bại!");
        }
    }

    public void suaKhachHang(String maKH, String ho, String ten, String diaChi, String dienThoai) throws Exception {
        // Kiểm tra và parse dữ liệu
        int ma;
        try {
            ma = Integer.parseInt(maKH);
        } catch (NumberFormatException e) {
            throw new Exception("Mã KH phải là số hợp lệ!");
        }

        // Kiểm tra dữ liệu trống
        if (ho.trim().isEmpty() || ten.trim().isEmpty() || diaChi.trim().isEmpty() || dienThoai.trim().isEmpty()) {
            throw new Exception("Các trường không được để trống!");
        }

        // Tạo đối tượng KhachHang
        KhachHang kh = new KhachHang(ma, ho, ten, diaChi, dienThoai);
        if (!khachHangDAO.updateKhachHang(kh)) {
            throw new Exception("Sửa khách hàng thất bại!");
        }
    }

    public void xoaKhachHang(String maKH) throws Exception {
        // Kiểm tra và parse mã khách hàng
        int ma;
        try {
            ma = Integer.parseInt(maKH);
        } catch (NumberFormatException e) {
            throw new Exception("Mã KH phải là số hợp lệ!");
        }

        if (!khachHangDAO.deleteKhachHang(ma)) {
            throw new Exception("Xóa khách hàng thất bại!");
        }
    }

    public List<KhachHang> timKiemKhachHang(String keyword) throws Exception {
        if (keyword.trim().isEmpty()) {
            throw new Exception("Từ khóa tìm kiếm không được để trống!");
        }
        List<KhachHang> result = khachHangDAO.searchKhachHang(keyword);
        if (result.isEmpty()) {
            throw new Exception("Không tìm thấy khách hàng nào!");
        }
        return result;
    }
}