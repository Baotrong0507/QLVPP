
package qlvpp.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import qlvpp.connections.Myconnections;
import qlvpp.model.ChiTietPhieuNhap;
import qlvpp.model.NhaCungCap;
import qlvpp.model.PhieuNhap;
import java.sql.Date;

public class ExcelGUI extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtFileName;
    private List<Object> dataList; // Danh sách tạm cho các loại dữ liệu

    public ExcelGUI() {
        dataList = new ArrayList<>();
        setLayout(new BorderLayout());
        setBackground(new java.awt.Color(255, 255, 255));

        // Tiêu đề
        JLabel lblTitle = new JLabel("XỬ LÝ EXCEL", SwingConstants.CENTER);
        lblTitle.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // Panel nhập tên file và các nút
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(new java.awt.Color(255, 255, 255));

        JLabel lblFileName = new JLabel("Tên file Excel:");
        inputPanel.add(lblFileName);

        txtFileName = new JTextField(20);
        inputPanel.add(txtFileName);

        JButton btnChooseFile = new JButton("Chọn File");
        btnChooseFile.setBackground(new java.awt.Color(0, 120, 215));
        btnChooseFile.setForeground(new java.awt.Color(255, 255, 255));
        btnChooseFile.setFocusPainted(false);
        btnChooseFile.addActionListener(e -> chooseFile());
        inputPanel.add(btnChooseFile);

        JButton btnPreview = new JButton("Xem trước");
        btnPreview.setBackground(new java.awt.Color(0, 120, 215));
        btnPreview.setForeground(new java.awt.Color(255, 255, 255));
        btnPreview.setFocusPainted(false);
        btnPreview.addActionListener(e -> previewData());
        inputPanel.add(btnPreview);

        JButton btnLoad = new JButton("Tải Dữ Liệu");
        btnLoad.setBackground(new java.awt.Color(0, 120, 215));
        btnLoad.setForeground(new java.awt.Color(255, 255, 255));
        btnLoad.setFocusPainted(false);
        btnLoad.addActionListener(e -> loadDataToList());
        inputPanel.add(btnLoad);

        JButton btnSave = new JButton("Lưu");
        btnSave.setBackground(new java.awt.Color(0, 120, 215));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setFocusPainted(false);
        btnSave.addActionListener(e -> saveToDatabase());
        inputPanel.add(btnSave);

        add(inputPanel, BorderLayout.SOUTH);

        // Bảng hiển thị dữ liệu
        tableModel = new DefaultTableModel(new Object[]{"Dữ liệu"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Chọn file Excel qua JFileChooser
    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".xlsx") || f.getName().toLowerCase().endsWith(".xls");
            }
            @Override
            public String getDescription() {
                return "Excel Files (*.xls, *.xlsx)";
            }
        });

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            txtFileName.setText(selectedFile.getAbsolutePath());
        }
    }

    // Đọc và hiển thị dữ liệu từ file Excel
    private void previewData() {
        String fileName = new File(txtFileName.getText().trim()).getName();
        String tableName = fileName.substring(0, fileName.lastIndexOf(".")).toLowerCase();
        if (!tableName.equals("nhacungcap") && !tableName.equals("phieunhap") && !tableName.equals("chitietphieunhap")) {
            JOptionPane.showMessageDialog(this, "Tên file phải là NhaCungCap, PhieuNhap hoặc ChiTietPhieuNhap!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File file = new File(txtFileName.getText().trim());
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "File không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            tableModel.setRowCount(0);

            Iterator<Row> rowIterator = sheet.iterator();
            if (!rowIterator.hasNext()) {
                JOptionPane.showMessageDialog(this, "File Excel trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Row headerRow = rowIterator.next(); // Lấy header để xác định cột
            String[] headers = new String[sheet.getRow(0).getPhysicalNumberOfCells()];
            for (int i = 0; i < headers.length; i++) {
                headers[i] = headerRow.getCell(i).getStringCellValue().toLowerCase();
            }

            tableModel.setColumnIdentifiers(headers);

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Object[] rowData = new Object[headers.length];
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                rowData[i] = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    rowData[i] = cell.getDateCellValue();
                                } else {
                                    rowData[i] = cell.getNumericCellValue();
                                }
                                break;
                            default:
                                rowData[i] = "";
                        }
                    } else {
                        rowData[i] = "";
                    }
                }
                tableModel.addRow(rowData);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi đọc file: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Lưu dữ liệu từ bảng vào danh sách tạm
    private void loadDataToList() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để tải!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dataList.clear();
        String fileName = new File(txtFileName.getText().trim()).getName().toLowerCase();
        String tableName = fileName.substring(0, fileName.lastIndexOf("."));

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            switch (tableName) {
                case "nhacungcap":
                    int maNCC = (int) tableModel.getValueAt(i, 0);
                    String tenNCC = (String) tableModel.getValueAt(i, 1);
                    String diaChi = (String) tableModel.getValueAt(i, 2);
                    String sdt = (String) tableModel.getValueAt(i, 3);
                    dataList.add(new NhaCungCap(maNCC, tenNCC, diaChi, sdt));
                    break;
                case "phieunhap":
                    int maPN = (int) tableModel.getValueAt(i, 0);
                    int maNV = (int) tableModel.getValueAt(i, 1);
                    int maNCCPN = (int) tableModel.getValueAt(i, 2);
                    Date ngayNhap = (Date) tableModel.getValueAt(i, 3);
                    double tongTien = (double) tableModel.getValueAt(i, 4);
                    dataList.add(new PhieuNhap(maPN, maNV, maNCCPN, ngayNhap, tongTien));
                    break;
                case "chitietphieunhap":
                    int maPNCT = (int) tableModel.getValueAt(i, 0);
                    int maSP = (int) tableModel.getValueAt(i, 1);
                    int soLuong = (int) tableModel.getValueAt(i, 2);
                    double giaNhap = (double) tableModel.getValueAt(i, 3);
                    double thanhTien = (double) tableModel.getValueAt(i, 4);
                    dataList.add(new ChiTietPhieuNhap(maPNCT, maSP, soLuong, giaNhap, thanhTien));
                    break;
            }
        }

        JOptionPane.showMessageDialog(this, "Dữ liệu đã được tải vào danh sách tạm!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    // Lưu dữ liệu từ danh sách tạm vào SQL
    private void saveToDatabase() {
        if (dataList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Danh sách tạm trống! Vui lòng tải dữ liệu trước.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = Myconnections.getConnection()) {
            int successCount = 0;
            String fileName = new File(txtFileName.getText().trim()).getName().toLowerCase();
            String tableName = fileName.substring(0, fileName.lastIndexOf("."));

            for (Object obj : dataList) {
                switch (tableName) {
                    case "nhacungcap":
                        NhaCungCap ncc = (NhaCungCap) obj;
                        String checkNCC = "SELECT COUNT(*) FROM NhaCungCap WHERE MaNCC = ?";
                        String insertNCC = "INSERT INTO NhaCungCap (MaNCC, TenNCC, DiaChi, sdt) VALUES (?, ?, ?, ?)";
                        PreparedStatement checkNCCStmt = conn.prepareStatement(checkNCC);
                        checkNCCStmt.setInt(1, ncc.getMaNCC());
                        ResultSet rsNCC = checkNCCStmt.executeQuery();
                        rsNCC.next();
                        if (rsNCC.getInt(1) > 0) {
                            JOptionPane.showMessageDialog(this, "Mã NCC " + ncc.getMaNCC() + " đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            continue;
                        }
                        PreparedStatement insertNCCStmt = conn.prepareStatement(insertNCC);
                        insertNCCStmt.setInt(1, ncc.getMaNCC());
                        insertNCCStmt.setString(2, ncc.getTenNCC());
                        insertNCCStmt.setString(3, ncc.getDiaChi());
                        insertNCCStmt.setString(4, ncc.getSdt());
                        insertNCCStmt.executeUpdate();
                        successCount++;
                        break;
                    case "phieunhap":
                        PhieuNhap pn = (PhieuNhap) obj;
                        String checkPN = "SELECT COUNT(*) FROM PhieuNhap WHERE MaPN = ?";
                        String insertPN = "INSERT INTO PhieuNhap (MaPN, MaNV, MaNCC, NgayNhap, TongTien) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement checkPNStmt = conn.prepareStatement(checkPN);
                        checkPNStmt.setInt(1, pn.getMaPN());
                        ResultSet rsPN = checkPNStmt.executeQuery();
                        rsPN.next();
                        if (rsPN.getInt(1) > 0) {
                            JOptionPane.showMessageDialog(this, "Mã PN " + pn.getMaPN() + " đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            continue;
                        }
                        PreparedStatement insertPNStmt = conn.prepareStatement(insertPN);
                        insertPNStmt.setInt(1, pn.getMaPN());
                        insertPNStmt.setInt(2, pn.getMaNV());
                        insertPNStmt.setInt(3, pn.getMaNCC());
                        insertPNStmt.setDate(4, new java.sql.Date(pn.getNgayNhap().getTime()));
                        insertPNStmt.setDouble(5, pn.getTongTien());
                        insertPNStmt.executeUpdate();
                        successCount++;
                        break;
                    case "chitietphieunhap":
                        ChiTietPhieuNhap ctpn = (ChiTietPhieuNhap) obj;
                        String checkCTPN = "SELECT COUNT(*) FROM ChiTietPhieuNhap WHERE MaPN = ? AND MaSP = ?";
                        String insertCTPN = "INSERT INTO ChiTietPhieuNhap (MaPN, MaSP, SoLuong, GiaNhap, ThanhTien) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement checkCTPNStmt = conn.prepareStatement(checkCTPN);
                        checkCTPNStmt.setInt(1, ctpn.getMaPN());
                        checkCTPNStmt.setInt(2, ctpn.getMaSP());
                        ResultSet rsCTPN = checkCTPNStmt.executeQuery();
                        rsCTPN.next();
                        if (rsCTPN.getInt(1) > 0) {
                            JOptionPane.showMessageDialog(this, "Mã PN " + ctpn.getMaPN() + " và Mã SP " + ctpn.getMaSP() + " đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            continue;
                        }
                        PreparedStatement insertCTPNStmt = conn.prepareStatement(insertCTPN);
                        insertCTPNStmt.setInt(1, ctpn.getMaPN());
                        insertCTPNStmt.setInt(2, ctpn.getMaSP());
                        insertCTPNStmt.setInt(3, ctpn.getSoLuong());
                        insertCTPNStmt.setDouble(4, ctpn.getGiaNhap());
                        insertCTPNStmt.setDouble(5, ctpn.getThanhTien());
                        insertCTPNStmt.executeUpdate();
                        successCount++;
                        break;
                }
            }

            JOptionPane.showMessageDialog(this, "Đã lưu " + successCount + "/" + dataList.size() + " bản ghi thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            dataList.clear();
            tableModel.setRowCount(0);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu vào cơ sở dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}

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

