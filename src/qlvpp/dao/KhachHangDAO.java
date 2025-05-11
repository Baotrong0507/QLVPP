	package qlvpp.dao;
	
	import qlvpp.model.KhachHang;
	import qlvpp.connections.*;
	import java.sql.*;
	import java.util.ArrayList;
	import java.util.List;
	import qlvpp.connections.*;
	
	public class KhachHangDAO {
	    private Connection conn;
	
	    public KhachHangDAO() {
	    	conn = Myconnections.getConnection();
	    }
	
	    public List<KhachHang> getAllKhachHang() {
	        List<KhachHang> khachHangList = new ArrayList<>();
	        String sql = "SELECT * FROM KhachHang";
	        try (PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                KhachHang kh = new KhachHang(
	                    rs.getInt("MaKH"),
	                    rs.getString("Ho"),
	                    rs.getString("Ten"),
	                    rs.getString("DiaChi"),
	                    rs.getString("DienThoai")
	                );
	                khachHangList.add(kh);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return khachHangList;
	    }
	
	    public boolean addKhachHang(KhachHang kh) {
	        String sql = "INSERT INTO KhachHang (MaKH, Ho, Ten, DiaChi, DienThoai) VALUES (?, ?, ?, ?, ?)";
	        try (PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, kh.getMaKH());
	            ps.setString(2, kh.getHo());
	            ps.setString(3, kh.getTen());
	            ps.setString(4, kh.getDiaChi());
	            ps.setString(5, kh.getDienThoai());
	            return ps.executeUpdate() > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	
	    public boolean updateKhachHang(KhachHang kh) {
	        String sql = "UPDATE KhachHang SET Ho = ?, Ten = ?, DiaChi = ?, DienThoai = ? WHERE MaKH = ?";
	        try (PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setString(1, kh.getHo());
	            ps.setString(2, kh.getTen());
	            ps.setString(3, kh.getDiaChi());
	            ps.setString(4, kh.getDienThoai());
	            ps.setInt(5, kh.getMaKH());
	            return ps.executeUpdate() > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	
	    public boolean deleteKhachHang(int maKH) {
	        String sql = "DELETE FROM KhachHang WHERE MaKH = ?";
	        try (PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, maKH);
	            return ps.executeUpdate() > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	
	    public List<KhachHang> searchKhachHang(String search) {
	        List<KhachHang> khachHangList = new ArrayList<>();
	        String sql = "SELECT * FROM KhachHang WHERE Ten LIKE ?";
	        try (PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setString(1, "%" + search + "%");
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                KhachHang kh = new KhachHang(
	                    rs.getInt("MaKH"),
	                    rs.getString("Ho"),
	                    rs.getString("Ten"),
	                    rs.getString("DiaChi"),
	                    rs.getString("DienThoai")
	                );
	                khachHangList.add(kh);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return khachHangList;
	    }
	}