package qlvpp.dao;

import qlvpp.model.KhuyenMai;
import qlvpp.model.KhuyenMaiSanPham;
import qlvpp.model.KhuyenMaiHoaDon;
import qlvpp.model.SanPham;
import qlvpp.connections.Myconnections;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class KhuyenMaiDAO {
    private Connection conn;

    public KhuyenMaiDAO() {
        conn = Myconnections.getConnection();
    }

    public List<KhuyenMai> getAllKhuyenMai() {
        List<KhuyenMai> khuyenMaiList = new ArrayList<>();
        String sql = "SELECT * FROM ChuongTrinhKhuyenMai";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai(
                    rs.getInt("MaKM"),
                    rs.getString("NgayBatDau"),
                    rs.getString("NgayKetThuc")
                );
                khuyenMaiList.add(km);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return khuyenMaiList;
    }

    public KhuyenMai getKhuyenMaiById(int maKM) {
    String sql = "SELECT * FROM ChuongTrinhKhuyenMai WHERE MaKM = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, maKM);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new KhuyenMai(
                    rs.getInt("MaKM"),
                    rs.getDate("NgayBatDau") != null ? new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("NgayBatDau")) : null,
                    rs.getDate("NgayKetThuc") != null ? new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("NgayKetThuc")) : null
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

public boolean addKhuyenMai(KhuyenMai km, List<KhuyenMaiSanPham> kmspList, List<KhuyenMaiHoaDon> kmhdList) {
    try {
        conn.setAutoCommit(false);

        // Insert into ChuongTrinhKhuyenMai
        String sqlKM = "INSERT INTO ChuongTrinhKhuyenMai (MaKM, NgayBatDau, NgayKetThuc) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlKM)) {
            ps.setInt(1, km.getMaKM());

            // Chuyển đổi String thành java.sql.Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateBatDau = sdf.parse(km.getNgayBatDau());
            java.util.Date dateKetThuc = sdf.parse(km.getNgayKetThuc());
            ps.setDate(2, new java.sql.Date(dateBatDau.getTime()));
            ps.setDate(3, new java.sql.Date(dateKetThuc.getTime()));

            ps.executeUpdate();
        }

        // Insert KhuyenMaiSanPham
        String sqlKMSP = "INSERT INTO KhuyenMaiSanPham (MaKM, MaSP, PhanTramGiam) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlKMSP)) {
            for (KhuyenMaiSanPham kmsp : kmspList) {
                ps.setInt(1, kmsp.getMaKM());
                ps.setInt(2, kmsp.getMaSP());
                ps.setDouble(3, kmsp.getPhanTramGiam());
                ps.addBatch();
            }
            ps.executeBatch();
        }

        // Insert KhuyenMaiHoaDon
        String sqlKMHD = "INSERT INTO KhuyenMaiHoaDon (MaKM, SoTienHoaDon, PhanTramGiam) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlKMHD)) {
            for (KhuyenMaiHoaDon kmhd : kmhdList) {
                ps.setInt(1, kmhd.getMaKM());
                ps.setDouble(2, kmhd.getSoTienHoaDon());
                ps.setDouble(3, kmhd.getPhanTramGiam());
                ps.addBatch();
            }
            ps.executeBatch();
        }

        conn.commit();
        return true;
    } catch (SQLException e) {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
        return false;
    } catch (ParseException e) {
        e.printStackTrace();
        return false;
    } finally {
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public boolean updateKhuyenMai(KhuyenMai km, List<KhuyenMaiSanPham> kmspList, List<KhuyenMaiHoaDon> kmhdList) {
    try {
        conn.setAutoCommit(false);

        // Update ChuongTrinhKhuyenMai
        String sqlKM = "UPDATE ChuongTrinhKhuyenMai SET NgayBatDau = ?, NgayKetThuc = ? WHERE MaKM = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlKM)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateBatDau = sdf.parse(km.getNgayBatDau());
            java.util.Date dateKetThuc = sdf.parse(km.getNgayKetThuc());
            ps.setDate(1, new java.sql.Date(dateBatDau.getTime()));
            ps.setDate(2, new java.sql.Date(dateKetThuc.getTime()));
            ps.setInt(3, km.getMaKM());
            ps.executeUpdate();
        }

        // Delete old KhuyenMaiSanPham
        String sqlDeleteKMSP = "DELETE FROM KhuyenMaiSanPham WHERE MaKM = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlDeleteKMSP)) {
            ps.setInt(1, km.getMaKM());
            ps.executeUpdate();
        }

        // Insert new KhuyenMaiSanPham
        String sqlKMSP = "INSERT INTO KhuyenMaiSanPham (MaKM, MaSP, PhanTramGiam) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlKMSP)) {
            for (KhuyenMaiSanPham kmsp : kmspList) {
                ps.setInt(1, kmsp.getMaKM());
                ps.setInt(2, kmsp.getMaSP());
                ps.setDouble(3, kmsp.getPhanTramGiam());
                ps.addBatch();
            }
            ps.executeBatch();
        }

        // Delete old KhuyenMaiHoaDon
        String sqlDeleteKMHD = "DELETE FROM KhuyenMaiHoaDon WHERE MaKM = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlDeleteKMHD)) {
            ps.setInt(1, km.getMaKM());
            ps.executeUpdate();
        }

        // Insert new KhuyenMaiHoaDon
        String sqlKMHD = "INSERT INTO KhuyenMaiHoaDon (MaKM, SoTienHoaDon, PhanTramGiam) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlKMHD)) {
            for (KhuyenMaiHoaDon kmhd : kmhdList) {
                ps.setInt(1, kmhd.getMaKM());
                ps.setDouble(2, kmhd.getSoTienHoaDon());
                ps.setDouble(3, kmhd.getPhanTramGiam());
                ps.addBatch();
            }
            ps.executeBatch();
        }

        conn.commit();
        return true;
    } catch (SQLException e) {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
        return false;
    } catch (ParseException e) {
        e.printStackTrace();
        return false;
    } finally {
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

    public boolean deleteKhuyenMai(int maKM) {
        try {
            conn.setAutoCommit(false);

            // Delete KhuyenMaiSanPham
            String sqlKMSP = "DELETE FROM KhuyenMaiSanPham WHERE MaKM = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlKMSP)) {
                ps.setInt(1, maKM);
                ps.executeUpdate();
            }

            // Delete KhuyenMaiHoaDon
            String sqlKMHD = "DELETE FROM KhuyenMaiHoaDon WHERE MaKM = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlKMHD)) {
                ps.setInt(1, maKM);
                ps.executeUpdate();
            }

            // Delete ChuongTrinhKhuyenMai
            String sqlKM = "DELETE FROM ChuongTrinhKhuyenMai WHERE MaKM = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlKM)) {
                ps.setInt(1, maKM);
                ps.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<KhuyenMai> searchKhuyenMai(String maKM, String ngayBatDau, String ngayKetThuc) {
    List<KhuyenMai> khuyenMaiList = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT * FROM ChuongTrinhKhuyenMai WHERE 1=1");
    List<Object> params = new ArrayList<>();

    if (maKM != null && !maKM.isEmpty()) {
        sql.append(" AND MaKM = ?");
        params.add(Integer.parseInt(maKM));
    }
    if (ngayBatDau != null && !ngayBatDau.isEmpty()) {
        sql.append(" AND NgayBatDau = ?");
        params.add(ngayBatDau);
    }
    if (ngayKetThuc != null && !ngayKetThuc.isEmpty()) {
        sql.append(" AND NgayKetThuc = ?");
        params.add(ngayKetThuc);
    }

    try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            KhuyenMai km = new KhuyenMai(
                rs.getInt("MaKM"),
                rs.getString("NgayBatDau"),
                rs.getString("NgayKetThuc")
            );
            khuyenMaiList.add(km);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return khuyenMaiList;
}

    public List<SanPham> getAllSanPham() {
        List<SanPham> sanPhamList = new ArrayList<>();
        String sql = "SELECT MaSP, TenSP FROM SanPham";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SanPham sp = new SanPham(rs.getInt("MaSP"), rs.getString("TenSP"));
                sanPhamList.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sanPhamList;
    }

    public SanPham getSanPhamById(int maSP) {
        String sql = "SELECT MaSP, TenSP FROM SanPham WHERE MaSP = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSP);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new SanPham(rs.getInt("MaSP"), rs.getString("TenSP"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<KhuyenMaiSanPham> getKhuyenMaiSanPham(int maKM) {
        List<KhuyenMaiSanPham> kmspList = new ArrayList<>();
        String sql = "SELECT * FROM KhuyenMaiSanPham WHERE MaKM = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maKM);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                kmspList.add(new KhuyenMaiSanPham(
                    rs.getInt("MaKM"),
                    rs.getInt("MaSP"),
                    rs.getDouble("PhanTramGiam")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kmspList;
    }

    public List<KhuyenMaiHoaDon> getKhuyenMaiHoaDon(int maKM) {
        List<KhuyenMaiHoaDon> kmhdList = new ArrayList<>();
        String sql = "SELECT * FROM KhuyenMaiHoaDon WHERE MaKM = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maKM);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                kmhdList.add(new KhuyenMaiHoaDon(
                    rs.getInt("MaKM"),
                    rs.getDouble("SoTienHoaDon"),
                    rs.getDouble("PhanTramGiam")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kmhdList;
    }
    public int getNextMaKM() {
    String sql = "SELECT COALESCE(MAX(MaKM), 0) + 1 AS nextMaKM FROM ChuongTrinhKhuyenMai";
    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            return rs.getInt("nextMaKM");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 1;
}
}