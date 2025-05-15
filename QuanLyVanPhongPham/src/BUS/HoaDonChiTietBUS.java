package BUS;

import DAO.HoaDonChiTietDAO;
import DTO.HoaDonChiTietDTO;

import java.util.ArrayList;

public class HoaDonChiTietBUS {
    private HoaDonChiTietDAO dao = new HoaDonChiTietDAO();

    public ArrayList<HoaDonChiTietDTO> getDSChiTiet(int maHD) {
        return dao.getDSChiTiet(maHD);
    }

    public boolean themChiTiet(HoaDonChiTietDTO ct) {
        return dao.themChiTiet(ct);
    }

    public boolean suaChiTiet(HoaDonChiTietDTO ct) {
        return dao.suaChiTiet(ct);
    }

    public boolean xoaChiTiet(int maHD, int maSP) {
        return dao.xoaChiTiet(maHD, maSP);
    }
}
