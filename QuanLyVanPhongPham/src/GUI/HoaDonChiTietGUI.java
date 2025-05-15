package GUI;

import BUS.HoaDonChiTietBUS;
import DTO.HoaDonChiTietDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class HoaDonChiTietGUI extends JFrame {
    private JTextField txtMaHD, txtMaSP, txtSoLuong, txtDonGia, txtThanhTien;
    private JButton btnThem, btnSua, btnXoa, btnTaiLai;
    private JTable table;
    private DefaultTableModel model;
    private HoaDonChiTietBUS bus = new HoaDonChiTietBUS();

    public HoaDonChiTietGUI() {
        setTitle("Quản lý Chi Tiết Hóa Đơn");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel nhập liệu
        JPanel pnlInput = new JPanel(new GridLayout(2, 5, 10, 10));
        txtMaHD = new JTextField();
        txtMaSP = new JTextField();
        txtSoLuong = new JTextField();
        txtDonGia = new JTextField();
        txtThanhTien = new JTextField();
        txtThanhTien.setEditable(false);

        pnlInput.add(new JLabel("Mã HĐ:"));
        pnlInput.add(new JLabel("Mã SP:"));
        pnlInput.add(new JLabel("Số lượng:"));
        pnlInput.add(new JLabel("Đơn giá:"));
        pnlInput.add(new JLabel("Thành tiền:"));

        pnlInput.add(txtMaHD);
        pnlInput.add(txtMaSP);
        pnlInput.add(txtSoLuong);
        pnlInput.add(txtDonGia);
        pnlInput.add(txtThanhTien);

        add(pnlInput, BorderLayout.NORTH);

        // Bắt sự kiện tự động tính thành tiền
        KeyAdapter tinhThanhTien = new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                try {
                    int soLuong = Integer.parseInt(txtSoLuong.getText());
                    double donGia = Double.parseDouble(txtDonGia.getText());
                    double thanhTien = soLuong * donGia;
                    txtThanhTien.setText(String.valueOf(thanhTien));
                } catch (NumberFormatException ex) {
                    txtThanhTien.setText("0");
                }
            }
        };
        txtSoLuong.addKeyListener(tinhThanhTien);
        txtDonGia.addKeyListener(tinhThanhTien);

        // Nút
        JPanel pnlButton = new JPanel();
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnTaiLai = new JButton("Tải lại");
        pnlButton.add(btnThem);
        pnlButton.add(btnSua);
        pnlButton.add(btnXoa);
        pnlButton.add(btnTaiLai);
        add(pnlButton, BorderLayout.SOUTH);

        // Table
        model = new DefaultTableModel(new String[]{"Mã HĐ", "Mã SP", "Số lượng", "Đơn giá", "Thành tiền"}, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // Load dữ liệu mặc định theo mã hóa đơn đầu tiên (nếu có)
        btnTaiLai.addActionListener(e -> {
            if (!txtMaHD.getText().trim().isEmpty()) {
                loadTable(Integer.parseInt(txtMaHD.getText()));
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hóa đơn để tải dữ liệu.");
            }
        });

        btnThem.addActionListener(e -> {
            try {
                HoaDonChiTietDTO ct = getChiTietTuForm();
                if (bus.themChiTiet(ct)) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");
                    loadTable(ct.getMaHD());
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ.");
            }
        });

        btnSua.addActionListener(e -> {
            try {
                HoaDonChiTietDTO ct = getChiTietTuForm();
                if (bus.suaChiTiet(ct)) {
                    JOptionPane.showMessageDialog(this, "Sửa thành công!");
                    loadTable(ct.getMaHD());
                } else {
                    JOptionPane.showMessageDialog(this, "Sửa thất bại.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ.");
            }
        });

        btnXoa.addActionListener(e -> {
            try {
                int maHD = Integer.parseInt(txtMaHD.getText());
                int maSP = Integer.parseInt(txtMaSP.getText());
                if (bus.xoaChiTiet(maHD, maSP)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadTable(maHD);
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ.");
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtMaHD.setText(model.getValueAt(row, 0).toString());
                txtMaSP.setText(model.getValueAt(row, 1).toString());
                txtSoLuong.setText(model.getValueAt(row, 2).toString());
                txtDonGia.setText(model.getValueAt(row, 3).toString());
                txtThanhTien.setText(model.getValueAt(row, 4).toString());
            }
        });

        setVisible(true);
    }

    private HoaDonChiTietDTO getChiTietTuForm() {
        int maHD = Integer.parseInt(txtMaHD.getText());
        int maSP = Integer.parseInt(txtMaSP.getText());
        int soLuong = Integer.parseInt(txtSoLuong.getText());
        double donGia = Double.parseDouble(txtDonGia.getText());
        double thanhTien = soLuong * donGia;
        return new HoaDonChiTietDTO(maHD, maSP, soLuong, donGia, thanhTien);
    }

    private void loadTable(int maHD) {
        model.setRowCount(0);
        ArrayList<HoaDonChiTietDTO> ds = bus.getDSChiTiet(maHD);
        for (HoaDonChiTietDTO ct : ds) {
            model.addRow(new Object[]{
                ct.getMaHD(),
                ct.getMaSP(),
                ct.getSoLuong(),
                ct.getDonGia(),
                ct.getThanhTien()
            });
        }
    }

    public static void main(String[] args) {
        new HoaDonChiTietGUI();
    }
}
