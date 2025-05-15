package qlvpp.bus;

import qlvpp.dao.ChiTietHoaDonDAO;
import qlvpp.model.ChiTietHoaDon;
import qlvpp.model.SanPham;

import java.util.List;

public class ChiTietHoaDonBUS {
    private ChiTietHoaDonDAO chiTietHoaDonDAO;

    public ChiTietHoaDonBUS() {
        chiTietHoaDonDAO = new ChiTietHoaDonDAO();
    }

    public List<ChiTietHoaDon> getChiTietByMaHD(int maHD) {
        return chiTietHoaDonDAO.getChiTietByMaHD(maHD);
    }

    public boolean themChiTietHoaDon(ChiTietHoaDon cthd) throws Exception {
        // Kiểm tra số lượng tồn kho trước khi thêm
        SanPhamBUS sanPhamBUS = new SanPhamBUS();
        SanPham sp = sanPhamBUS.timTheoMa(cthd.getMaSP());
        if (sp == null) {
            throw new Exception("Sản phẩm không tồn tại!");
        }
        if (sp.getSoLuong() < cthd.getSoLuong()) {
            throw new Exception("Số lượng tồn kho không đủ! Chỉ còn: " + sp.getSoLuong());
        }

        // Thêm chi tiết hóa đơn
        if (chiTietHoaDonDAO.addChiTietHoaDon(cthd)) {
            // Cập nhật số lượng tồn kho
            chiTietHoaDonDAO.updateSoLuongSanPham(cthd.getMaSP(), cthd.getSoLuong());
            return true;
        }
        return false;
    }
}