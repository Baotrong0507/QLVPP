package qlvpp.gui;

import qlvpp.model.PhieuNhap;
import qlvpp.model.ChiTietPhieuNhap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.ArrayList;

public class ChiTietPhieuNhapForm extends JDialog {
    private JTextField txtMaPN, txtMaNV, txtMaNCC, txtNgayNhap, txtTongTien;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnThemDong, btnXoaDong, btnLuu;
    private PhieuNhap phieuNhap;
    private ArrayList<ChiTietPhieuNhap> chiTietList = new ArrayList<>();

    public ChiTietPhieuNhapForm(Frame owner, String title, PhieuNhap pn) {
        super(owner, title, true);
        this.phieuNhap = pn;

        setLayout(new BorderLayout());
        setSize(700, 500);
        setLocationRelativeTo(null);

        JPanel infoPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        txtMaPN = new JTextField(pn != null ? String.valueOf(pn.getMaPN()) : "");
        txtMaNV = new JTextField(pn != null ? String.valueOf(pn.getMaNV()) : "");
        txtMaNCC = new JTextField(pn != null ? String.valueOf(pn.getMaNCC()) : "");
        txtNgayNhap = new JTextField(pn != null ? pn.getNgayNhap().toString() : "");
        txtTongTien = new JTextField("0");
        txtTongTien.setEditable(false);

        infoPanel.add(new JLabel("Mã PN:"));
        infoPanel.add(txtMaPN);
        infoPanel.add(new JLabel("Mã NV:"));
        infoPanel.add(txtMaNV);
        infoPanel.add(new JLabel("Mã NCC:"));
        infoPanel.add(txtMaNCC);
        infoPanel.add(new JLabel("Ngày nhập (yyyy-mm-dd):"));
        infoPanel.add(txtNgayNhap);
        infoPanel.add(new JLabel("Tổng tiền:"));
        infoPanel.add(txtTongTien);

        add(infoPanel, BorderLayout.NORTH);

        String[] columnNames = {"Mã SP", "Số lượng", "Đơn giá", "Thành tiền"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col != 3; // chỉ không cho sửa "Thành tiền"
            }
        };

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnThemDong = new JButton("Thêm dòng");
        btnXoaDong = new JButton("Xóa dòng");
        btnLuu = new JButton("Lưu");
        buttonPanel.add(btnThemDong);
        buttonPanel.add(btnXoaDong);
        buttonPanel.add(btnLuu);
        add(buttonPanel, BorderLayout.SOUTH);

        btnThemDong.addActionListener(e -> {
            model.addRow(new Object[]{"", "", "", ""});
        });

        btnXoaDong.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                model.removeRow(row);
                tinhTongTien();
            }
        });

        // Cập nhật thành tiền khi sửa số lượng hoặc đơn giá
model.addTableModelListener(e -> {
    int row = e.getFirstRow();
    if (row >= 0 && model.getRowCount() > row) {
        try {
            int soLuong = Integer.parseInt(model.getValueAt(row, 1).toString());
            double donGia = Double.parseDouble(model.getValueAt(row, 2).toString());
            double thanhTien = soLuong * donGia;

            Object oldValue = model.getValueAt(row, 3);
            if (oldValue == null || !(oldValue instanceof Number) || ((Number) oldValue).doubleValue() != thanhTien) {
                model.setValueAt(thanhTien, row, 3);
            }

            tinhTongTien();
        } catch (Exception ex) {
            Object oldValue = model.getValueAt(row, 3);
            if (oldValue == null || !(oldValue instanceof Number) || ((Number) oldValue).doubleValue() != 0.0) {
                model.setValueAt(0.0, row, 3);
            }
            tinhTongTien();
        }
    }
});


        btnLuu.addActionListener(e -> {
            try {
                int maPN = Integer.parseInt(txtMaPN.getText());
                int maNV = Integer.parseInt(txtMaNV.getText());
                int maNCC = Integer.parseInt(txtMaNCC.getText());
                Date ngayNhap = Date.valueOf(txtNgayNhap.getText());
                double tongTien = Double.parseDouble(txtTongTien.getText());

                phieuNhap = new PhieuNhap(maPN, maNV, maNCC, ngayNhap, tongTien);

                chiTietList.clear();
                for (int i = 0; i < model.getRowCount(); i++) {
                    int maSP = Integer.parseInt(model.getValueAt(i, 0).toString());
                    int soLuong = Integer.parseInt(model.getValueAt(i, 1).toString());
                    double donGia = Double.parseDouble(model.getValueAt(i, 2).toString());
                    double thanhTien = Double.parseDouble(model.getValueAt(i, 3).toString());
                    chiTietList.add(new ChiTietPhieuNhap(maPN, maSP, soLuong, donGia, thanhTien));
                }

                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi định dạng dữ liệu!\n" + ex.getMessage());
            }
        });
    }

    public PhieuNhap getPhieuNhap() {
        return phieuNhap;
    }

    public ArrayList<ChiTietPhieuNhap> getChiTietList() {
        return chiTietList;
    }

    private void tinhTongTien() {
        double tong = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            try {
                tong += Double.parseDouble(model.getValueAt(i, 3).toString());
            } catch (Exception ignored) {}
        }
        txtTongTien.setText(String.valueOf(tong));
    }
}
