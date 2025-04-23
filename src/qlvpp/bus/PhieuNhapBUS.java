package qlvpp.bus;

import qlvpp.model.PhieuNhap;
import qlvpp.dao.PhieuNhapDAO;

import java.util.ArrayList;
import java.util.List;

public class PhieuNhapBUS {
    private List<PhieuNhap> list;
    private PhieuNhapDAO dao = new PhieuNhapDAO();

    public PhieuNhapBUS() {
        list = dao.getAll();
    }

    public List<PhieuNhap> getAll() {
        return list;
    }

    public boolean add(PhieuNhap pn) {
        for (PhieuNhap item : list) {
            if (item.getMaPN() == pn.getMaPN()) {
                return false; // Trùng mã
            }
        }
        dao.insert(pn);
        list.add(pn);
        return true;
    }

    public void update(PhieuNhap pn) {
        dao.update(pn);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMaPN() == pn.getMaPN()) {
                list.set(i, pn);
                break;
            }
        }
    }

    public void delete(int maPN) {
        dao.delete(maPN);
        list.removeIf(pn -> pn.getMaPN() == maPN);
    }

    public List<PhieuNhap> search(String keyword) {
        keyword = keyword.toLowerCase();
        List<PhieuNhap> result = new ArrayList<>();
        for (PhieuNhap pn : list) {
            if (String.valueOf(pn.getMaPN()).contains(keyword) ||
                String.valueOf(pn.getMaNCC()).contains(keyword)) {
                result.add(pn);
            }
        }
        return result;
    }
}