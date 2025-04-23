package qlvpp.dao;

import java.sql.*;
import java.util.*;
import qlvpp.model.NhaCungCap;
import qlvpp.connections.Myconnections;

public class NhaCungCapDAO {
    public List<NhaCungCap> getAll() {
        List<NhaCungCap> list = new ArrayList<>();
        try (Connection conn = Myconnections.getConnection()) {
            String sql = "SELECT * FROM NhaCungCap";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new NhaCungCap(
                    rs.getInt("MaNCC"),
                    rs.getString("TenNCC"),
                    rs.getString("DiaChi"),
                    rs.getString("Sdt")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(NhaCungCap ncc) {
        try (Connection conn = Myconnections.getConnection()) {
            String sql = "INSERT INTO NhaCungCap VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ncc.getMaNCC());
            stmt.setString(2, ncc.getTenNCC());
            stmt.setString(3, ncc.getDiaChi());
            stmt.setString(4, ncc.getSdt());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(NhaCungCap ncc) {
        try (Connection conn = Myconnections.getConnection()) {
            String sql = "UPDATE NhaCungCap SET TenNCC=?, DiaChi=?, Sdt=? WHERE MaNCC=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ncc.getTenNCC());
            stmt.setString(2, ncc.getDiaChi());
            stmt.setString(3, ncc.getSdt());
            stmt.setInt(4, ncc.getMaNCC());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int maNCC) {
        try (Connection conn = Myconnections.getConnection()) {
            String sql = "DELETE FROM NhaCungCap WHERE MaNCC=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, maNCC);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

