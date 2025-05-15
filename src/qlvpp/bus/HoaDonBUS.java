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
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public HoaDonBUS() {
        hoaDonDAO = new HoaDonDAO();
    }

    public List<HoaDon> getAllHoaDon() {
        return hoaDonDAO.getAllHoaDon();
    }

    public void themHoaDon(int maHD, String maKH, String maNV, String ngayLap, String tongTien) throws Exception {
        // Kiểm tra dữ liệu
        int maKhachHang, maNhanVien;
        double tongTienValue;
        LocalDate localDate;

        // Kiểm tra mã hóa đơn đã tồn tại chưa
        if (hoaDonDAO.getHoaDonByMaHD(maHD) != null) {
            throw new Exception("Mã hóa đơn đã tồn tại!");
        }

        try {
            maKhachHang = Integer.parseInt(maKH);
            maNhanVien = Integer.parseInt(maNV);
            tongTienValue = Double.parseDouble(tongTien);
            if (tongTienValue < 0) {
                throw new Exception("Tổng tiền không thể âm!");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Mã KH, Mã NV và Tổng Tiền phải là số hợp lệ!");
        }

        // Parse ngày lập
        try {
            localDate = LocalDate.parse(ngayLap, formatter);
        } catch (DateTimeParseException e) {
            throw new Exception("Vui lòng nhập ngày theo định dạng dd/MM/yyyy!");
        }

        // Tạo đối tượng HoaDon
        Date ngayLapDate = Date.valueOf(localDate);
        HoaDon hd = new HoaDon(maHD, maKhachHang, maNhanVien, ngayLapDate, tongTienValue);

        // Gọi DAO để thêm
        if (!hoaDonDAO.addHoaDon(hd)) {
            throw new Exception("Thêm hóa đơn thất bại!");
        }
    }

    public void suaHoaDon(int maHD, String maKH, String maNV, String ngayLap, String tongTien) throws Exception {
        // Kiểm tra dữ liệu
        int maKhachHang, maNhanVien;
        double tongTienValue;
        LocalDate localDate;

        // Kiểm tra mã hóa đơn có tồn tại không
        if (hoaDonDAO.getHoaDonByMaHD(maHD) == null) {
            throw new Exception("Mã hóa đơn không tồn tại!");
        }

        try {
            maKhachHang = Integer.parseInt(maKH);
            maNhanVien = Integer.parseInt(maNV);
            tongTienValue = Double.parseDouble(tongTien);
            if (tongTienValue < 0) {
                throw new Exception("Tổng tiền không thể âm!");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Mã KH, Mã NV và Tổng Tiền phải là số hợp lệ!");
        }

        // Parse ngày lập
        try {
            localDate = LocalDate.parse(ngayLap, formatter);
        } catch (DateTimeParseException e) {
            throw new Exception("Vui lòng nhập ngày theo định dạng dd/MM/yyyy!");
        }

        // Tạo đối tượng HoaDon
        Date ngayLapDate = Date.valueOf(localDate);
        HoaDon hd = new HoaDon(maHD, maKhachHang, maNhanVien, ngayLapDate, tongTienValue);

        // Gọi DAO để sửa
        if (!hoaDonDAO.updateHoaDon(hd)) {
            throw new Exception("Sửa hóa đơn thất bại!");
        }
    }

    public void xoaHoaDon(int maHD) throws Exception {
        // Kiểm tra mã hóa đơn có tồn tại không
        if (hoaDonDAO.getHoaDonByMaHD(maHD) == null) {
            throw new Exception("Mã hóa đơn không tồn tại!");
        }

        if (!hoaDonDAO.deleteHoaDon(maHD)) {
            throw new Exception("Xóa hóa đơn thất bại!");
        }
    }

    public List<HoaDon> timKiemHoaDon(String search) throws Exception {
        if (search.trim().isEmpty()) {
            throw new Exception("Mã HD tìm kiếm không được để trống!");
        }
        List<HoaDon> result = hoaDonDAO.searchHoaDon(search);
        if (result.isEmpty()) {
            throw new Exception("Không tìm thấy hóa đơn nào!");
        }
        return result;
    }

    // Tính tổng doanh thu
    public double tinhDoanhThu() {
        double doanhThu = 0.0;
        List<HoaDon> danhSachHoaDon = hoaDonDAO.getAllHoaDon();
        for (HoaDon hd : danhSachHoaDon) {
            doanhThu += hd.getTongTien();
        }
        return doanhThu;
    }

    // Tự động tạo mã hóa đơn
    public int generateMaHD() {
        try {
            int maxMaHD = hoaDonDAO.getMaxMaHD();
            if (maxMaHD == 0) {
                return 1; // Mặc định nếu không có hóa đơn nào
            }
            return maxMaHD + 1; // Tăng mã lên 1
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo mã hóa đơn: " + e.getMessage());
        }
    }

    // Tính doanh thu theo tháng
    public Map<String, Double> tinhDoanhThuTheoThang() {
        Map<String, Double> doanhThuTheoThang = new HashMap<>();
        List<HoaDon> danhSachHoaDon = hoaDonDAO.getAllHoaDon();
        for (HoaDon hd : danhSachHoaDon) {
            LocalDate ngayLap = hd.getNgayLap().toLocalDate();
            String thangNam = ngayLap.format(DateTimeFormatter.ofPattern("MM/yyyy"));
            String key = "Tháng " + thangNam;
            doanhThuTheoThang.put(key, doanhThuTheoThang.getOrDefault(key, 0.0) + hd.getTongTien());
        }
        return doanhThuTheoThang;
    }

    // Lọc hóa đơn theo khoảng ngày và tháng
    public List<HoaDon> filterByDateRangeAndMonths(String startDate, String endDate, String monthsInput) {
        List<HoaDon> danhSachHoaDon = hoaDonDAO.getAllHoaDon();
        LocalDate start = startDate.isEmpty() ? null : LocalDate.parse(startDate, formatter);
        LocalDate end = endDate.isEmpty() ? null : LocalDate.parse(endDate, formatter);
        List<Integer> months = parseMonths(monthsInput);

        return danhSachHoaDon.stream()
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
                    int monthValue = Integer.parseInt(month.trim());
                    if (monthValue >= 1 && monthValue <= 12) {
                        months.add(monthValue);
                    }
                } catch (NumberFormatException e) {
                    // Bỏ qua nếu tháng không hợp lệ
                }
            }
        }
        return months;
    }
}