package qlvpp.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import qlvpp.bus.KhuyenMaiBUS;
import qlvpp.model.KhuyenMai;
import qlvpp.model.KhuyenMaiSanPham;
import qlvpp.model.KhuyenMaiHoaDon;
import qlvpp.model.SanPham;

public class KhuyenMaiDetailForm extends JDialog {
    private KhuyenMaiBUS khuyenMaiBUS;
    private int maKM;
    private JLabel lblMaKM, lblNgayBatDau, lblNgayKetThuc, lblTrangThai;
    private JTable tableSanPham, tableHoaDon;
    private DefaultTableModel modelSanPham, modelHoaDon;

    public KhuyenMaiDetailForm(JFrame parent, int maKM) {
        super(parent, "Chi Tiết Chương Trình Khuyến Mãi", true);
        this.khuyenMaiBUS = new KhuyenMaiBUS();
        this.maKM = maKM;
        initComponents();
        loadKhuyenMaiDetails();
    }

    private void initComponents() {
        setSize(600, 500);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Panel Thông Tin Chung
        JPanel panelInfo = new JPanel(new GridLayout(5, 2, 10, 10));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelInfo.add(new JLabel("Mã Khuyến Mãi:"));
        lblMaKM = new JLabel();
        panelInfo.add(lblMaKM);


        panelInfo.add(new JLabel("Ngày Bắt Đầu:"));
        lblNgayBatDau = new JLabel();
        panelInfo.add(lblNgayBatDau);

        panelInfo.add(new JLabel("Ngày Kết Thúc:"));
        lblNgayKetThuc = new JLabel();
        panelInfo.add(lblNgayKetThuc);

        panelInfo.add(new JLabel("Trạng Thái:"));
        lblTrangThai = new JLabel();
        panelInfo.add(lblTrangThai);

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab Khuyến Mãi Sản Phẩm
        JPanel panelSanPham = new JPanel(new BorderLayout());
        modelSanPham = new DefaultTableModel(new String[]{"Mã SP", "Tên SP", "% Giảm"}, 0);
        tableSanPham = new JTable(modelSanPham);
        tableSanPham.setEnabled(false); // Chỉ đọc
        JScrollPane scrollSanPham = new JScrollPane(tableSanPham);
        panelSanPham.add(scrollSanPham, BorderLayout.CENTER);
        tabbedPane.addTab("Khuyến Mãi Sản Phẩm", panelSanPham);

        // Tab Khuyến Mãi Hóa Đơn
        JPanel panelHoaDon = new JPanel(new BorderLayout());
        modelHoaDon = new DefaultTableModel(new String[]{"Số Tiền HD", "% Giảm"}, 0);
        tableHoaDon = new JTable(modelHoaDon);
        tableHoaDon.setEnabled(false); // Chỉ đọc
        JScrollPane scrollHoaDon = new JScrollPane(tableHoaDon);
        panelHoaDon.add(scrollHoaDon, BorderLayout.CENTER);
        tabbedPane.addTab("Khuyến Mãi Hóa Đơn", panelHoaDon);

        // Panel Nút
        JPanel panelButtons = new JPanel(new FlowLayout());
        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dispose());
        panelButtons.add(btnClose);

        // Bố cục
        setLayout(new BorderLayout());
        add(panelInfo, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
    }

    private void loadKhuyenMaiDetails() {
        KhuyenMai km = khuyenMaiBUS.getKhuyenMaiById(maKM);
        if (km != null) {
            lblMaKM.setText(String.valueOf(km.getMaKM()));
            lblNgayBatDau.setText(km.getNgayBatDau());
            lblNgayKetThuc.setText(km.getNgayKetThuc());
            lblTrangThai.setText(khuyenMaiBUS.tinhTrangThai(km.getNgayBatDau(), km.getNgayKetThuc()));

            // Load KhuyenMaiSanPham
            List<KhuyenMaiSanPham> kmspList = khuyenMaiBUS.getKhuyenMaiSanPham(maKM);
            for (KhuyenMaiSanPham kmsp : kmspList) {
                SanPham sp = khuyenMaiBUS.getSanPhamById(kmsp.getMaSP());
                modelSanPham.addRow(new Object[]{kmsp.getMaSP(), sp.getTenSP(), kmsp.getPhanTramGiam()});
            }

            // Load KhuyenMaiHoaDon
            List<KhuyenMaiHoaDon> kmhdList = khuyenMaiBUS.getKhuyenMaiHoaDon(maKM);
            for (KhuyenMaiHoaDon kmhd : kmhdList) {
                modelHoaDon.addRow(new Object[]{kmhd.getSoTienHoaDon(), kmhd.getPhanTramGiam()});
            }
        }
    }
}