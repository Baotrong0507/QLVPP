package qlvpp.dao;

import java.sql.*;
import java.util.ArrayList;
import qlvpp.model.SanPham;
import qlvpp.connections.Myconnections;

public class SanPhamDAO {

    public ArrayList<SanPham> getAll() {
        ArrayList<SanPham> ds = new ArrayList<>();
        String sql = "SELECT * FROM SanPham";
        try (Connection con = Myconnections.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SanPham sp = new SanPham(
                    rs.getInt("MaSP"),
                    rs.getString("TenSP"),
                    rs.getInt("SoLuong"),
                    rs.getDouble("DonGia"),
                    rs.getString("DonViTienTe"), // tên cột DB cần trùng
                    rs.getInt("MaLoai"),
                    rs.getString("XuatXu")
                );
                ds.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }

    public boolean insert(SanPham sp) {
        String sql = "INSERT INTO SanPham VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Myconnections.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, sp.getMaSP());
            ps.setString(2, sp.getTenSP());
            ps.setInt(3, sp.getSoLuong());
            ps.setDouble(4, sp.getDonGia());
            ps.setString(5, sp.getDonViTienTe());
            ps.setInt(6, sp.getMaLoai());
            ps.setString(7, sp.getXuatXu());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(SanPham sp) {
        String sql = "UPDATE SanPham SET TenSP=?, SoLuong=?, DonGia=?, DonViTienTe=?, MaLoai=?, XuatXu=? WHERE MaSP=?";
        try (Connection con = Myconnections.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sp.getTenSP());
            ps.setInt(2, sp.getSoLuong());
            ps.setDouble(3, sp.getDonGia());
            ps.setString(4, sp.getDonViTienTe());
            ps.setInt(5, sp.getMaLoai());
            ps.setString(6, sp.getXuatXu());
            ps.setInt(7, sp.getMaSP());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int maSP) {
        String sql = "DELETE FROM SanPham WHERE MaSP=?";
        try (Connection con = Myconnections.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, maSP);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
