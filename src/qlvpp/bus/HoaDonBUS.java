package qlvpp.bus;

import qlvpp.dao.HoaDonDAO;
import qlvpp.model.HoaDon;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HoaDonBUS {
    private HoaDonDAO hoaDonDAO;
    private List<HoaDon> danhSachHoaDon;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public HoaDonBUS() {
        hoaDonDAO = new HoaDonDAO();
        danhSachHoaDon = hoaDonDAO.getAllHoaDon();
    }

    public List<HoaDon> getAllHoaDon() {
        return hoaDonDAO.getAllHoaDon();
    }

    public boolean addHoaDon(HoaDon hd) {
        boolean result = hoaDonDAO.addHoaDon(hd);
        if (result) {
            danhSachHoaDon = hoaDonDAO.getAllHoaDon();
        }
        return result;
    }

    public boolean updateHoaDon(HoaDon hd) {
        boolean result = hoaDonDAO.updateHoaDon(hd);
        if (result) {
            danhSachHoaDon = hoaDonDAO.getAllHoaDon();
        }
        return result;
    }

    public boolean deleteHoaDon(int maHD) {
        boolean result = hoaDonDAO.deleteHoaDon(maHD);
        if (result) {
            danhSachHoaDon = hoaDonDAO.getAllHoaDon();
        }
        return result;
    }

    public List<HoaDon> searchHoaDon(String search) {
        return hoaDonDAO.searchHoaDon(search);
    }

    // Tính tổng doanh thu
    public double tinhDoanhThu() {
        double doanhThu = 0.0;
        for (HoaDon hd : danhSachHoaDon) {
            doanhThu += hd.getTongTien();
        }
        return doanhThu;
    }

    // Tính doanh thu theo tháng
    public Map<String, Double> tinhDoanhThuTheoThang() {
        Map<String, Double> doanhThuTheoThang = new HashMap<>();
        for (HoaDon hd : danhSachHoaDon) {
            // Chuyển java.sql.Date thành LocalDate để xử lý
            LocalDate ngayLap = hd.getNgayLap().toLocalDate();
            String thangNam = ngayLap.format(DateTimeFormatter.ofPattern("MM/yyyy"));
            String key = "Tháng " + thangNam;
            doanhThuTheoThang.put(key, doanhThuTheoThang.getOrDefault(key, 0.0) + hd.getTongTien());
        }
        return doanhThuTheoThang;
    }

    // Lọc dữ liệu theo khoảng ngày và tháng
    public void filterByDateRangeAndMonths(String startDate, String endDate, String monthsInput) {
        danhSachHoaDon = hoaDonDAO.getAllHoaDon();
        LocalDate start = startDate.isEmpty() ? null : LocalDate.parse(startDate, formatter);
        LocalDate end = endDate.isEmpty() ? null : LocalDate.parse(endDate, formatter);
        List<Integer> months = parseMonths(monthsInput);

        danhSachHoaDon = danhSachHoaDon.stream()
            .filter(hd -> {
                LocalDate ngayLap = hd.getNgayLap().toLocalDate();
                boolean inDateRange = (start == null || !ngayLap.isBefore(start)) &&
                                     (end == null || !ngayLap.isAfter(end));
                boolean inMonths = months.isEmpty() || months.contains(ngayLap.getMonthValue());
                return inDateRange && inMonths;
            })
            .collect(Collectors.toList());
    }

    private List<Integer> parseMonths(String monthsInput) {
        List<Integer> months = new ArrayList<>();
        if (!monthsInput.isEmpty()) {
            String[] monthArray = monthsInput.split(",");
            for (String month : monthArray) {
                try {
                    months.add(Integer.parseInt(month.trim()));
                } catch (NumberFormatException e) {
                    // Bỏ qua nếu tháng không hợp lệ
                }
            }
        }
        return months;
    }
}