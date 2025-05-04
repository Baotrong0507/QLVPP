package qlvpp.gui;

import qlvpp.dao.PhieuNhapDAO;
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

        String[] columnNames = {"Mã SP", "Số lượng", "Giá Nhập", "Thành tiền"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col != 3; // Chỉ không cho sửa "Thành tiền"
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
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!");
            }
        });

        // Cập nhật thành tiền khi sửa số lượng hoặc giá nhập
        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn(); // Lấy cột được chỉnh sửa
            if (row >= 0 && model.getRowCount() > row && (column == 1 || column == 2)) { // Chỉ xử lý cột Số lượng hoặc Giá Nhập
                try {
                    String soLuongStr = model.getValueAt(row, 1).toString();
                    String giaNhapStr = model.getValueAt(row, 2).toString();
                    if (!soLuongStr.isEmpty() && !giaNhapStr.isEmpty()) {
                        int soLuong = Integer.parseInt(soLuongStr);
                        double giaNhap = Double.parseDouble(giaNhapStr);
                        double thanhTien = soLuong * giaNhap;
                        model.setValueAt(thanhTien, row, 3);
                    } else {
                        model.setValueAt(0.0, row, 3);
                    }
                    tinhTongTien();
                } catch (NumberFormatException ex) {
                    model.setValueAt(0.0, row, 3);
                    tinhTongTien();
                }
            }
        });

        btnLuu.addActionListener(e -> {
            // Kiểm tra bảng có dữ liệu không
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Bảng chi tiết phiếu nhập trống! Vui lòng thêm ít nhất một dòng.");
                return;
            }

            try {
                // Lấy dữ liệu phiếu nhập
                int maPN = Integer.parseInt(txtMaPN.getText());
                int maNV = Integer.parseInt(txtMaNV.getText());
                int maNCC = Integer.parseInt(txtMaNCC.getText());
                Date ngayNhap = Date.valueOf(txtNgayNhap.getText());
                double tongTien = Double.parseDouble(txtTongTien.getText());

                // Kiểm tra dữ liệu chi tiết
                chiTietList.clear();
                for (int i = 0; i < model.getRowCount(); i++) {
                    String maSPStr = model.getValueAt(i, 0).toString();
                    String soLuongStr = model.getValueAt(i, 1).toString();
                    String giaNhapStr = model.getValueAt(i, 2).toString();
                    String thanhTienStr = model.getValueAt(i, 3).toString();

                    if (maSPStr.isEmpty() || soLuongStr.isEmpty() || giaNhapStr.isEmpty()) {
                        throw new IllegalArgumentException("Dòng " + (i + 1) + ": Vui lòng nhập đầy đủ Mã SP, Số lượng, Giá Nhập.");
                    }

                    int maSP = Integer.parseInt(maSPStr);
                    int soLuong = Integer.parseInt(soLuongStr);
                    double giaNhap = Double.parseDouble(giaNhapStr);
                    double thanhTien = Double.parseDouble(thanhTienStr);

                    chiTietList.add(new ChiTietPhieuNhap(maPN, maSP, soLuong, giaNhap, thanhTien));
                }

                // Tạo đối tượng PhieuNhap
                phieuNhap = new PhieuNhap(maPN, maNV, maNCC, ngayNhap, tongTien);

                // Lưu vào cơ sở dữ liệu
                PhieuNhapDAO dao = new PhieuNhapDAO();
                dao.savePhieuNhap(phieuNhap, chiTietList);
                JOptionPane.showMessageDialog(this, "Lưu thành công!");
                dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu vào cơ sở dữ liệu: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi định dạng dữ liệu: " + ex.getMessage());
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
            } catch (Exception ignored) {
            }
        }
        txtTongTien.setText(String.valueOf(tong));
    }
}