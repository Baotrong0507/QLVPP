package qlvpp.bus;

import java.util.*;
import qlvpp.model.NhaCungCap;
import qlvpp.dao.NhaCungCapDAO;

public class NhaCungCapBUS {
    private List<NhaCungCap> list;
    private NhaCungCapDAO dao = new NhaCungCapDAO();

    public NhaCungCapBUS() {
        list = dao.getAll();
    }

    public List<NhaCungCap> getAllNCC() {
        return list;
    }

    public boolean addNCC(NhaCungCap ncc) {
        for (NhaCungCap item : list) {
            if (item.getMaNCC() == ncc.getMaNCC()) {
                return false; // Trùng mã
            }
        }
        dao.insert(ncc);
        list.add(ncc);
        return true;
    }

    public void updateNCC(NhaCungCap ncc) {
        dao.update(ncc);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMaNCC() == ncc.getMaNCC()) {
                list.set(i, ncc);
                break;
            }
        }
    }

    public void deleteNCC(int maNCC) {
        dao.delete(maNCC);
        list.removeIf(ncc -> ncc.getMaNCC() == maNCC);
    }

    public List<NhaCungCap> search(String keyword) {
        keyword = keyword.toLowerCase();
        List<NhaCungCap> result = new ArrayList<>();
        for (NhaCungCap ncc : list) {
            if (String.valueOf(ncc.getMaNCC()).contains(keyword) || 
                ncc.getTenNCC().toLowerCase().contains(keyword)) {
                result.add(ncc);
            }
        }
        return result;
    }
}
