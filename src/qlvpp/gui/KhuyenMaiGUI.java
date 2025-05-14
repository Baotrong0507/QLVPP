package qlvpp.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import qlvpp.bus.KhuyenMaiBUS;
import qlvpp.model.KhuyenMai;

public class KhuyenMaiGUI extends JPanel {
    private KhuyenMaiBUS khuyenMaiBUS;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaKM, txtNgayBatDau, txtNgayKetThuc;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnChiTiet;

    public KhuyenMaiGUI() {
        khuyenMaiBUS = new KhuyenMaiBUS();
        initComponents();
        loadKhuyenMaiList();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Mã Khuyến Mãi:"));
        txtMaKM = new JTextField();
        inputPanel.add(txtMaKM);



        inputPanel.add(new JLabel("Ngày Bắt Đầu (YYYY-MM-DD):"));
        txtNgayBatDau = new JTextField();
        inputPanel.add(txtNgayBatDau);

        inputPanel.add(new JLabel("Ngày Kết Thúc (YYYY-MM-DD):"));
        txtNgayKetThuc = new JTextField();
        inputPanel.add(txtNgayKetThuc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnTimKiem = new JButton("Tìm Kiếm");
        btnChiTiet = new JButton("Chi Tiết");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnTimKiem);
        buttonPanel.add(btnChiTiet);

        // Table
        String[] columns = {"Mã KM", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Trạng Thái"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add components
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        btnThem.addActionListener(e -> themKhuyenMai());
        btnSua.addActionListener(e -> suaKhuyenMai());
        btnXoa.addActionListener(e -> xoaKhuyenMai());
        btnTimKiem.addActionListener(e -> timKiemKhuyenMai());
        btnChiTiet.addActionListener(e -> xemChiTiet());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtMaKM.setText(tableModel.getValueAt(row, 0).toString());
                txtNgayBatDau.setText(tableModel.getValueAt(row, 1).toString());
                txtNgayKetThuc.setText(tableModel.getValueAt(row, 2).toString());
            }
        });
    }

private void loadKhuyenMaiList() {
    tableModel.setRowCount(0);
    List<KhuyenMai> kmList = khuyenMaiBUS.getAllKhuyenMai();
    for (KhuyenMai km : kmList) {
        String ngayKetThucDisplay = (km.getNgayKetThuc() != null) ? km.getNgayKetThuc() : "Sắp diễn ra";
        String trangThai = khuyenMaiBUS.tinhTrangThai(km.getNgayBatDau(), km.getNgayKetThuc());
        tableModel.addRow(new Object[]{km.getMaKM(), km.getNgayBatDau(), ngayKetThucDisplay, trangThai});
    }
}

    private void themKhuyenMai() {
        KhuyenMaiForm form = new KhuyenMaiForm((JFrame) SwingUtilities.getWindowAncestor(this), false);
        form.setVisible(true);
        loadKhuyenMaiList();
    }

    private void suaKhuyenMai() {
        try {
            int maKM = Integer.parseInt(txtMaKM.getText());
            KhuyenMaiForm form = new KhuyenMaiForm((JFrame) SwingUtilities.getWindowAncestor(this), true, maKM);
            form.setVisible(true);
            loadKhuyenMaiList();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một chương trình khuyến mãi!");
        }
    }

    private void xoaKhuyenMai() {
        try {
            int maKM = Integer.parseInt(txtMaKM.getText());
            if (khuyenMaiBUS.deleteKhuyenMai(maKM)) {
                JOptionPane.showMessageDialog(this, "Xóa chương trình khuyến mãi thành công!");
                loadKhuyenMaiList();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa chương trình khuyến mãi thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho Mã KM!");
        }
    }

    private void timKiemKhuyenMai() {
    String maKM = txtMaKM.getText().trim();
    String ngayBatDau = txtNgayBatDau.getText().trim();
    String ngayKetThuc = txtNgayKetThuc.getText().trim();

    // Nếu tất cả các trường đều trống, hiển thị toàn bộ danh sách
    if (maKM.isEmpty() && ngayBatDau.isEmpty() && ngayKetThuc.isEmpty()) {
        loadKhuyenMaiList();
        return;
    }

    tableModel.setRowCount(0);
    List<KhuyenMai> khuyenMaiList = khuyenMaiBUS.searchKhuyenMai(maKM, ngayBatDau, ngayKetThuc);
    for (KhuyenMai km : khuyenMaiList) {
        String trangThai = khuyenMaiBUS.tinhTrangThai(km.getNgayBatDau(), km.getNgayKetThuc());
        tableModel.addRow(new Object[]{km.getMaKM(), km.getNgayBatDau(), km.getNgayKetThuc(), trangThai});
    }

    if (khuyenMaiList.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Không tìm thấy chương trình khuyến mãi phù hợp!");
    }
}

    private void xemChiTiet() {
    try {
        int maKM = Integer.parseInt(txtMaKM.getText());
        KhuyenMaiDetailForm detailForm = new KhuyenMaiDetailForm((JFrame) SwingUtilities.getWindowAncestor(this), maKM);
        detailForm.setVisible(true);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn một chương trình khuyến mãi!");
    }
}

    private void clearFields() {
        txtMaKM.setText("");
        txtNgayBatDau.setText("");
        txtNgayKetThuc.setText("");
    }
}