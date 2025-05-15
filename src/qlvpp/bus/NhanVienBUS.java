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

    public void themNhanVien(String maNV, String ho, String ten, String diaChi, String luongThang) throws Exception {
        // Kiểm tra và parse dữ liệu
        int ma;
        double luong;
        try {
            ma = Integer.parseInt(maNV);
            // Bỏ dấu phẩy để parse lương tháng
            luong = Double.parseDouble(luongThang.replace(",", ""));
        } catch (NumberFormatException e) {
            throw new Exception("Mã NV và Lương Tháng phải là số hợp lệ!");
        }

        // Kiểm tra dữ liệu trống
        if (ho.trim().isEmpty() || ten.trim().isEmpty() || diaChi.trim().isEmpty()) {
            throw new Exception("Họ, Tên và Địa Chỉ không được để trống!");
        }

        // Tạo đối tượng NhanVien
        NhanVien nv = new NhanVien(ma, ho, ten, diaChi, luong);
        if (!nhanVienDAO.addNhanVien(nv)) {
            throw new Exception("Thêm nhân viên thất bại!");
        }
    }

    public void suaNhanVien(String maNV, String ho, String ten, String diaChi, String luongThang) throws Exception {
        // Kiểm tra và parse dữ liệu
        int ma;
        double luong;
        try {
            ma = Integer.parseInt(maNV);
            // Bỏ dấu phẩy để parse lương tháng
            luong = Double.parseDouble(luongThang.replace(",", ""));
        } catch (NumberFormatException e) {
            throw new Exception("Mã NV và Lương Tháng phải là số hợp lệ!");
        }

        // Kiểm tra dữ liệu trống
        if (ho.trim().isEmpty() || ten.trim().isEmpty() || diaChi.trim().isEmpty()) {
            throw new Exception("Họ, Tên và Địa Chỉ không được để trống!");
        }

        // Tạo đối tượng NhanVien
        NhanVien nv = new NhanVien(ma, ho, ten, diaChi, luong);
        if (!nhanVienDAO.updateNhanVien(nv)) {
            throw new Exception("Sửa nhân viên thất bại!");
        }
    }

    public void xoaNhanVien(String maNV) throws Exception {
        // Kiểm tra và parse mã nhân viên
        int ma;
        try {
            ma = Integer.parseInt(maNV);
        } catch (NumberFormatException e) {
            throw new Exception("Mã NV phải là số hợp lệ!");
        }

        if (!nhanVienDAO.deleteNhanVien(ma)) {
            throw new Exception("Xóa nhân viên thất bại!");
        }
    }

    public List<NhanVien> timKiemNhanVien(String keyword) throws Exception {
        if (keyword.trim().isEmpty()) {
            throw new Exception("Từ khóa tìm kiếm không được để trống!");
        }
        List<NhanVien> result = nhanVienDAO.searchNhanVien(keyword);
        if (result.isEmpty()) {
            throw new Exception("Không tìm thấy nhân viên nào!");
        }
        return result;
    }
}