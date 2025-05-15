package qlvpp.dao;

import qlvpp.connections.Myconnections;
import qlvpp.model.HoaDon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    public List<HoaDon> getAllHoaDon() {
        List<HoaDon> ds = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon";
        try (Connection con = Myconnections.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                        rs.getInt("MaHD"), // Đổi sang int
                        rs.getInt("MaKH"),
                        rs.getInt("MaNV"),
                        rs.getDate("NgayLap"),
                        rs.getDouble("TongTien")
                );
                ds.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public boolean addHoaDon(HoaDon hd) {
        String sql = "INSERT INTO HoaDon (MaHD, MaKH, MaNV, NgayLap, TongTien) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Myconnections.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, hd.getMaHD()); // Đổi sang int
            ps.setInt(2, hd.getMaKH());
            ps.setInt(3, hd.getMaNV());
            ps.setDate(4, hd.getNgayLap());
            ps.setDouble(5, hd.getTongTien());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateHoaDon(HoaDon hd) {
        String sql = "UPDATE HoaDon SET MaKH=?, MaNV=?, NgayLap=?, TongTien=? WHERE MaHD=?";
        try (Connection con = Myconnections.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, hd.getMaKH());
            ps.setInt(2, hd.getMaNV());
            ps.setDate(3, hd.getNgayLap());
            ps.setDouble(4, hd.getTongTien());
            ps.setInt(5, hd.getMaHD()); // Đổi sang int
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteHoaDon(int maHD) { // Đổi sang int
        String sql = "DELETE FROM HoaDon WHERE MaHD=?";
        try (Connection con = Myconnections.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, maHD);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<HoaDon> searchHoaDon(String search) {
        List<HoaDon> result = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE CAST(MaHD AS CHAR) LIKE ? OR CAST(MaKH AS CHAR) LIKE ? OR CAST(MaNV AS CHAR) LIKE ?";
        try (Connection con = Myconnections.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String likePattern = "%" + search + "%";
            ps.setString(1, likePattern);
            ps.setString(2, likePattern);
            ps.setString(3, likePattern);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                        rs.getInt("MaHD"), // Đổi sang int
                        rs.getInt("MaKH"),
                        rs.getInt("MaNV"),
                        rs.getDate("NgayLap"),
                        rs.getDouble("TongTien")
                );
                result.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getMaxMaHD() { // Đổi kiểu trả về thành int
        String sql = "SELECT MAX(MaHD) as maxMaHD FROM HoaDon";
        try (Connection con = Myconnections.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("maxMaHD");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu không có dữ liệu
    }

    public HoaDon getHoaDonByMaHD(int maHD) { // Đổi sang int
        String sql = "SELECT * FROM HoaDon WHERE MaHD = ?";
        try (Connection con = Myconnections.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, maHD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new HoaDon(
                        rs.getInt("MaHD"), // Đổi sang int
                        rs.getInt("MaKH"),
                        rs.getInt("MaNV"),
                        rs.getDate("NgayLap"),
                        rs.getDouble("TongTien")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}