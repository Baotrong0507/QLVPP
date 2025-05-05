package qlvpp.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import qlvpp.bus.KhachHangBUS;
import qlvpp.model.KhachHang;

public class KhachHangGUI extends JPanel {
    private KhachHangBUS khachHangBUS;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaKH, txtHo, txtTen, txtDiaChi, txtDienThoai;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem;

    public KhachHangGUI() {
        khachHangBUS = new KhachHangBUS();
        initComponents();
        loadKhachHangList();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Mã Khách Hàng:"));
        txtMaKH = new JTextField();
        inputPanel.add(txtMaKH);

        inputPanel.add(new JLabel("Họ:"));
        txtHo = new JTextField();
        inputPanel.add(txtHo);

        inputPanel.add(new JLabel("Tên:"));
        txtTen = new JTextField();
        inputPanel.add(txtTen);

        inputPanel.add(new JLabel("Địa Chỉ:"));
        txtDiaChi = new JTextField();
        inputPanel.add(txtDiaChi);

        inputPanel.add(new JLabel("Điện Thoại:"));
        txtDienThoai = new JTextField();
        inputPanel.add(txtDienThoai);

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
        String[] columns = {"Mã KH", "Họ", "Tên", "Địa Chỉ", "Điện Thoại"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add components
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        btnThem.addActionListener(e -> themKhachHang());
        btnSua.addActionListener(e -> suaKhachHang());
        btnXoa.addActionListener(e -> xoaKhachHang());
        btnTimKiem.addActionListener(e -> timKiemKhachHang());
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtMaKH.setText(tableModel.getValueAt(row, 0).toString());
                txtHo.setText(tableModel.getValueAt(row, 1).toString());
                txtTen.setText(tableModel.getValueAt(row, 2).toString());
                txtDiaChi.setText(tableModel.getValueAt(row, 3).toString());
                txtDienThoai.setText(tableModel.getValueAt(row, 4).toString());
            }
        });
    }

    private void loadKhachHangList() {
        tableModel.setRowCount(0);
        List<KhachHang> khachHangList = khachHangBUS.getAllKhachHang();
        for (KhachHang kh : khachHangList) {
            tableModel.addRow(new Object[]{kh.getMaKH(), kh.getHo(), kh.getTen(), kh.getDiaChi(), kh.getDienThoai()});
        }
    }

    private void themKhachHang() {
        try {
            KhachHang kh = new KhachHang(
                Integer.parseInt(txtMaKH.getText()),
                txtHo.getText(),
                txtTen.getText(),
                txtDiaChi.getText(),
                txtDienThoai.getText()
            );
            if (khachHangBUS.addKhachHang(kh)) {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
                loadKhachHangList();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho Mã KH!");
        }
    }

    private void suaKhachHang() {
        try {
            KhachHang kh = new KhachHang(
                Integer.parseInt(txtMaKH.getText()),
                txtHo.getText(),
                txtTen.getText(),
                txtDiaChi.getText(),
                txtDienThoai.getText()
            );
            if (khachHangBUS.updateKhachHang(kh)) {
                JOptionPane.showMessageDialog(this, "Sửa khách hàng thành công!");
                loadKhachHangList();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa khách hàng thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho Mã KH!");
        }
    }

    private void xoaKhachHang() {
        try {
            int maKH = Integer.parseInt(txtMaKH.getText());
            if (khachHangBUS.deleteKhachHang(maKH)) {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
                loadKhachHangList();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho Mã KH!");
        }
    }

    private void timKiemKhachHang() {
        String search = txtTen.getText();
        tableModel.setRowCount(0);
        List<KhachHang> khachHangList = khachHangBUS.searchKhachHang(search);
        for (KhachHang kh : khachHangList) {
            tableModel.addRow(new Object[]{kh.getMaKH(), kh.getHo(), kh.getTen(), kh.getDiaChi(), kh.getDienThoai()});
        }
    }

    private void clearFields() {
        txtMaKH.setText("");
        txtHo.setText("");
        txtTen.setText("");
        txtDiaChi.setText("");
        txtDienThoai.setText("");
    }
}