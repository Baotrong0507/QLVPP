package qlvpp.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import qlvpp.bus.KhuyenMaiBUS;
import qlvpp.model.KhuyenMai;
import qlvpp.model.KhuyenMaiSanPham;
import qlvpp.model.KhuyenMaiHoaDon;
import qlvpp.model.SanPham;

public class KhuyenMaiForm extends JDialog {
    private KhuyenMaiBUS khuyenMaiBUS;
    private JTextField txtMaKM, txtNgayBatDau, txtNgayKetThuc;
    private JTable tableSanPham, tableHoaDon;
    private DefaultTableModel modelSanPham, modelHoaDon;
    private JComboBox<String> cbSanPham;
    private JTextField txtPhanTramSP, txtSoTienHD, txtPhanTramHD;
    private boolean isEdit;
    private int maKM;

    public KhuyenMaiForm(JFrame parent, boolean isEdit, int... maKM) {
    super(parent, isEdit ? "Sửa Chương Trình Khuyến Mãi" : "Thêm Chương Trình Khuyến Mãi", true);
    this.khuyenMaiBUS = new KhuyenMaiBUS();
    this.isEdit = isEdit;
    this.maKM = isEdit ? maKM[0] : khuyenMaiBUS.getNextMaKM(); // Tự động sinh MaKM khi thêm mới
    initComponents();
    if (isEdit) {
        loadKhuyenMaiData();
    } else {
        txtMaKM.setText(String.valueOf(this.maKM)); // Hiển thị MaKM tự sinh
    }
}

    private void initComponents() {
        setSize(700, 500);
        setLocationRelativeTo(getParent());

        // Input Panel
        JPanel panelInfo = new JPanel(new GridLayout(4, 2, 10, 10));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        txtMaKM = new JTextField();
        txtMaKM.setEditable(false);
        txtNgayBatDau = new JTextField();
        txtNgayKetThuc = new JTextField();

        panelInfo.add(new JLabel("Mã Khuyến Mãi:"));
        panelInfo.add(txtMaKM);
        panelInfo.add(new JLabel("Ngày Bắt Đầu (YYYY-MM-DD):"));
        panelInfo.add(txtNgayBatDau);
        panelInfo.add(new JLabel("Ngày Kết Thúc (YYYY-MM-DD):"));
        panelInfo.add(txtNgayKetThuc);

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab Khuyến Mãi Sản Phẩm
        JPanel panelSanPham = new JPanel(new BorderLayout());
        modelSanPham = new DefaultTableModel(new String[]{"Mã SP", "Tên SP", "% Giảm"}, 0);
        tableSanPham = new JTable(modelSanPham);
        JScrollPane scrollSanPham = new JScrollPane(tableSanPham);

        JPanel panelSPControls = new JPanel(new FlowLayout());
        cbSanPham = new JComboBox<>();
        loadSanPhamComboBox();
        txtPhanTramSP = new JTextField(5);
        JButton btnAddSP = new JButton("Thêm");
        JButton btnRemoveSP = new JButton("Xóa");

        panelSPControls.add(new JLabel("Sản Phẩm:"));
        panelSPControls.add(cbSanPham);
        panelSPControls.add(new JLabel("% Giảm:"));
        panelSPControls.add(txtPhanTramSP);
        panelSPControls.add(btnAddSP);
        panelSPControls.add(btnRemoveSP);

        panelSanPham.add(scrollSanPham, BorderLayout.CENTER);
        panelSanPham.add(panelSPControls, BorderLayout.SOUTH);
        tabbedPane.addTab("Khuyến Mãi Sản Phẩm", panelSanPham);

        // Tab Khuyến Mãi Hóa Đơn
        JPanel panelHoaDon = new JPanel(new BorderLayout());
        modelHoaDon = new DefaultTableModel(new String[]{"Số Tiền HD", "% Giảm"}, 0);
        tableHoaDon = new JTable(modelHoaDon);
        JScrollPane scrollHoaDon = new JScrollPane(tableHoaDon);

        JPanel panelHDControls = new JPanel(new FlowLayout());
        txtSoTienHD = new JTextField(10);
        txtPhanTramHD = new JTextField(5);
        JButton btnAddHD = new JButton("Thêm");
        JButton btnRemoveHD = new JButton("Xóa");

        /*panelHDControls.add(new JLabel("Số Tiền HD:"));
        panelHDControls.add(txtSoTienHD);
        panelHDControls.add(new JLabel("% Giảm:"));
        panelHDControls.add(txtPhanTramHD);*/
        panelHDControls.add(btnAddHD);
        panelHDControls.add(btnRemoveHD);

        panelHoaDon.add(scrollHoaDon, BorderLayout.CENTER);
        panelHoaDon.add(panelHDControls, BorderLayout.SOUTH);
        txtPhanTramHD = new JTextField(5);
        panelHDControls.add(new JLabel("Số Tiền HD:"));
        panelHDControls.add(txtSoTienHD);
        panelHDControls.add(new JLabel("% Giảm:"));
        panelHDControls.add(txtPhanTramHD);
        panelHDControls.add(btnAddHD);
        panelHDControls.add(btnRemoveHD);

        panelHoaDon.add(scrollHoaDon, BorderLayout.CENTER);
        panelHoaDon.add(panelHDControls, BorderLayout.SOUTH);
        tabbedPane.addTab("Khuyến Mãi Hóa Đơn", panelHoaDon);

        // Button Panel
        JPanel panelButtons = new JPanel(new FlowLayout());
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        panelButtons.add(btnSave);
        panelButtons.add(btnCancel);

        // Layout
        setLayout(new BorderLayout());
        add(panelInfo, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        // Action Listeners
        btnAddSP.addActionListener(e -> themSanPham());
        btnRemoveSP.addActionListener(e -> xoaSanPham());
        btnAddHD.addActionListener(e -> themHoaDon());
        btnRemoveHD.addActionListener(e -> xoaHoaDon());
        btnSave.addActionListener(e -> luuKhuyenMai());
        btnCancel.addActionListener(e -> dispose());
    }

    private void loadSanPhamComboBox() {
        List<SanPham> sanPhamList = khuyenMaiBUS.getAllSanPham();
        for (SanPham sp : sanPhamList) {
            cbSanPham.addItem(sp.getMaSP() + " - " + sp.getTenSP());
        }
    }

    private void loadKhuyenMaiData() {
    KhuyenMai km = khuyenMaiBUS.getKhuyenMaiById(this.maKM);
    if (km != null) {
        txtMaKM.setText(String.valueOf(km.getMaKM()));
        txtNgayBatDau.setText(km.getNgayBatDau() != null ? km.getNgayBatDau() : "");
        txtNgayKetThuc.setText(km.getNgayKetThuc() != null && !km.getNgayKetThuc().equalsIgnoreCase("Sắp diễn ra") ? km.getNgayKetThuc() : "Sắp diễn ra");
    }


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

    private void themSanPham() {
        try {
            String[] spInfo = cbSanPham.getSelectedItem().toString().split(" - ");
            int maSP = Integer.parseInt(spInfo[0]);
            double phanTram = Double.parseDouble(txtPhanTramSP.getText());
            if (phanTram < 0 || phanTram > 100) {
                JOptionPane.showMessageDialog(this, "Phần trăm giảm phải từ 0 đến 100!");
                return;
            }
            SanPham sp = khuyenMaiBUS.getSanPhamById(maSP);
            modelSanPham.addRow(new Object[]{maSP, sp.getTenSP(), phanTram});
            txtPhanTramSP.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho % Giảm!");
        }
    }

    private void xoaSanPham() {
        int row = tableSanPham.getSelectedRow();
        if (row != -1) {
            modelSanPham.removeRow(row);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm để xóa!");
        }
    }

    private void themHoaDon() {
        try {
            double soTienHD = Double.parseDouble(txtSoTienHD.getText());
            double phanTram = Double.parseDouble(txtPhanTramHD.getText());
            if (phanTram < 0 || phanTram > 100) {
                JOptionPane.showMessageDialog(this, "Phần trăm giảm phải từ 0 đến 100!");
                return;
            }
            modelHoaDon.addRow(new Object[]{soTienHD, phanTram});
            txtSoTienHD.setText("");
            txtPhanTramHD.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho Số Tiền HD và % Giảm!");
        }
    }

    private void xoaHoaDon() {
        int row = tableHoaDon.getSelectedRow();
        if (row != -1) {
            modelHoaDon.removeRow(row);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một mức hóa đơn để xóa!");
        }
    }

    private void luuKhuyenMai() {
        try {
            // Validate inputs
            int maKM = this.maKM;
            String ngayBatDau = txtNgayBatDau.getText();
            String ngayKetThuc = txtNgayKetThuc.getText();

            if (ngayBatDau.isEmpty() || ngayKetThuc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
                return;
            }

            KhuyenMai km = new KhuyenMai(maKM, ngayBatDau, ngayKetThuc);

            // Get KhuyenMaiSanPham list
            List<KhuyenMaiSanPham> kmspList = new ArrayList<>();
            for (int i = 0; i < modelSanPham.getRowCount(); i++) {
                kmspList.add(new KhuyenMaiSanPham(maKM, (int) modelSanPham.getValueAt(i, 0), (double) modelSanPham.getValueAt(i, 2)));
            }

            // Get KhuyenMaiHoaDon list
            List<KhuyenMaiHoaDon> kmhdList = new ArrayList<>();
            for (int i = 0; i < modelHoaDon.getRowCount(); i++) {
                kmhdList.add(new KhuyenMaiHoaDon(maKM, (double) modelHoaDon.getValueAt(i, 0), (double) modelHoaDon.getValueAt(i, 1)));
            }

            // Save
            if (isEdit) {
                if (khuyenMaiBUS.updateKhuyenMai(km, kmspList, kmhdList)) {
                    JOptionPane.showMessageDialog(this, "Sửa chương trình khuyến mãi thành công!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Sửa chương trình khuyến mãi thất bại!");
                }
            } else {
                if (khuyenMaiBUS.addKhuyenMai(km, kmspList, kmhdList)) {
                    JOptionPane.showMessageDialog(this, "Thêm chương trình khuyến mãi thành công!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm chương trình khuyến mãi thất bại!");
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho Mã KM!");
        }
    }
}