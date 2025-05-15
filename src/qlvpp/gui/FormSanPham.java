package qlvpp.gui;

import qlvpp.model.SanPham;
import qlvpp.bus.SanPhamBUS;

import javax.swing.*;
import java.awt.*;

public class FormSanPham extends JDialog {
    private JTextField txtMaSP, txtTenSP, txtSoLuong, txtDonGia, txtTienTe, txtMaLoai, txtXuatXu;
    private JButton btnLuu, btnHuy;
    private SanPhamGUI parent;
    private String action;
    private SanPham sp;

    public FormSanPham(SanPhamGUI parent, String action, SanPham sp) {
        this.parent = parent;
        this.action = action;
        this.sp = sp;

        setTitle(action + " sản phẩm");
        setModal(true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(8, 2, 5, 5));

        txtMaSP = new JTextField();
        txtTenSP = new JTextField();
        txtSoLuong = new JTextField();
        txtDonGia = new JTextField();
        txtTienTe = new JTextField();
        txtMaLoai = new JTextField();
        txtXuatXu = new JTextField();

        add(new JLabel("Mã SP:"));     add(txtMaSP);
        add(new JLabel("Tên SP:"));    add(txtTenSP);
        add(new JLabel("Số lượng:"));  add(txtSoLuong);
        add(new JLabel("Đơn giá:"));   add(txtDonGia);
        add(new JLabel("Tiền tệ:"));   add(txtTienTe);
        add(new JLabel("Mã loại:"));   add(txtMaLoai);
        add(new JLabel("Xuất xứ:"));   add(txtXuatXu);

        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");
        add(btnLuu);
        add(btnHuy);

        if (action.equals("Sửa") && sp != null) {
            txtMaSP.setText(String.valueOf(sp.getMaSP()));
            txtMaSP.setEditable(false); // Mã SP không sửa
            txtTenSP.setText(sp.getTenSP());
            txtSoLuong.setText(String.valueOf(sp.getSoLuong()));
            txtDonGia.setText(String.valueOf(sp.getDonGia()));
            txtTienTe.setText(sp.getDonViTienTe());
            txtMaLoai.setText(String.valueOf(sp.getMaLoai()));
            txtXuatXu.setText(sp.getXuatXu());
        }

        btnLuu.addActionListener(e -> luuSanPham());
        btnHuy.addActionListener(e -> dispose());
    }

    private void luuSanPham() {
        try {
            int maSP = Integer.parseInt(txtMaSP.getText().trim());
            String tenSP = txtTenSP.getText().trim();
            int soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            double donGia = Double.parseDouble(txtDonGia.getText().trim());
            String tienTe = txtTienTe.getText().trim();
            int maLoai = Integer.parseInt(txtMaLoai.getText().trim());
            String xuatXu = txtXuatXu.getText().trim();

            SanPham newSP = new SanPham(maSP, tenSP, soLuong, donGia, tienTe, maLoai, xuatXu);
            SanPhamBUS bus = new SanPhamBUS(); // Cần dùng bus mới để cập nhật SQL

            if (action.equals("Thêm")) {
                if (bus.timTheoMa(maSP) != null) {
                    JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại.");
                    return;
                }
                if (bus.them(newSP)) {
                    JOptionPane.showMessageDialog(this, "Đã thêm sản phẩm.");
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể thêm sản phẩm.");
                }
            } else if (action.equals("Sửa")) {
                if (bus.sua(newSP)) {
                    JOptionPane.showMessageDialog(this, "Đã cập nhật sản phẩm.");
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể cập nhật sản phẩm.");
                }
            }

            parent.loadData(bus.getAll());
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }
}
