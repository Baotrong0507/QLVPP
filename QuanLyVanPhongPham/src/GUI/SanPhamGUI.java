package GUI;

import BUS.SanPhamBUS;
import DTO.SanPhamDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SanPhamGUI extends JFrame {
    private SanPhamBUS spBUS = new SanPhamBUS();

    private JTextField txtMaSP, txtTenSP, txtSoLuong, txtDonGia, txtDonVi, txtMaLoai, txtXuatXu, txtTimKiem;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnLamMoi;
    private JTable tableSP;
    private DefaultTableModel model;

    public SanPhamGUI() {
        setTitle("Quản Lý Sản Phẩm");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel nhập liệu
        JPanel panelInput = new JPanel(new GridLayout(8, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createTitledBorder("Thông Tin Sản Phẩm"));

        panelInput.add(new JLabel("Mã SP:"));
        txtMaSP = new JTextField();
        panelInput.add(txtMaSP);

        panelInput.add(new JLabel("Tên SP:"));
        txtTenSP = new JTextField();
        panelInput.add(txtTenSP);

        panelInput.add(new JLabel
