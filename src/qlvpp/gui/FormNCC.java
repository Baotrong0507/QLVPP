package qlvpp.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import qlvpp.bus.NhaCungCapBUS;
import qlvpp.model.NhaCungCap;

public class FormNCC extends JDialog {
    private JTextField txtMa, txtTen, txtDiaChi, txtSdt;
    private NhaCungCapGUI parent;
    private String action;
    private NhaCungCap existing;
    private NhaCungCapBUS bus = new NhaCungCapBUS();

    public FormNCC(NhaCungCapGUI parent, String action, NhaCungCap ncc) {
        this.parent = parent;
        this.action = action;
        this.existing = ncc;

        setTitle(action + " Nhà Cung Cấp");
        setSize(300, 300);
        setLocationRelativeTo(parent);
        setModal(true);
        setLayout(new GridLayout(5, 2, 5, 5));

        txtMa = new JTextField();
        txtTen = new JTextField();
        txtDiaChi = new JTextField();
        txtSdt = new JTextField();

        add(new JLabel("Mã NCC:"));
        add(txtMa);
        add(new JLabel("Tên NCC:"));
        add(txtTen);
        add(new JLabel("Địa chỉ:"));
        add(txtDiaChi);
        add(new JLabel("SĐT:"));
        add(txtSdt);

        JButton btnLuu = new JButton("Lưu");
        add(new JLabel());
        add(btnLuu);

        if (action.equals("Sửa") && ncc != null) {
            txtMa.setText(String.valueOf(ncc.getMaNCC()));
            txtMa.setEditable(false); // Không cho sửa mã
            txtTen.setText(ncc.getTenNCC());
            txtDiaChi.setText(ncc.getDiaChi());
            txtSdt.setText(ncc.getSdt());
        }

        btnLuu.addActionListener(e -> {
            try {
                int ma = Integer.parseInt(txtMa.getText().trim());
                String ten = txtTen.getText().trim();
                String diaChi = txtDiaChi.getText().trim();
                String sdt = txtSdt.getText().trim();

                NhaCungCap newNCC = new NhaCungCap(ma, ten, diaChi, sdt);

                if (action.equals("Thêm")) {
                    if (!bus.addNCC(newNCC)) {
                        JOptionPane.showMessageDialog(this, "Mã nhà cung cấp đã tồn tại!");
                        return;
                    }
                } else {
                    bus.updateNCC(newNCC);
                }

                parent.loadData(bus.getAllNCC());
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        });
    }
}
