package qlvpp.gui;

import javax.swing.*;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import qlvpp.bus.PhieuNhapBUS;

public class ThongKeGUI extends JPanel {

    private JLabel lblDoanhThu, lblChiTieu, lblLoiNhuan;
    private PhieuNhapBUS phieuNhapBUS;
    private JTextField txtStartDate, txtEndDate, txtMonths;
    private JButton btnFilter;

    public ThongKeGUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        phieuNhapBUS = new PhieuNhapBUS();

        // Tiêu đề
        JLabel lblTitle = new JLabel("Bảng Thống Kê", SwingConstants.CENTER);
        lblTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        lblTitle.setForeground(new Color(20, 20, 70));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitle, BorderLayout.NORTH);

        // Panel bộ lọc (phía trên)
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.add(new JLabel("Từ ngày (dd/MM/yyyy):"));
        txtStartDate = new JTextField(10);
        filterPanel.add(txtStartDate);
        filterPanel.add(new JLabel("Đến ngày (dd/MM/yyyy):"));
        txtEndDate = new JTextField(10);
        filterPanel.add(txtEndDate);
        filterPanel.add(new JLabel("Tháng (1,2,...):"));
        txtMonths = new JTextField(10);
        filterPanel.add(txtMonths);

        btnFilter = new JButton("Lọc");
        btnFilter.setBackground(new Color(0, 120, 215));
        btnFilter.setForeground(Color.WHITE);
        btnFilter.addActionListener(e -> applyFilter());
        filterPanel.add(btnFilter);

        add(filterPanel, BorderLayout.SOUTH);

        // Panel hiển thị số liệu (phía trái)
        JPanel statsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        statsPanel.setBackground(Color.WHITE);

        // Doanh thu (ghi chú)
        lblDoanhThu = new JLabel("Doanh thu: Chưa triển khai");
        lblDoanhThu.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        statsPanel.add(lblDoanhThu);

        // Chi tiêu
        lblChiTieu = new JLabel("Chi tiêu: 0 VNĐ");
        lblChiTieu.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        statsPanel.add(lblChiTieu);

        // Lợi nhuận (ghi chú)
        lblLoiNhuan = new JLabel("Lợi nhuận: Chưa triển khai");
        lblLoiNhuan.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        statsPanel.add(lblLoiNhuan);

        add(statsPanel, BorderLayout.WEST);

        // Tính toán và cập nhật số liệu ban đầu
        updateStatistics();

        // Vẽ biểu đồ ban đầu
        ChartPanel chartPanel = createChart();
        add(chartPanel, BorderLayout.CENTER);
    }

    private void applyFilter() {
        String startDate = txtStartDate.getText().trim();
        String endDate = txtEndDate.getText().trim();
        String monthsInput = txtMonths.getText().trim();

        // Kiểm tra định dạng ngày
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            if (!startDate.isEmpty()) {
                LocalDate.parse(startDate, formatter);
            }
            if (!endDate.isEmpty()) {
                LocalDate.parse(endDate, formatter);
            }
            if (!startDate.isEmpty() && !endDate.isEmpty()) {
                LocalDate start = LocalDate.parse(startDate, formatter);
                LocalDate end = LocalDate.parse(endDate, formatter);
                if (start.isAfter(end)) {
                    throw new IllegalArgumentException("Ngày bắt đầu phải trước ngày kết thúc");
                }
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày theo định dạng dd/MM/yyyy: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra tháng
        if (!monthsInput.isEmpty()) {
            String[] monthArray = monthsInput.split(",");
            for (String month : monthArray) {
                try {
                    int m = Integer.parseInt(month.trim());
                    if (m < 1 || m > 12) {
                        throw new NumberFormatException("Tháng phải từ 1 đến 12");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập tháng hợp lệ: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        phieuNhapBUS.filterByDateRangeAndMonths(startDate, endDate, monthsInput);
        updateStatistics();

        // Xóa biểu đồ cũ và thêm biểu đồ mới
        java.awt.Component[] components = getComponents();
        for (java.awt.Component comp : components) {
            if (comp instanceof ChartPanel) {
                remove(comp);
                break;
            }
        }
        ChartPanel chartPanel = createChart();
        add(chartPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
        System.out.println("Biểu đồ đã được làm mới");
    }

    private void updateStatistics() {
        // Ghi chú: Thêm logic doanh thu khi có HoaDonDAO
        /*
        double doanhThu = phieuNhapBUS.tinhDoanhThu();
        lblDoanhThu.setText("Doanh thu: " + String.format("%,.0f", doanhThu) + " VNĐ");
        */

        double chiTieu = phieuNhapBUS.tinhChiTieu();
        lblChiTieu.setText("Chi tiêu: " + String.format("%,.0f", chiTieu) + " VNĐ");

        // Ghi chú: Thêm logic lợi nhuận khi có HoaDonDAO
        /*
        double loiNhuan = phieuNhapBUS.tinhLoiNhuan();
        lblLoiNhuan.setText("Lợi nhuận: " + String.format("%,.0f", loiNhuan) + " VNĐ");
        */
    }

    private ChartPanel createChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Double> chiTieuTheoThang = phieuNhapBUS.tinhChiTieuTheoThang();
        if (chiTieuTheoThang.isEmpty()) {
            dataset.addValue(0.0, "Chi tiêu", "Không có dữ liệu");
            System.out.println("Biểu đồ: Không có dữ liệu để hiển thị");
        } else {
            // Sắp xếp theo tháng và năm
            java.util.List<Map.Entry<String, Double>> entries = new ArrayList<>(chiTieuTheoThang.entrySet());
            entries.sort((entry1, entry2) -> {
                String[] parts1 = entry1.getKey().split("/"); // Tách "Tháng 1/2024" thành [Tháng 1, 2024]
                String[] parts2 = entry2.getKey().split("/");
                int year1 = Integer.parseInt(parts1[1]);
                int month1 = Integer.parseInt(parts1[0].replace("Tháng ", ""));
                int year2 = Integer.parseInt(parts2[1]);
                int month2 = Integer.parseInt(parts2[0].replace("Tháng ", ""));
                if (year1 != year2) {
                    return year1 - year2;
                }
                return month1 - month2;
            });

            // Thêm vào dataset theo thứ tự đã sắp xếp
            for (Map.Entry<String, Double> entry : entries) {
                dataset.addValue(entry.getValue(), "Chi tiêu", entry.getKey());
                System.out.println("Thêm vào biểu đồ: " + entry.getKey() + " - " + entry.getValue());
            }
        }

        JFreeChart barChart = ChartFactory.createBarChart(
            "Biểu đồ Thống kê Chi tiêu Theo Tháng",
            "Thời gian",
            "Số tiền (VNĐ)",
            dataset
        );

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(500, 300));
        return chartPanel;
    }
}