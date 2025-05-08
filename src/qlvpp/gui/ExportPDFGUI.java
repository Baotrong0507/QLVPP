package qlvpp.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import qlvpp.file.PDFExporter;

public class ExportPDFGUI extends JPanel {

    private JComboBox<String> cbDocumentType;
    private JTextField txtMaPN;
    private JButton btnExportPDF;

    public ExportPDFGUI() {
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
        JLabel lblMaPN = new JLabel("Mã phiếu nhập:");
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
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phiếu nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            File pdfFile = PDFExporter.exportPDF(documentType, maPN);
            JOptionPane.showMessageDialog(this, "Xuất PDF thành công! File: " + pdfFile.getAbsolutePath(), "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}