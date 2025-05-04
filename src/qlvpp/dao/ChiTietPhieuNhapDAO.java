package qlvpp.dao;

import qlvpp.model.ChiTietPhieuNhap;
import qlvpp.connections.Myconnections;
import java.sql.*;
import java.util.*;

public class ChiTietPhieuNhapDAO {
    public List<ChiTietPhieuNhap> getByMaPN(int maPN) {
        List<ChiTietPhieuNhap> list = new ArrayList<>();
        try (Connection conn = Myconnections.getConnection()) {
            String sql = "SELECT * FROM ChiTietPhieuNhap WHERE MaPN = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maPN);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ChiTietPhieuNhap ct = new ChiTietPhieuNhap(
                    rs.getInt("MaPN"),
                    rs.getInt("MaSP"),
                    rs.getInt("SoLuong"),
                    rs.getDouble("GiaNhap"),
                    rs.getDouble("ThanhTien")
                );
                list.add(ct);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy ChiTietPhieuNhap: " + e.getMessage(), e);
        }
        return list;
    }

    public void insert(ChiTietPhieuNhap ct) {
        try (Connection conn = Myconnections.getConnection()) {
            String sql = "INSERT INTO ChiTietPhieuNhap (MaPN, MaSP, SoLuong, GiaNhap, ThanhTien) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ct.getMaPN());
            stmt.setInt(2, ct.getMaSP());
            stmt.setInt(3, ct.getSoLuong());
            stmt.setDouble(4, ct.getGiaNhap());
            stmt.setDouble(5, ct.getThanhTien());
            stmt.executeUpdate();
        } catch (SQLException e) {
            //throw new RuntimeException("Lỗi khi thêm ChiTietPhieuNhap: " #e.getMessage(), e);
        }
    }

    public void update(ChiTietPhieuNhap ct) {
        try (Connection conn = Myconnections.getConnection()) {
            String sql = "UPDATE ChiTietPhieuNhap SET SoLuong = ?, GiaNhap = ?, ThanhTien = ? WHERE MaPN = ? AND MaSP = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ct.getSoLuong());
            stmt.setDouble(2, ct.getGiaNhap());
            stmt.setDouble(3, ct.getThanhTien());
            stmt.setInt(4, ct.getMaPN());
            stmt.setInt(5, ct.getMaSP());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật ChiTietPhieuNhap: " + e.getMessage(), e);
        }
    }

    public void delete(int maPN, int maSP) {
        try (Connection conn = Myconnections.getConnection()) {
            String sql = "DELETE FROM ChiTietPhieuNhap WHERE MaPN = ? AND MaSP = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maPN);
            stmt.setInt(2, maSP);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa ChiTietPhieuNhap: " + e.getMessage(), e);
        }
    }

    public void deleteByMaPN(int maPN) {
        try (Connection conn = Myconnections.getConnection()) {
            String sql = "DELETE FROM ChiTietPhieuNhap WHERE MaPN = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maPN);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa ChiTietPhieuNhap theo MaPN: " + e.getMessage(), e);
        }
    }
}