package qlvpp.gui;

import javax.swing.*;
import java.awt.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import qlvpp.bus.PhieuNhapBUS;

public class ThongKeGUI extends JPanel {

    private JLabel lblDoanhThu, lblChiTieu, lblLoiNhuan;
    private PhieuNhapBUS phieuNhapBUS;

    public ThongKeGUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        phieuNhapBUS = new PhieuNhapBUS();

        // Tiêu đề
        JLabel lblTitle = new JLabel("Bảng Thống Kê", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(20, 20, 70));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitle, BorderLayout.NORTH);

        // Panel hiển thị số liệu
        JPanel statsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        statsPanel.setBackground(Color.WHITE);

        // Doanh thu (ghi chú)
        lblDoanhThu = new JLabel("Doanh thu: Chưa triển khai");
        lblDoanhThu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        statsPanel.add(lblDoanhThu);

        // Chi tiêu
        lblChiTieu = new JLabel("Chi tiêu: 0 VNĐ");
        lblChiTieu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        statsPanel.add(lblChiTieu);

        // Lợi nhuận (ghi chú)
        lblLoiNhuan = new JLabel("Lợi nhuận: Chưa triển khai");
        lblLoiNhuan.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        statsPanel.add(lblLoiNhuan);

        add(statsPanel, BorderLayout.WEST);

        // Tính toán và cập nhật số liệu
        updateStatistics();

        // Vẽ biểu đồ
        ChartPanel chartPanel = createChart();
        add(chartPanel, BorderLayout.CENTER);
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
        // Ghi chú: Thêm dữ liệu doanh thu và lợi nhuận khi có HoaDonDAO
        /*
        dataset.addValue(phieuNhapBUS.tinhDoanhThu(), "Doanh thu", "Thống kê");
        dataset.addValue(phieuNhapBUS.tinhLoiNhuan(), "Lợi nhuận", "Thống kê");
        */
        dataset.addValue(phieuNhapBUS.tinhChiTieu(), "Chi tiêu", "Thống kê");

        JFreeChart barChart = ChartFactory.createBarChart(
            "Biểu đồ Thống kê",
            "Danh mục",
            "Số tiền (VNĐ)",
            dataset
        );

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(500, 300));
        return chartPanel;
    }
}