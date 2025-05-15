package qlvpp.bus;

import java.util.ArrayList;
import qlvpp.dao.SanPhamDAO;
import qlvpp.model.SanPham;

public class SanPhamBUS {
    private ArrayList<SanPham> dssp = new ArrayList<>();
    private SanPhamDAO dao = new SanPhamDAO();

    public SanPhamBUS() {
        dssp = dao.getAll();
    }

    public ArrayList<SanPham> getAll() {
        return dssp;
    }

    public boolean them(SanPham sp) {
        for (SanPham s : dssp) {
            if (s.getMaSP() == sp.getMaSP()) return false;
        }
        if (dao.insert(sp)) {
            dssp.add(sp);
            return true;
        }
        return false;
    }

    public boolean sua(SanPham sp) {
        if (dao.update(sp)) {
            for (int i = 0; i < dssp.size(); i++) {
                if (dssp.get(i).getMaSP() == sp.getMaSP()) {
                    dssp.set(i, sp);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean xoa(int maSP) {
        if (dao.delete(maSP)) {
            dssp.removeIf(sp -> sp.getMaSP() == maSP);
            return true;
        }
        return false;
    }
    public SanPham timTheoMa(int maSP) {
    for (SanPham sp : dssp) {
        if (sp.getMaSP() == maSP) return sp;
    }
    return null;
    }

    // 🔍 Tìm kiếm theo 2 mã loại khác nhau (OR)
    public ArrayList<SanPham> timTheoHaiMaLoai(int ma1, int ma2) {
        ArrayList<SanPham> kq = new ArrayList<>();
        for (SanPham sp : dssp) {
            if (sp.getMaLoai() == ma1 || sp.getMaLoai() == ma2) {
                kq.add(sp);
            }
        }
        return kq;
    }
}
