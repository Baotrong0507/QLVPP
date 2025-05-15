package DAO;

import java.sql.*;
import java.util.ArrayList;

import DAO.*;
import DTO.LoaiSanPhamDTO;

public class LoaiSanPhamDAO {
    private Connection conn;

    public LoaiSanPhamDAO() {
        try {
            // Đổi thông tin URL, username, password phù hợp với MySQL của bạn
            String url = "jdbc:mysql://localhost:3306/your_database_name";
            String username = "your_username";
            String password = "your_password";
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<LoaiSanPhamDTO> getAllLoai() {
        ArrayList<LoaiSanPhamDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM LoaiSanPham";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                LoaiSanPhamDTO loai = new LoaiSanPhamDTO(
                    rs.getString("MaLoai"),
                    rs.getString("TenLoai")
                );
                list.add(loai);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<LoaiSanPhamDTO> timKiemLoai(String keyword) {
        ArrayList<LoaiSanPhamDTO> list = new ArrayList<>();
        // Tách keyword theo dấu ',' để tìm với OR logic
        String[] keys = keyword.split(",");
        StringBuilder sql = new StringBuilder("SELECT * FROM LoaiSanPham WHERE ");
        for (int i = 0; i < keys.length; i++) {
            sql.append("TenLoai LIKE ?");
            if (i != keys.length - 1) {
                sql.append(" OR ");
            }
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < keys.length; i++) {
                pstmt.setString(i + 1, "%" + keys[i].trim() + "%");
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                LoaiSanPhamDTO loai = new LoaiSanPhamDTO(
                    rs.getString("MaLoai"),
                    rs.getString("TenLoai")
                );
                list.add(loai);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean themLoai(LoaiSanPhamDTO loai) {
        String sql = "INSERT INTO LoaiSanPham(MaLoai, TenLoai) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loai.getMaLoai());
            pstmt.setString(2, loai.getTenLoai());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaLoai(LoaiSanPhamDTO loai) {
        String sql = "UPDATE LoaiSanPham SET TenLoai=? WHERE MaLoai=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loai.getTenLoai());
            pstmt.setString(2, loai.getMaLoai());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaLoai(String maLoai) {
        String sql = "DELETE FROM LoaiSanPham WHERE MaLoai=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maLoai);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
