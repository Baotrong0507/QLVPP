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
        dao.insert(ct);
    }

    public void update(ChiTietPhieuNhap ct) {
        dao.update(ct);
    }

    public void delete(int maPN, int maSP) {
        dao.delete(maPN, maSP);
    }
}
