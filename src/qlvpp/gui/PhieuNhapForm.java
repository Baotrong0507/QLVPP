package qlvpp.gui;

import qlvpp.model.PhieuNhap;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class PhieuNhapForm extends JDialog {
    private JTextField txtMaPN, txtMaNV, txtMaNCC, txtNgayNhap, txtTongTien;
    private JButton btnLuu;
    private PhieuNhap phieuNhap;

    public PhieuNhapForm(Frame owner, String title, PhieuNhap pn) {
        super(owner, title, true);
        this.phieuNhap = pn;

        setLayout(new GridLayout(6, 2, 5, 5));
        setSize(300, 300);
        setLocationRelativeTo(null);

        txtMaPN = new JTextField(pn != null ? String.valueOf(pn.getMaPN()) : "");
        txtMaNV = new JTextField(pn != null ? String.valueOf(pn.getMaNV()) : "");
        txtMaNCC = new JTextField(pn != null ? String.valueOf(pn.getMaNCC()) : "");
        txtNgayNhap = new JTextField(pn != null ? pn.getNgayNhap().toString() : "");
        txtTongTien = new JTextField(pn != null ? String.valueOf(pn.getTongTien()) : "");
        btnLuu = new JButton("Lưu");

        add(new JLabel("Mã PN:"));
        add(txtMaPN);
        add(new JLabel("Mã NV:"));
        add(txtMaNV);
        add(new JLabel("Mã NCC:"));
        add(txtMaNCC);
        add(new JLabel("Ngày nhập (yyyy-mm-dd):"));
        add(txtNgayNhap);
        add(new JLabel("Tổng tiền:"));
        add(txtTongTien);
        add(new JLabel());
        add(btnLuu);

        btnLuu.addActionListener(e -> {
            try {
                int maPN = Integer.parseInt(txtMaPN.getText());
                int maNV = Integer.parseInt(txtMaNV.getText());
                int maNCC = Integer.parseInt(txtMaNCC.getText());
                Date ngayNhap = Date.valueOf(txtNgayNhap.getText());
                double tongTien = Double.parseDouble(txtTongTien.getText());

                phieuNhap = new PhieuNhap(maPN, maNV, maNCC, ngayNhap, tongTien);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi định dạng dữ liệu!");
            }
        });
    }

    public PhieuNhap getPhieuNhap() {
        return phieuNhap;
    }
}