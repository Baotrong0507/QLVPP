/*package qlvpp.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import qlvpp.model.PhieuNhap;
import qlvpp.dao.PhieuNhapDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelGUI extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private PhieuNhapDAO phieuNhapDAO;
    private List<PhieuNhap> phieuNhapList;

    public ExcelGUI() {
        phieuNhapDAO = new PhieuNhapDAO();
        phieuNhapList = new ArrayList<>();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Tiêu đề
        JLabel lblTitle = new JLabel("Xử lý Dữ liệu Excel", SwingConstants.CENTER);
        lblTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        lblTitle.setForeground(new Color(20, 20, 70));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitle, BorderLayout.NORTH);

        // Bảng dữ liệu
        String[] columnNames = {"MaPN", "MaNV", "MaNCC", "NgayNhap", "TongTien"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnImport = new JButton("Nhập từ Excel");
        btnImport.setBackground(new Color(0, 120, 215));
        btnImport.setForeground(Color.WHITE);
        btnImport.addActionListener(e -> importFromExcel());
        buttonPanel.add(btnImport);

        JButton btnSaveToSQL = new JButton("Lưu vào SQL");
        btnSaveToSQL.setBackground(new Color(0, 120, 215));
        btnSaveToSQL.setForeground(Color.WHITE);
        btnSaveToSQL.addActionListener(e -> saveToSQL());
        buttonPanel.add(btnSaveToSQL);

        JButton btnLoadFromSQL = new JButton("Lấy từ SQL");
        btnLoadFromSQL.setBackground(new Color(0, 120, 215));
        btnLoadFromSQL.setForeground(Color.WHITE);
        btnLoadFromSQL.addActionListener(e -> loadFromSQL());
        buttonPanel.add(btnLoadFromSQL);

        JButton btnExportToExcel = new JButton("Xuất ra Excel");
        btnExportToExcel.setBackground(new Color(0, 120, 215));
        btnExportToExcel.setForeground(Color.WHITE);
        btnExportToExcel.addActionListener(e -> exportToExcel());
        buttonPanel.add(btnExportToExcel);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void importFromExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel để nhập");
        if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();
        phieuNhapList.clear();
        tableModel.setRowCount(0);

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Lấy tất cả MaPN hiện có trong SQL để kiểm tra trùng lặp
            List<PhieuNhap> existingPhieuNhap = phieuNhapDAO.getAll();
            Set<Integer> existingMaPN = new HashSet<>();
            for (PhieuNhap pn : existingPhieuNhap) {
                existingMaPN.add(pn.getMaPN());
            }

            // Đọc dữ liệu từ Excel
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Bỏ qua hàng tiêu đề

                try {
                    int maPN = (int) row.getCell(0).getNumericCellValue();
                    int maNV = (int) row.getCell(1).getNumericCellValue();
                    int maNCC = (int) row.getCell(2).getNumericCellValue();
                    String ngayNhapStr = row.getCell(3).getStringCellValue();
                    double tongTien = row.getCell(4).getNumericCellValue();

                    // Kiểm tra trùng lặp MaPN
                    if (existingMaPN.contains(maPN)) {
                        JOptionPane.showMessageDialog(this, "MaPN " + maPN + " đã tồn tại trong cơ sở dữ liệu, bỏ qua bản ghi này.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        continue;
                    }

                    // Chuyển đổi ngày
                    LocalDate ngayNhapLocal = LocalDate.parse(ngayNhapStr, formatter);
                    Date ngayNhap = Date.valueOf(ngayNhapLocal);

                    PhieuNhap pn = new PhieuNhap(maPN, maNV, maNCC, ngayNhap, tongTien);
                    phieuNhapList.add(pn);
                    tableModel.addRow(new Object[]{maPN, maNV, maNCC, ngayNhapStr, tongTien});
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi đọc dòng " + (row.getRowNum() + 1) + ": " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
            JOptionPane.showMessageDialog(this, "Đã nhập " + phieuNhapList.size() + " bản ghi từ Excel.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc file Excel: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveToSQL() {
        if (phieuNhapList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để lưu vào SQL.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int successCount = 0;
        for (PhieuNhap pn : phieuNhapList) {
            try {
                phieuNhapDAO.insert(pn);
                successCount++;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu MaPN " + pn.getMaPN() + ": " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        JOptionPane.showMessageDialog(this, "Đã lưu thành công " + successCount + "/" + phieuNhapList.size() + " bản ghi vào SQL.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadFromSQL() {
        phieuNhapList.clear();
        tableModel.setRowCount(0);

        phieuNhapList = phieuNhapDAO.getAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (PhieuNhap pn : phieuNhapList) {
            String ngayNhapStr = pn.getNgayNhap().toLocalDate().format(formatter);
            tableModel.addRow(new Object[]{
                pn.getMaPN(),
                pn.getMaNV(),
                pn.getMaNCC(),
                ngayNhapStr,
                pn.getTongTien()
            });
        }
        JOptionPane.showMessageDialog(this, "Đã tải " + phieuNhapList.size() + " bản ghi từ SQL.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportToExcel() {
        if (phieuNhapList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất ra Excel.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("PhieuNhapExport.xlsx"));
        if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(file)) {
            Sheet sheet = workbook.createSheet("PhieuNhap");

            // Tạo hàng tiêu đề
            Row headerRow = sheet.createRow(0);
            String[] headers = {"MaPN", "MaNV", "MaNCC", "NgayNhap", "TongTien"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Thêm dữ liệu
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (int i = 0; i < phieuNhapList.size(); i++) {
                PhieuNhap pn = phieuNhapList.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(pn.getMaPN());
                row.createCell(1).setCellValue(pn.getMaNV());
                row.createCell(2).setCellValue(pn.getMaNCC());
                row.createCell(3).setCellValue(pn.getNgayNhap().toLocalDate().format(formatter));
                row.createCell(4).setCellValue(pn.getTongTien());
            }

            // Tự động điều chỉnh kích thước cột
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(fos);
            JOptionPane.showMessageDialog(this, "Đã xuất thành công ra file " + file.getAbsolutePath(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}*/