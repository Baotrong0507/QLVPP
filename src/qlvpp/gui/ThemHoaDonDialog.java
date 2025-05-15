package qlvpp.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import qlvpp.bus.ChiTietHoaDonBUS;
import qlvpp.bus.HoaDonBUS;
import qlvpp.bus.SanPhamBUS;
import qlvpp.model.ChiTietHoaDon;
import qlvpp.model.SanPham;

public class ThemHoaDonDialog extends JDialog {
    private HoaDonBUS hoaDonBUS;
    private SanPhamBUS sanPhamBUS;
    private ChiTietHoaDonBUS chiTietHoaDonBUS;
    private JTextField txtMaHD, txtMaKH, txtMaNV;
    private JLabel lblTongTien;
    private HoaDonGUI hoaDonGUI;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private double tongTien = 0.0;

    public ThemHoaDonDialog(Frame parent, HoaDonGUI hoaDonGUI) {
        super(parent, "Thêm hóa đơn mới", true);
        this.hoaDonGUI = hoaDonGUI;
        hoaDonBUS = new HoaDonBUS();
        sanPhamBUS = new SanPhamBUS();
        chiTietHoaDonBUS = new ChiTietHoaDonBUS();
        initializeUI();
        setSize(600, 500);
        setLocationRelativeTo(parent);
        autoGenerateMaHD();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        headerPanel.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn"));

        txtMaHD = new JTextField();
        txtMaHD.setEditable(false);
        txtMaKH = new JTextField();
        txtMaNV = new JTextField();

        headerPanel.add(createInputField("Mã HD:", txtMaHD));
        headerPanel.add(createInputField("Mã KH:", txtMaKH));
        headerPanel.add(createInputField("Mã NV:", txtMaNV));

        return headerPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        String[] columnsSanPham = {"Mã SP", "Tên SP", "Số Lượng", "Đơn Giá", "Thành Tiền"};
        DefaultTableModel tableModelSanPham = new DefaultTableModel(columnsSanPham, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tableSanPham = new JTable(tableModelSanPham);
        JScrollPane scrollPane = new JScrollPane(tableSanPham);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm đã chọn"));

        JPanel addProductPanel = createAddProductPanel(tableModelSanPham);

        centerPanel.add(addProductPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        return centerPanel;
    }

    private JPanel createAddProductPanel(DefaultTableModel tableModel) {
        JPanel panel = new JPanel(new GridLayout(1, 5, 10, 0));
        panel.setBorder(BorderFactory.createTitledBorder("Thêm sản phẩm"));

        JComboBox<SanPham> comboSanPham = new JComboBox<>(getDanhSachSanPham().toArray(new SanPham[0]));
        comboSanPham.setRenderer(new SanPhamRenderer());

        JTextField txtSoLuong = new JTextField();
        JButton btnThem = createStyledButton("Thêm vào hóa đơn", new Color(0, 153, 76));

        btnThem.addActionListener(e -> handleAddProduct(comboSanPham, txtSoLuong, tableModel));

        panel.add(new JLabel("Chọn sản phẩm:"));
        panel.add(comboSanPham);
        panel.add(new JLabel("Số lượng:"));
        panel.add(txtSoLuong);
        panel.add(btnThem);

        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout(10, 10));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        lblTongTien = new JLabel("Tổng tiền: 0.00");
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 14));
        lblTongTien.setForeground(new Color(220, 0, 0));

        JButton btnLuu = createStyledButton("LƯU HÓA ĐƠN", new Color(0, 102, 204));
        btnLuu.setPreferredSize(new Dimension(120, 40));
        btnLuu.addActionListener(this::handleSaveInvoice);

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.add(lblTongTien);

        footerPanel.add(totalPanel, BorderLayout.CENTER);
        footerPanel.add(btnLuu, BorderLayout.EAST);

        return footerPanel;
    }

    private void handleAddProduct(JComboBox<SanPham> combo, JTextField txtSoLuong, DefaultTableModel model) {
        SanPham sanPham = (SanPham) combo.getSelectedItem();
        int soLuong;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText());
            if (soLuong <= 0) {
                throw new NumberFormatException("Số lượng phải lớn hơn 0!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số hợp lệ và lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double donGia = sanPham.getDonGia();
        double thanhTien = donGia * soLuong;
        model.addRow(new Object[]{sanPham.getMaSP(), sanPham.getTenSP(), soLuong, donGia, thanhTien});

        tongTien = calculateTotal(model);
        lblTongTien.setText("Tổng tiền: " + String.format("%.2f", tongTien));
    }

    private double calculateTotal(DefaultTableModel model) {
        double total = 0.0;
        for (int i = 0; i < model.getRowCount(); i++) {
            total += Double.parseDouble(model.getValueAt(i, 4).toString());
        }
        return total;
    }

    private void handleSaveInvoice(ActionEvent e) {
        try {
            String ngayLap = LocalDate.now().format(formatter);
            int maHD = Integer.parseInt(txtMaHD.getText()); // Parse thành int
            hoaDonBUS.themHoaDon(
                    maHD,
                    txtMaKH.getText(),
                    txtMaNV.getText(),
                    ngayLap,
                    String.valueOf(tongTien)
            );

            JTable table = findTable();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                int maSP = Integer.parseInt(model.getValueAt(i, 0).toString());
                int soLuong = Integer.parseInt(model.getValueAt(i, 2).toString());
                double donGia = Double.parseDouble(model.getValueAt(i, 3).toString());
                double thanhTien = Double.parseDouble(model.getValueAt(i, 4).toString());

                ChiTietHoaDon cthd = new ChiTietHoaDon(maHD, maSP, soLuong, donGia, thanhTien);
                chiTietHoaDonBUS.themChiTietHoaDon(cthd);
            }

            dispose();
            JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            hoaDonGUI.loadHoaDonList();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JTable findTable() {
        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JPanel) {
                for (Component innerComp : ((JPanel) comp).getComponents()) {
                    if (innerComp instanceof JPanel) {
                        for (Component deepestComp : ((JPanel) innerComp).getComponents()) {
                            if (deepestComp instanceof JScrollPane) {
                                return ((JScrollPane) deepestComp).getViewport().getView() instanceof JTable
                                        ? (JTable) ((JScrollPane) deepestComp).getViewport().getView()
                                        : null;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private void autoGenerateMaHD() {
        try {
            int maHD = hoaDonBUS.generateMaHD(); // Nhận mã dưới dạng int
            txtMaHD.setText(String.valueOf(maHD)); // Hiển thị dưới dạng chuỗi
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tạo mã hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        return btn;
    }

    private JPanel createInputField(String label, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.add(new JLabel(label), BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private static class SanPhamRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof SanPham) {
                SanPham sp = (SanPham) value;
                setText(String.format("%s - %s (%.2f VND)", sp.getMaSP(), sp.getTenSP(), sp.getDonGia()));
            }
            return this;
        }
    }

    private List<SanPham> getDanhSachSanPham() {
        return sanPhamBUS.getAll();
    }
}