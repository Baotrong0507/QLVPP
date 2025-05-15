package DAO;

import DTO.HoaDonChiTietDTO;

import java.sql.*;
import java.util.ArrayList;

public class HoaDonChiTietDAO {
    private Connection conn;

    public HoaDonChiTietDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=YourDB;user=sa;password=yourpassword");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HoaDonChiTietDTO> getDSChiTiet(int maHD) {
        ArrayList<HoaDonChiTietDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDonChiTiet WHERE MaHD=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, maHD);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                HoaDonChiTietDTO ct = new HoaDonChiTietDTO(
                    rs.getInt("MaHD"),
                    rs.getInt("MaSP"),
                    rs.getInt("SoLuong"),
                    rs.getDouble("DonGia"),
                    rs.getDouble("ThanhTien")
                );
                list.add(ct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean themChiTiet(HoaDonChiTietDTO ct) {
        String sql = "INSERT INTO HoaDonChiTiet (MaHD, MaSP, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, ct.getMaHD());
            pst.setInt(2, ct.getMaSP());
            pst.setInt(3, ct.getSoLuong());
            pst.setDouble(4, ct.getDonGia());
            pst.setDouble(5, ct.getThanhTien());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaChiTiet(HoaDonChiTietDTO ct) {
        String sql = "UPDATE HoaDonChiTiet SET SoLuong=?, DonGia=?, ThanhTien=? WHERE MaHD=? AND MaSP=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, ct.getSoLuong());
            pst.setDouble(2, ct.getDonGia());
            pst.setDouble(3, ct.getThanhTien());
            pst.setInt(4, ct.getMaHD());
            pst.setInt(5, ct.getMaSP());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaChiTiet(int maHD, int maSP) {
        String sql = "DELETE FROM HoaDonChiTiet WHERE MaHD=? AND MaSP=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, maHD);
            pst.setInt(2, maSP);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
