package DAO;

import DTO.SanPhamDTO;

import java.sql.*;
import java.util.ArrayList;

public class SanPhamDAO {
    private Connection conn;

    public SanPhamDAO() {
        try {
            // Kết nối DB, bạn thay thông tin kết nối phù hợp
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yourdb", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách sản phẩm
    public ArrayList<SanPhamDTO> getDanhSachSP() {
        ArrayList<SanPhamDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM SanPham";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                SanPhamDTO sp = new SanPhamDTO(
                    rs.getInt("MaSP"),
                    rs.getString("TenSP"),
                    rs.getInt("SoLuong"),
                    rs.getDouble("DonGia"),
                    rs.getString("DonViTienTe"),
                    rs.getInt("MaLoai"),
                    rs.getString("Xuatxu")
                );
                list.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm sản phẩm
    public boolean themSP(SanPhamDTO sp) {
        String sql = "INSERT INTO SanPham(MaSP, TenSP, SoLuong, DonGia, DonViTienTe, MaLoai, Xuatxu) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, sp.getMaSP());
            pst.setString(2, sp.getTenSP());
            pst.setInt(3, sp.getSoLuong());
            pst.setDouble(4, sp.getDonGia());
            pst.setString(5, sp.getDonViTienTe());
            pst.setInt(6, sp.getMaLoai());
            pst.setString(7, sp.getXuatXu());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Sửa sản phẩm
    public boolean suaSP(SanPhamDTO sp) {
        String sql = "UPDATE SanPham SET TenSP=?, SoLuong=?, DonGia=?, DonViTienTe=?, MaLoai=?, Xuatxu=? WHERE MaSP=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, sp.getTenSP());
            pst.setInt(2, sp.getSoLuong());
            pst.setDouble(3, sp.getDonGia());
            pst.setString(4, sp.getDonViTienTe());
            pst.setInt(5, sp.getMaLoai());
            pst.setString(6, sp.getXuatXu());
            pst.setInt(7, sp.getMaSP());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa sản phẩm
    public boolean xoaSP(int maSP) {
        String sql = "DELETE FROM SanPham WHERE MaSP=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, maSP);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm kiếm sản phẩm theo nhiều từ khóa (OR)
    public ArrayList<SanPhamDTO> timKiemSP(String keywords) {
        ArrayList<SanPhamDTO> list = new ArrayList<>();
        String[] keys = keywords.split(",");
        StringBuilder sql = new StringBuilder("SELECT * FROM SanPham WHERE ");
        for (int i = 0; i < keys.length; i++) {
            sql.append("TenSP LIKE ?");
            if (i != keys.length - 1) sql.append(" OR ");
        }
        try (PreparedStatement pst = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < keys.length; i++) {
                pst.setString(i + 1, "%" + keys[i].trim() + "%");
            }
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                SanPhamDTO sp = new SanPhamDTO(
                    rs.getInt("MaSP"),
                    rs.getString("TenSP"),
                    rs.getInt("SoLuong"),
                    rs.getDouble("DonGia"),
                    rs.getString("DonViTienTe"),
                    rs.getInt("MaLoai"),
                    rs.getString("Xuatxu")
                );
                list.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
