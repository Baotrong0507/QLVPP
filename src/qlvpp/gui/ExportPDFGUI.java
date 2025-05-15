package qlvpp.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import qlvpp.bus.HoaDonBUS;
import qlvpp.model.HoaDon; // Thêm import cho HoaDon

public class ExportPDFGUI extends JPanel {

    private JComboBox<String> cbDocumentType;
    private JTextField txtMaPN;
    private JButton btnExportPDF;
    private HoaDonBUS hoaDonBUS; // Thêm để lấy dữ liệu hóa đơn
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"); // Định dạng thời gian

    public ExportPDFGUI() {
        hoaDonBUS = new HoaDonBUS(); // Khởi tạo HoaDonBUS
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        // Tiêu đề
        JLabel lblTitle = new JLabel("XUẤT FILE PDF", SwingConstants.CENTER);
        lblTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 24));
        lblTitle.setForeground(new java.awt.Color(20, 20, 70));
        add(lblTitle, BorderLayout.NORTH);

        // Panel chính
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Chọn loại tài liệu
        JLabel lblDocumentType = new JLabel("Loại tài liệu:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(lblDocumentType, gbc);

        String[] documentTypes = {"Phiếu nhập", "Hóa đơn"};
        cbDocumentType = new JComboBox<>(documentTypes);
        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(cbDocumentType, gbc);

        // Nhập mã phiếu nhập
        JLabel lblMaPN = new JLabel("Mã phiếu nhập/Hóa đơn:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(lblMaPN, gbc);

        txtMaPN = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(txtMaPN, gbc);

        // Nút Xuất PDF
        btnExportPDF = new JButton("Xuất PDF");
        btnExportPDF.setBackground(new java.awt.Color(0, 120, 215));
        btnExportPDF.setForeground(Color.WHITE);
        btnExportPDF.setFocusPainted(false);
        btnExportPDF.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
        btnExportPDF.addActionListener(e -> exportPDF());
        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(btnExportPDF, gbc);

        add(inputPanel, BorderLayout.CENTER);
    }

    private void exportPDF() {
        String documentType = (String) cbDocumentType.getSelectedItem();
        String maPN = txtMaPN.getText().trim();

        if (maPN.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phiếu nhập hoặc hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Tạo tên file với thời gian hiện tại
        String timestamp = LocalDateTime.now().format(timeFormatter); // Ví dụ: 20250515_100000
        String fileName = "exports/" + documentType.replace(" ", "") + "_" + maPN + "_" + timestamp + ".pdf";
        File pdfFile = new File(fileName); // Thư mục exports (tạo nếu chưa tồn tại)
        pdfFile.getParentFile().mkdirs(); // Tạo thư mục nếu không tồn tại

        try {
            // Xuất PDF dựa trên loại tài liệu
            if ("Hóa đơn".equals(documentType)) {
                // Lấy dữ liệu hóa đơn từ HoaDonBUS
                var hoaDonList = hoaDonBUS.timKiemHoaDon(maPN);
                if (hoaDonList.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn với mã: " + maPN, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                HoaDon hd = hoaDonList.get(0); // Lấy hóa đơn đầu tiên (giả sử mã duy nhất)
                qlvpp.file.PDFExporter.exportPDF(documentType, maPN, hd); // Truyền thêm dữ liệu hóa đơn
            } else {
                qlvpp.file.PDFExporter.exportPDF(documentType, maPN); // Xử lý phiếu nhập
            }

            JOptionPane.showMessageDialog(this, "Xuất PDF thành công! File: " + pdfFile.getAbsolutePath(), "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}