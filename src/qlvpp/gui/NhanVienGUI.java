package qlvpp.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import qlvpp.bus.NhanVienBUS;
import qlvpp.model.NhanVien;

public class NhanVienGUI extends JPanel {
    private NhanVienBUS nhanVienBUS;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaNV, txtHo, txtTen, txtDiaChi, txtLuongThang;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem;

    public NhanVienGUI() {
        nhanVienBUS = new NhanVienBUS();
        initComponents();
        loadNhanVienList();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Mã Nhân Viên:"));
        txtMaNV = new JTextField();
        inputPanel.add(txtMaNV);

        inputPanel.add(new JLabel("Họ:"));
        txtHo = new JTextField();
        inputPanel.add(txtHo);

        inputPanel.add(new JLabel("Tên:"));
        txtTen = new JTextField();
        inputPanel.add(txtTen);

        inputPanel.add(new JLabel("Địa Chỉ:"));
        txtDiaChi = new JTextField();
        inputPanel.add(txtDiaChi);

        inputPanel.add(new JLabel("Lương Tháng:"));
        txtLuongThang = new JTextField();
        inputPanel.add(txtLuongThang);

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
        String[] columns = {"Mã NV", "Họ", "Tên", "Địa Chỉ", "Lương Tháng"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add components
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        btnThem.addActionListener(e -> themNhanVien());
        btnSua.addActionListener(e -> suaNhanVien());
        btnXoa.addActionListener(e -> xoaNhanVien());
        btnTimKiem.addActionListener(e -> timKiemNhanVien());
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtMaNV.setText(tableModel.getValueAt(row, 0).toString());
                txtHo.setText(tableModel.getValueAt(row, 1).toString());
                txtTen.setText(tableModel.getValueAt(row, 2).toString());
                txtDiaChi.setText(tableModel.getValueAt(row, 3).toString());
                txtLuongThang.setText(tableModel.getValueAt(row, 4).toString());
            }
        });
    }

    private void loadNhanVienList() {
        tableModel.setRowCount(0);
        List<NhanVien> nhanVienList = nhanVienBUS.getAllNhanVien();
        for (NhanVien nv : nhanVienList) {
            tableModel.addRow(new Object[]{nv.getMaNV(), nv.getHo(), nv.getTen(), nv.getDiaChi(), nv.getLuongThang()});
        }
    }

    private void themNhanVien() {
        try {
            NhanVien nv = new NhanVien(
                Integer.parseInt(txtMaNV.getText()),
                txtHo.getText(),
                txtTen.getText(),
                txtDiaChi.getText(),
                Double.parseDouble(txtLuongThang.getText())
            );
            if (nhanVienBUS.addNhanVien(nv)) {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
                loadNhanVienList();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho Mã NV và Lương Tháng!");
        }
    }

    private void suaNhanVien() {
        try {
            NhanVien nv = new NhanVien(
                Integer.parseInt(txtMaNV.getText()),
                txtHo.getText(),
                txtTen.getText(),
                txtDiaChi.getText(),
                Double.parseDouble(txtLuongThang.getText())
            );
            if (nhanVienBUS.updateNhanVien(nv)) {
                JOptionPane.showMessageDialog(this, "Sửa nhân viên thành công!");
                loadNhanVienList();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa nhân viên thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho Mã NV và Lương Tháng!");
        }
    }

    private void xoaNhanVien() {
        try {
            int maNV = Integer.parseInt(txtMaNV.getText());
            if (nhanVienBUS.deleteNhanVien(maNV)) {
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
                loadNhanVienList();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho Mã NV!");
        }
    }

    private void timKiemNhanVien() {
        String search = txtTen.getText();
        tableModel.setRowCount(0);
        List<NhanVien> nhanVienList = nhanVienBUS.searchNhanVien(search);
        for (NhanVien nv : nhanVienList) {
            tableModel.addRow(new Object[]{nv.getMaNV(), nv.getHo(), nv.getTen(), nv.getDiaChi(), nv.getLuongThang()});
        }
    }

    private void clearFields() {
        txtMaNV.setText("");
        txtHo.setText("");
        txtTen.setText("");
        txtDiaChi.setText("");
        txtLuongThang.setText("");
    }
}