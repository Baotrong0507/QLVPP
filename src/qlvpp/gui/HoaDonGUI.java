package qlvpp.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import qlvpp.bus.HoaDonBUS;
import qlvpp.model.HoaDon;

public class HoaDonGUI extends JPanel {
    private HoaDonBUS hoaDonBUS;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaHD, txtMaKH, txtMaNV, txtNgayLap, txtTongTien;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public HoaDonGUI() {
        hoaDonBUS = new HoaDonBUS();
        initComponents();
        loadHoaDonList();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Mã Hóa Đơn:"));
        txtMaHD = new JTextField();
        inputPanel.add(txtMaHD);

        inputPanel.add(new JLabel("Mã Khách Hàng:"));
        txtMaKH = new JTextField();
        inputPanel.add(txtMaKH);

        inputPanel.add(new JLabel("Mã Nhân Viên:"));
        txtMaNV = new JTextField();
        inputPanel.add(txtMaNV);

        inputPanel.add(new JLabel("Ngày Lập (dd/MM/yyyy):"));
        txtNgayLap = new JTextField();
        inputPanel.add(txtNgayLap);

        inputPanel.add(new JLabel("Tổng Tiền:"));
        txtTongTien = new JTextField();
        inputPanel.add(txtTongTien);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnTimKiem = new JButton("Tìm Kiếm");
        
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnTimKiem);

        // Table
        String[] columns = {"Mã HD", "Mã KH", "Mã NV", "Ngày Lập", "Tổng Tiền"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add components
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        btnThem.addActionListener(e -> themHoaDon());
        btnSua.addActionListener(e -> suaHoaDon());
        btnXoa.addActionListener(e -> xoaHoaDon());
        btnTimKiem.addActionListener(e -> timKiemHoaDon());
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtMaHD.setText(tableModel.getValueAt(row, 0).toString());
                txtMaKH.setText(tableModel.getValueAt(row, 1).toString());
                txtMaNV.setText(tableModel.getValueAt(row, 2).toString());
                txtNgayLap.setText(tableModel.getValueAt(row, 3).toString());
                txtTongTien.setText(tableModel.getValueAt(row, 4).toString());
            }
        });
    }

    private void loadHoaDonList() {
        tableModel.setRowCount(0);
        List<HoaDon> hoaDonList = hoaDonBUS.getAllHoaDon();
        for (HoaDon hd : hoaDonList) {
            // Chuyển java.sql.Date thành LocalDate để định dạng
            LocalDate ngayLap = hd.getNgayLap().toLocalDate();
            tableModel.addRow(new Object[]{
                hd.getMaHD(),
                hd.getMaKH(),
                hd.getMaNV(),
                ngayLap.format(formatter), // Định dạng ngày để hiển thị
                hd.getTongTien()
            });
        }
    }

    private void themHoaDon() {
        try {
            // Parse chuỗi ngày thành LocalDate, sau đó chuyển thành java.sql.Date
            LocalDate localDate = LocalDate.parse(txtNgayLap.getText(), formatter);
            Date ngayLap = Date.valueOf(localDate);

            HoaDon hd = new HoaDon(
                Integer.parseInt(txtMaHD.getText()),
                Integer.parseInt(txtMaKH.getText()),
                Integer.parseInt(txtMaNV.getText()),
                ngayLap,
                Double.parseDouble(txtTongTien.getText())
            );
            if (hoaDonBUS.addHoaDon(hd)) {
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công!");
                loadHoaDonList();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm hóa đơn thất bại!");
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày theo định dạng dd/MM/yyyy!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho Mã HD, Mã KH, Mã NV và Tổng Tiền!");
        }
    }

    private void suaHoaDon() {
        try {
            // Parse chuỗi ngày thành LocalDate, sau đó chuyển thành java.sql.Date
            LocalDate localDate = LocalDate.parse(txtNgayLap.getText(), formatter);
            Date ngayLap = Date.valueOf(localDate);

            HoaDon hd = new HoaDon(
                Integer.parseInt(txtMaHD.getText()),
                Integer.parseInt(txtMaKH.getText()),
                Integer.parseInt(txtMaNV.getText()),
                ngayLap,
                Double.parseDouble(txtTongTien.getText())
            );
            if (hoaDonBUS.updateHoaDon(hd)) {
                JOptionPane.showMessageDialog(this, "Sửa hóa đơn thành công!");
                loadHoaDonList();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa hóa đơn thất bại!");
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày theo định dạng dd/MM/yyyy!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho Mã HD, Mã KH, Mã NV và Tổng Tiền!");
        }
    }

    private void xoaHoaDon() {
        try {
            int maHD = Integer.parseInt(txtMaHD.getText());
            if (hoaDonBUS.deleteHoaDon(maHD)) {
                JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công!");
                loadHoaDonList();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa hóa đơn thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho Mã HD!");
        }
    }

    private void timKiemHoaDon() {
        String search = txtMaHD.getText();
        tableModel.setRowCount(0);
        List<HoaDon> hoaDonList = hoaDonBUS.searchHoaDon(search);
        for (HoaDon hd : hoaDonList) {
            LocalDate ngayLap = hd.getNgayLap().toLocalDate();
            tableModel.addRow(new Object[]{
                hd.getMaHD(),
                hd.getMaKH(),
                hd.getMaNV(),
                ngayLap.format(formatter),
                hd.getTongTien()
            });
        }
    }

    private void clearFields() {
        txtMaHD.setText("");
        txtMaKH.setText("");
        txtMaNV.setText("");
        txtNgayLap.setText("");
        txtTongTien.setText("");
    }
}