package qlvpp.bus;

import qlvpp.model.PhieuNhap;
import qlvpp.dao.PhieuNhapDAO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void filterByDateRangeAndMonths(String startDate, String endDate, String monthsInput) {
        // Làm mới danh sách từ cơ sở dữ liệu
        list = dao.getAll();
        List<PhieuNhap> filteredList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate start = null, end = null;
        List<Integer> months = new ArrayList<>();

        // Xử lý ngày
        if (startDate != null && !startDate.trim().isEmpty()) {
            try {
                start = LocalDate.parse(startDate, formatter);
                System.out.println("Start date parsed: " + start);
            } catch (DateTimeParseException e) {
                System.out.println("Lỗi định dạng startDate: " + startDate);
                start = null;
            }
        }
        if (endDate != null && !endDate.trim().isEmpty()) {
            try {
                end = LocalDate.parse(endDate, formatter);
                System.out.println("End date parsed: " + end);
            } catch (DateTimeParseException e) {
                System.out.println("Lỗi định dạng endDate: " + endDate);
                end = null;
            }
        }

        // Xử lý tháng
        if (monthsInput != null && !monthsInput.trim().isEmpty()) {
            String[] monthArray = monthsInput.split(",");
            for (String month : monthArray) {
                try {
                    int m = Integer.parseInt(month.trim());
                    if (m >= 1 && m <= 12) {
                        months.add(m);
                    } else {
                        System.out.println("Tháng không hợp lệ: " + m);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi định dạng tháng: " + month);
                }
            }
        }
        System.out.println("Months to filter: " + months);

        // Lọc dữ liệu
        int originalSize = list.size();
        for (PhieuNhap pn : list) {
            if (pn.getNgayNhap() == null) {
                System.out.println("NgayNhap null cho MaPN: " + pn.getMaPN());
                continue;
            }
            LocalDate ngayNhap = pn.getNgayNhap().toLocalDate();
            boolean dateMatch = true;

            // Kiểm tra khoảng thời gian
            if (start != null && ngayNhap.isBefore(start)) {
                dateMatch = false;
                System.out.println("Loại bỏ do trước start: " + ngayNhap);
            }
            if (end != null && ngayNhap.isAfter(end)) {
                dateMatch = false;
                System.out.println("Loại bỏ do sau end: " + ngayNhap);
            }

            // Kiểm tra tháng
            boolean monthMatch = months.isEmpty();
            if (!months.isEmpty()) {
                int month = ngayNhap.getMonthValue();
                monthMatch = months.contains(month);
                if (!monthMatch) {
                    System.out.println("Loại bỏ do tháng không khớp: " + month);
                }
            }

            if (dateMatch && monthMatch) {
                filteredList.add(pn);
                System.out.println("Thêm vào filteredList: " + ngayNhap + ", TongTien: " + pn.getTongTien());
            }
        }

        list = filteredList;
        System.out.println("Số bản ghi ban đầu: " + originalSize + ", Sau khi lọc: " + list.size());
    }

    public double tinhChiTieu() {
        double tongChiTieu = 0;
        for (PhieuNhap pn : list) {
            tongChiTieu += pn.getTongTien();
        }
        return tongChiTieu;
    }

    public Map<String, Double> tinhChiTieuTheoThang() {
        Map<String, Double> chiTieuTheoThang = new HashMap<>();
        for (PhieuNhap pn : list) {
            int month = pn.getNgayNhap().toLocalDate().getMonthValue();
            int year = pn.getNgayNhap().toLocalDate().getYear();
            String key = "Tháng " + month + "/" + year;
            chiTieuTheoThang.put(key, chiTieuTheoThang.getOrDefault(key, 0.0) + pn.getTongTien());
        }
        System.out.println("Chi tiêu theo tháng: " + chiTieuTheoThang);
        return chiTieuTheoThang;
    }
}