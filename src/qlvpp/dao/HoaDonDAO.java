package qlvpp.dao;

import qlvpp.model.HoaDon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import qlvpp.connections.*;

public class HoaDonDAO {
    private Connection conn;

    public HoaDonDAO() {
    	conn = Myconnections.getConnection();
    }
    	

    public List<HoaDon> getAllHoaDon() {
        List<HoaDon> hoaDonList = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                    rs.getInt("MaHD"),
                    rs.getInt("MaKH"),
                    rs.getInt("MaNV"),
                    rs.getString("NgayLap"),
                    rs.getDouble("TongTien")
                );
                hoaDonList.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoaDonList;
    }

    public boolean addHoaDon(HoaDon hd) {
        String sql = "INSERT INTO HoaDon (MaHD, MaKH, MaNV, NgayLap, TongTien) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hd.getMaHD());
            ps.setInt(2, hd.getMaKH());
            ps.setInt(3, hd.getMaNV());
            ps.setString(4, hd.getNgayLap());
            ps.setDouble(5, hd.getTongTien());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateHoaDon(HoaDon hd) {
        String sql = "UPDATE HoaDon SET MaKH = ?, MaNV = ?, NgayLap = ?, TongTien = ? WHERE MaHD = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hd.getMaKH());
            ps.setInt(2, hd.getMaNV());
            ps.setString(3, hd.getNgayLap());
            ps.setDouble(4, hd.getTongTien());
            ps.setInt(5, hd.getMaHD());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteHoaDon(int maHD) {
        String sql = "DELETE FROM HoaDon WHERE MaHD = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHD);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<HoaDon> searchHoaDon(String search) {
        List<HoaDon> hoaDonList = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE MaHD LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + search + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon(
                    rs.getInt("MaHD"),
                    rs.getInt("MaKH"),
                    rs.getInt("MaNV"),
                    rs.getString("NgayLap"),
                    rs.getDouble("TongTien")
                );
                hoaDonList.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoaDonList;
    }
}