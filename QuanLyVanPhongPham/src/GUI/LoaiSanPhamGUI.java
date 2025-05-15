package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BUS.LoaiSanPhamBUS;
import DTO.LoaiSanPhamDTO;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LoaiSanPhamGUI extends JFrame {
    private LoaiSanPhamBUS loaiBUS = new LoaiSanPhamBUS();

    private JTextField txtMaLoai, txtTenLoai, txtTimKiem;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnLamMoi;
    private JTable tableLoai;
    private DefaultTableModel model;

    public LoaiSanPhamGUI() {
        setTitle("Quản Lý Loại Sản Phẩm");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel nhập liệu
        JPanel panelInput = new JPanel(new GridLayout(3, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createTitledBorder("Thông Tin Loại Sản Phẩm"));

        panelInput.add(new JLabel("Mã Loại:"));
        txtMaLoai = new JTextField();
        panelInput.add(txtMaLoai);

        panelInput.add(new JLabel("Tên Loại:"));
        txtTenLoai = new JTextField();
        panelInput.add(txtTenLoai);

        panelInput.add(new JLabel("Tìm kiếm (cách nhau dấu ,):"));
        txtTimKiem = new JTextField();
        panelInput.add(txtTimKiem);

        add(panelInput, BorderLayout.NORTH);

        // Bảng dữ liệu
        model = new DefaultTableModel(new Object[]{"Mã Loại", "Tên Loại"}, 0);
        tableLoai = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableLoai);
        add(scrollPane, BorderLayout.CENTER);

        // Panel nút
        JPanel panelButtons = new JPanel();
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnTimKiem = new JButton("Tìm Kiếm");
        btnLamMoi = new JButton("Làm Mới");

        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnTimKiem);
        panelButtons.add(btnLamMoi);

        add(panelButtons, BorderLayout.SOUTH);

        // Load dữ liệu ban đầu
        loadTable(loaiBUS.getDanhSachLoai());

        // Sự kiện nút
        btnThem.addActionListener(e -> themLoai());
        btnSua.addActionListener(e -> suaLoai());
        btnXoa.addActionListener(e -> xoaLoai());
        btnTimKiem.addActionListener(e -> timKiemLoai());
        btnLamMoi.addActionListener(e -> lamMoi());

        // Khi click dòng bảng, hiển thị lên form
        tableLoai.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tableLoai.getSelectedRow();
                if (row >= 0) {
                    txtMaLoai.setText(model.getValueAt(row, 0).toString());
                    txtTenLoai.setText(model.getValueAt(row, 1).toString());
                }
            }
        });
    }

    private void loadTable(ArrayList<LoaiSanPhamDTO> list) {
        model.setRowCount(0);
        for (LoaiSanPhamDTO loai : list) {
            model.addRow(new Object[]{loai.getMaLoai(), loai.getTenLoai()});
        }
    }

    private void themLoai() {
        String ma = txtMaLoai.getText().trim();
        String ten = txtTenLoai.getText().trim();
        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ mã và tên loại!");
            return;
        }
        if (loaiBUS.themLoai(new LoaiSanPhamDTO(ma, ten))) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            loadTable(loaiBUS.getDanhSachLoai());
            lamMoi();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại! Có thể mã loại đã tồn tại.");
        }
    }

    private void suaLoai() {
        String ma = txtMaLoai.getText().trim();
        String ten = txtTenLoai.getText().trim();
        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ mã và tên loại!");
            return;
        }
        if (loaiBUS.suaLoai(new LoaiSanPhamDTO(ma, ten))) {
            JOptionPane.showMessageDialog(this, "Sửa thành công!");
            loadTable(loaiBUS.getDanhSachLoai());
            lamMoi();
        } else {
            JOptionPane.showMessageDialog(this, "Sửa thất bại! Kiểm tra lại mã loại.");
        }
    }

    private void xoaLoai() {
        String ma = txtMaLoai.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn mã loại để xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa loại sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (loaiBUS.xoaLoai(ma)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadTable(loaiBUS.getDanhSachLoai());
                lamMoi();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }

    private void timKiemLoai() {
        String keyword = txtTimKiem.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!");
            return;
        }
        ArrayList<LoaiSanPhamDTO> result = loaiBUS.timKiemLoai(keyword);
        loadTable(result);
    }

    private void lamMoi() {
        txtMaLoai.setText("");
        txtTenLoai.setText("");
        txtTimKiem.setText("");
        loadTable(loaiBUS.getDanhSachLoai());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoaiSanPhamGUI().setVisible(true);
        });
    }
}
