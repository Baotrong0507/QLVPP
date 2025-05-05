package qlvpp.dao;

import qlvpp.model.NhanVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    private Connection conn;

    public NhanVienDAO() {
        // Initialize database connection
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/QuanLyVanPhongPham", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> nhanVienList = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                    rs.getInt("MaNV"),
                    rs.getString("Ho"),
                    rs.getString("Ten"),
                    rs.getString("DiaChi"),
                    rs.getDouble("LuongThang")
                );
                nhanVienList.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nhanVienList;
    }

    public boolean addNhanVien(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (MaNV, Ho, Ten, DiaChi, LuongThang) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nv.getMaNV());
            ps.setString(2, nv.getHo());
            ps.setString(3, nv.getTen());
            ps.setString(4, nv.getDiaChi());
            ps.setDouble(5, nv.getLuongThang());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateNhanVien(NhanVien nv) {
        String sql = "UPDATE NhanVien SET Ho = ?, Ten = ?, DiaChi = ?, LuongThang = ? WHERE MaNV = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nv.getHo());
            ps.setString(2, nv.getTen());
            ps.setString(3, nv.getDiaChi());
            ps.setDouble(4, nv.getLuongThang());
            ps.setInt(5, nv.getMaNV());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteNhanVien(int maNV) {
        String sql = "DELETE FROM NhanVien WHERE MaNV = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNV);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<NhanVien> searchNhanVien(String search) {
        List<NhanVien> nhanVienList = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE Ten LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + search + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                    rs.getInt("MaNV"),
                    rs.getString("Ho"),
                    rs.getString("Ten"),
                    rs.getString("DiaChi"),
                    rs.getDouble("LuongThang")
                );
                nhanVienList.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nhanVienList;
    }
}