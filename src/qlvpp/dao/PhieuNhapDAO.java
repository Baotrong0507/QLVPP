package qlvpp.dao;
import qlvpp.connections.Myconnections;
import qlvpp.model.PhieuNhap;
import java.sql.*;
import java.util.*;

public class PhieuNhapDAO {
    public List<PhieuNhap> getAll() {
        List<PhieuNhap> list = new ArrayList<>();
        try (Connection conn = Myconnections.getConnection()){       
            String sql = "SELECT * FROM PhieuNhap";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new PhieuNhap(
                    rs.getInt("MaPN"),
                    rs.getInt("MaNV"),
                    rs.getInt("MaNCC"),
                    rs.getDate("NgayNhap"),
                    rs.getDouble("TongTien")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(PhieuNhap pn) {
        try (Connection conn = Myconnections.getConnection()) {
            String sql = "INSERT INTO PhieuNhap (MaPN, MaNV, MaNCC, NgayNhap, TongTien) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pn.getMaPN());
            stmt.setInt(2, pn.getMaNV());
            stmt.setInt(3, pn.getMaNCC());
            stmt.setDate(4, pn.getNgayNhap());
            stmt.setDouble(5, pn.getTongTien());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(PhieuNhap pn) {
        try (Connection conn = Myconnections.getConnection()) {
            String sql = "UPDATE PhieuNhap SET MaNV = ?, MaNCC = ?, NgayNhap = ?, TongTien = ? WHERE MaPN = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pn.getMaNV());
            stmt.setInt(2, pn.getMaNCC());
            stmt.setDate(3, pn.getNgayNhap());
            stmt.setDouble(4, pn.getTongTien());
            stmt.setInt(5, pn.getMaPN());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int maPN) {
        
        try (Connection conn = Myconnections.getConnection()) {
            String sql = "DELETE FROM PhieuNhap WHERE MaPN = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maPN);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
