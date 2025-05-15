package qlvpp.dao;

import qlvpp.model.ChiTietHoaDon;
import qlvpp.connections.Myconnections;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonDAO {
    private Connection conn;

    public ChiTietHoaDonDAO() {
        conn = Myconnections.getConnection();
    }

    public List<ChiTietHoaDon> getChiTietByMaHD(int maHD) {
        List<ChiTietHoaDon> chiTietList = new ArrayList<>();
        String sql = "SELECT * FROM HoaDonChiTiet WHERE MaHD = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietHoaDon cthd = new ChiTietHoaDon(
                        rs.getInt("MaHD"),
                        rs.getInt("MaSP"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("DonGia"),
                        rs.getDouble("ThanhTien")
                );
                chiTietList.add(cthd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chiTietList;
    }

    public boolean addChiTietHoaDon(ChiTietHoaDon cthd) {
        String sql = "INSERT INTO HoaDonChiTiet (MaHD, MaSP, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cthd.getMaHD());
            ps.setInt(2, cthd.getMaSP());
            ps.setInt(3, cthd.getSoLuong());
            ps.setDouble(4, cthd.getDonGia());
            ps.setDouble(5, cthd.getThanhTien());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSoLuongSanPham(int maSP, int soLuong) {
        String sql = "UPDATE SanPham SET SoLuong = SoLuong - ? WHERE MaSP = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soLuong);
            ps.setInt(2, maSP);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}