package qlvpp.bus;

import qlvpp.model.ChiTietPhieuNhap;
import qlvpp.dao.ChiTietPhieuNhapDAO;
import java.util.*;

public class ChiTietPhieuNhapBUS {
    private ChiTietPhieuNhapDAO dao = new ChiTietPhieuNhapDAO();

    public List<ChiTietPhieuNhap> getByMaPN(int maPN) {
        return dao.getByMaPN(maPN);
    }

    public void add(ChiTietPhieuNhap ct) {
        List<ChiTietPhieuNhap> dsCT = dao.getByMaPN(ct.getMaPN());

        for (ChiTietPhieuNhap item : dsCT) {
            if (item.getMaSP() == ct.getMaSP()) {
                // Nếu đã tồn tại sản phẩm trong phiếu, cộng dồn số lượng
                item.setSoLuong(item.getSoLuong() + ct.getSoLuong());
                dao.update(item);
                return;
            }
        }

        // Nếu không trùng thì thêm mới
        dao.insert(ct);
    }

    public void update(ChiTietPhieuNhap ct) {
        dao.update(ct);
    }

    public void delete(int maPN, int maSP) {
        dao.delete(maPN, maSP);
    }
}