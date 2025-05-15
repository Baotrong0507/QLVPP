package BUS;

import DAO.SanPhamDAO;
import DTO.SanPhamDTO;

import java.util.ArrayList;

public class SanPhamBUS {
    private SanPhamDAO spDAO = new SanPhamDAO();

    public ArrayList<SanPhamDTO> getDanhSachSP() {
        return spDAO.getDanhSachSP();
    }

    public boolean themSP(SanPhamDTO sp) {
        // Bạn có thể kiểm tra business logic ở đây (ví dụ kiểm tra dữ liệu trước khi thêm)
        return spDAO.themSP(sp);
    }

    public boolean suaSP(SanPhamDTO sp) {
        return spDAO.suaSP(sp);
    }

    public boolean xoaSP(int maSP) {
        return spDAO.xoaSP(maSP);
    }

    public ArrayList<SanPhamDTO> timKiemSP(String keyword) {
        return spDAO.timKiemSP(keyword);
    }
}
