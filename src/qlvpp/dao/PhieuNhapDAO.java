package qlvpp.dao;

import qlvpp.connections.Myconnections;
import qlvpp.model.PhieuNhap;
import qlvpp.model.ChiTietPhieuNhap;
import java.sql.*;
import java.util.*;

public class PhieuNhapDAO {
    private ChiTietPhieuNhapDAO chiTietDAO = new ChiTietPhieuNhapDAO();

    public List<PhieuNhap> getAll() {
        List<PhieuNhap> list = new ArrayList<>();
        try (Connection conn = Myconnections.getConnection()) {
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
            throw new RuntimeException("Lỗi khi lấy danh sách PhieuNhap: " + e.getMessage(), e);
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
            throw new RuntimeException("Lỗi khi thêm PhieuNhap: " + e.getMessage(), e);
        }
    }

    public void savePhieuNhap(PhieuNhap pn, ArrayList<ChiTietPhieuNhap> chiTietList) {
        Connection conn = null;
        try {
            conn = Myconnections.getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // Lưu PhieuNhap
            insert(pn);

            // Lưu ChiTietPhieuNhap
            for (ChiTietPhieuNhap ct : chiTietList) {
                chiTietDAO.insert(ct);
            }

            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback nếu có lỗi
                } catch (SQLException ex) {
                    throw new RuntimeException("Lỗi khi rollback: " + ex.getMessage(), ex);
                }
            }
            throw new RuntimeException("Lỗi khi lưu PhieuNhap và ChiTietPhieuNhap: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException("Lỗi khi đóng kết nối: " + e.getMessage(), e);
                }
            }
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
            throw new RuntimeException("Lỗi khi cập nhật PhieuNhap: " + e.getMessage(), e);
        }
    }

    public void delete(int maPN) {
        Connection conn = null;
        try {
            conn = Myconnections.getConnection();
            conn.setAutoCommit(false);

            // Xóa ChiTietPhieuNhap trước
            chiTietDAO.deleteByMaPN(maPN);

            // Xóa PhieuNhap
            String sql = "DELETE FROM PhieuNhap WHERE MaPN = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maPN);
            stmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException("Lỗi khi rollback: " + ex.getMessage(), ex);
                }
            }
            throw new RuntimeException("Lỗi khi xóa PhieuNhap: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException("Lỗi khi đóng kết nối: " + e.getMessage(), e);
                }
            }
        }
    }

    public double tinhTongChiTieu() {
        double tongChiTieu = 0;
        try (Connection conn = Myconnections.getConnection()) {
            String sql = "SELECT SUM(TongTien) AS tongChiTieu FROM PhieuNhap";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tongChiTieu = rs.getDouble("tongChiTieu");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tính tổng chi tiêu: " + e.getMessage(), e);
        }
        return tongChiTieu;
    }

    // Ghi chú: Thêm phương thức tính doanh thu sau khi có HoaDonDAO
    /*
    public double tinhTongDoanhThu() {
        double tongDoanhThu = 0;
        try (Connection conn = Myconnections.getConnection()) {
            String sql = "SELECT SUM(TongTien) AS tongDoanhThu FROM HoaDon";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tongDoanhThu = rs.getDouble("tongDoanhThu");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tính tổng doanh thu: " + e.getMessage(), e);
        }
        return tongDoanhThu;
    }
    */
}