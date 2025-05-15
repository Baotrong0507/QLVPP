package qlvpp.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import qlvpp.bus.SanPhamBUS;
import qlvpp.model.SanPham;

public class SanPhamGUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtMaLoai1, txtMaLoai2;
    private SanPhamBUS bus = new SanPhamBUS();

    public SanPhamGUI() {
        setLayout(new BorderLayout());

        // 🧭 Panel tìm kiếm nâng cao theo 2 mã loại
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtMaLoai1 = new JTextField(5);
        txtMaLoai2 = new JTextField(5);
        JButton btnTimKiem = new JButton("Tìm theo 2 mã loại (OR)");
        JButton btnTaiLai = new JButton("Tải lại");

        topPanel.add(new JLabel("Mã loại 1:"));
        topPanel.add(txtMaLoai1);
        topPanel.add(new JLabel("Mã loại 2:"));
        topPanel.add(txtMaLoai2);
        topPanel.add(btnTimKiem);
        topPanel.add(btnTaiLai);
        add(topPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{
            "Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Đơn vị", "Mã loại", "Xuất xứ"
        }, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 📦 Nút thêm/sửa/xóa có thể bổ sung sau
        JPanel buttonPanel = new JPanel();
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        add(buttonPanel, BorderLayout.SOUTH);

        // ⚙️ Sự kiện
        btnTimKiem.addActionListener(e -> {
            try {
                int ma1 = Integer.parseInt(txtMaLoai1.getText().trim());
                int ma2 = Integer.parseInt(txtMaLoai2.getText().trim());
                List<SanPham> kq = bus.timTheoHaiMaLoai(ma1, ma2);
                loadData(kq);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng mã loại (số nguyên).");
            }
        });

        btnTaiLai.addActionListener(e -> loadData(bus.getAll()));

        // 🚀 Load dữ liệu ban đầu
        loadData(bus.getAll());
    }

    public void loadData(List<SanPham> list) {
        model.setRowCount(0);
        for (SanPham sp : list) {
            model.addRow(new Object[]{
                sp.getMaSP(), sp.getTenSP(), sp.getSoLuong(),
                sp.getDonGia(), sp.getDonViTienTe(),
                sp.getMaLoai(), sp.getXuatXu()
            });
        }
    }
}
