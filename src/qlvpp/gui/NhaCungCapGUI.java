package qlvpp.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import qlvpp.bus.NhaCungCapBUS;
import qlvpp.model.NhaCungCap;

public class NhaCungCapGUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtTimKiem;
    private NhaCungCapBUS bus = new NhaCungCapBUS();

    public NhaCungCapGUI() {
        setLayout(new BorderLayout());

        // Thanh tìm kiếm
        JPanel topPanel = new JPanel(new BorderLayout());
        txtTimKiem = new JTextField();
        JButton btnTim = new JButton("Tìm kiếm");
        topPanel.add(new JLabel("Tìm mã hoặc tên: "), BorderLayout.WEST);
        topPanel.add(txtTimKiem, BorderLayout.CENTER);
        topPanel.add(btnTim, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Bảng
        model = new DefaultTableModel(new Object[]{"Mã NCC", "Tên NCC", "Địa chỉ", "SĐT"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnTaiLai = new JButton("Lưu / Tải lại");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnTaiLai);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load dữ liệu ban đầu
        loadData(bus.getAllNCC());

        // Sự kiện
        btnThem.addActionListener(e -> {
            new FormNCC(this, "Thêm", null).setVisible(true);
        });

        btnSua.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                NhaCungCap ncc = new NhaCungCap(
                    (int) model.getValueAt(row, 0),
                    model.getValueAt(row, 1).toString(),
                    model.getValueAt(row, 2).toString(),
                    model.getValueAt(row, 3).toString()
                );
                new FormNCC(this, "Sửa", ncc).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa.");
            }
        });

        btnXoa.addActionListener(e -> {
            String keyword = JOptionPane.showInputDialog(this, "Nhập mã hoặc tên NCC cần xóa:");
            if (keyword != null && !keyword.trim().isEmpty()) {
                List<NhaCungCap> results = bus.search(keyword);
                if (!results.isEmpty()) {
                    NhaCungCap selected = results.get(0); // chọn dòng đầu tiên
                    int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa NCC: " + selected.getTenNCC() + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        bus.deleteNCC(selected.getMaNCC());
                        loadData(bus.getAllNCC());
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy nhà cung cấp.");
                }
            }
        });

        btnTaiLai.addActionListener(e -> loadData(bus.getAllNCC()));

        btnTim.addActionListener(e -> {
            String keyword = txtTimKiem.getText().trim();
            if (!keyword.isEmpty()) {
                loadData(bus.search(keyword));
            }
        });
    }

    public void loadData(List<NhaCungCap> list) {
        model.setRowCount(0);
        for (NhaCungCap ncc : list) {
            model.addRow(new Object[]{
                ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(), ncc.getSdt()
            });
        }
    }
}
