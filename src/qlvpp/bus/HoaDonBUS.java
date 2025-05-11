package qlvpp.bus;

import qlvpp.dao.HoaDonDAO;
import qlvpp.model.HoaDon;
import java.util.List;

public class HoaDonBUS {
    private HoaDonDAO hoaDonDAO;

    public HoaDonBUS() {
        hoaDonDAO = new HoaDonDAO();
    }

    public List<HoaDon> getAllHoaDon() {
        return hoaDonDAO.getAllHoaDon();
    }

    public boolean addHoaDon(HoaDon hd) {
        return hoaDonDAO.addHoaDon(hd);
    }

    public boolean updateHoaDon(HoaDon hd) {
        return hoaDonDAO.updateHoaDon(hd);
    }

    public boolean deleteHoaDon(int maHD) {
        return hoaDonDAO.deleteHoaDon(maHD);
    }

    public List<HoaDon> searchHoaDon(String search) {
        return hoaDonDAO.searchHoaDon(search);
    }
}