package qlvpp.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import qlvpp.bus.HoaDonBUS;
import qlvpp.bus.SanPhamBUS;
import qlvpp.bus.ChiTietHoaDonBUS;
import qlvpp.model.HoaDon;
import qlvpp.model.SanPham;
import qlvpp.model.ChiTietHoaDon;

public class HoaDonGUI extends JPanel {
    private HoaDonBUS hoaDonBUS;
    private SanPhamBUS sanPhamBUS;
    private ChiTietHoaDonBUS chiTietHoaDonBUS;
    private JTable tableHoaDon, tableChiTietHoaDon, tableSanPham;
    private DefaultTableModel tableModelHoaDon, tableModelChiTiet, tableModelSanPham;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField txtTimKiem, txtMaHD, txtMaKH, txtMaNV, txtNgayLap, txtTongTien;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnLamMoi;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public HoaDonGUI() {
        hoaDonBUS = new HoaDonBUS();
        sanPhamBUS = new SanPhamBUS();
        chiTietHoaDonBUS = new ChiTietHoaDonBUS();
        initComponents();
        loadHoaDonList();
        loadDanhSachSanPham();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txtTimKiem = new JTextField(25);
        btnTimKiem = createStyledButton("Tìm kiếm", Color.BLUE);
        btnLamMoi = createStyledButton("Làm mới", Color.GRAY);

        searchPanel.add(new JLabel("Từ khóa:"));
        searchPanel.add(txtTimKiem);
        searchPanel.add(btnTimKiem);
        searchPanel.add(btnLamMoi);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn"));
        txtMaHD = new JTextField();
        txtMaKH = new JTextField();
        txtMaNV = new JTextField();
        txtNgayLap = new JTextField();
        txtTongTien = new JTextField();

        inputPanel.add(new JLabel("Mã Hóa Đơn:"));
        inputPanel.add(txtMaHD);
        inputPanel.add(new JLabel("Mã Khách Hàng:"));
        inputPanel.add(txtMaKH);
        inputPanel.add(new JLabel("Mã Nhân Viên:"));
        inputPanel.add(txtMaNV);
        inputPanel.add(new JLabel("Ngày Lập (dd/MM/yyyy):"));
        inputPanel.add(txtNgayLap);
        inputPanel.add(new JLabel("Tổng Tiền:"));
        inputPanel.add(txtTongTien);

        // Table Hóa Đơn
        String[] columnsHoaDon = {"Mã HD", "Mã KH", "Mã NV", "Ngày Lập", "Tổng Tiền"};
        tableModelHoaDon = new DefaultTableModel(columnsHoaDon, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableHoaDon = new JTable(tableModelHoaDon);
        sorter = new TableRowSorter<>(tableModelHoaDon);
        tableHoaDon.setRowSorter(sorter);
        JScrollPane scrollPaneHoaDon = new JScrollPane(tableHoaDon);
        scrollPaneHoaDon.setBorder(BorderFactory.createTitledBorder("Danh sách hóa đơn"));

        // Table Chi Tiết Hóa Đơn
        String[] columnsChiTiet = {"Mã SP", "Tên SP", "Số Lượng", "Đơn Giá", "Thành Tiền"};
        tableModelChiTiet = new DefaultTableModel(columnsChiTiet, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableChiTietHoaDon = new JTable(tableModelChiTiet);
        JScrollPane scrollPaneChiTiet = new JScrollPane(tableChiTietHoaDon);
        scrollPaneChiTiet.setBorder(BorderFactory.createTitledBorder("Chi tiết hóa đơn"));

        // Table Sản Phẩm
        String[] columnsSanPham = {"Mã SP", "Tên SP", "Số Lượng", "Đơn Giá", "Đơn Vị Tiền Tệ", "Mã Loại", "Xuất Xứ"};
        tableModelSanPham = new DefaultTableModel(columnsSanPham, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableSanPham = new JTable(tableModelSanPham);
        JScrollPane scrollPaneSanPham = new JScrollPane(tableSanPham);
        scrollPaneSanPham.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm"));

        // Sử dụng JSplitPane để chia giao diện
        JSplitPane splitPaneHoaDonChiTiet = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPaneHoaDon, scrollPaneChiTiet);
        splitPaneHoaDonChiTiet.setDividerLocation(200);
        splitPaneHoaDonChiTiet.setResizeWeight(0.5);

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPaneHoaDonChiTiet, scrollPaneSanPham);
        mainSplitPane.setDividerLocation(400);
        mainSplitPane.setResizeWeight(0.7);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        btnThem = createStyledButton("Thêm mới", new Color(34, 139, 34));
        btnSua = createStyledButton("Cập nhật", Color.ORANGE);
        btnXoa = createStyledButton("Xóa", Color.RED);

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);

        // Add components
        add(searchPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.WEST);
        add(mainSplitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        btnThem.addActionListener(e -> new ThemHoaDonDialog((Frame) SwingUtilities.getWindowAncestor(this), this).setVisible(true));
        btnSua.addActionListener(e -> suaHoaDon());
        btnXoa.addActionListener(e -> xoaHoaDon());
        btnTimKiem.addActionListener(e -> timKiemHoaDon());
        btnLamMoi.addActionListener(e -> lamMoi());

        tableHoaDon.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tableHoaDon.getSelectedRow() != -1) {
                int row = tableHoaDon.convertRowIndexToModel(tableHoaDon.getSelectedRow());
                txtMaHD.setText(tableModelHoaDon.getValueAt(row, 0).toString());
                txtMaKH.setText(tableModelHoaDon.getValueAt(row, 1).toString());
                txtMaNV.setText(tableModelHoaDon.getValueAt(row, 2).toString());
                txtNgayLap.setText(tableModelHoaDon.getValueAt(row, 3).toString());
                txtTongTien.setText(tableModelHoaDon.getValueAt(row, 4).toString());
                loadChiTietHoaDon(Integer.parseInt(txtMaHD.getText()));
            }
        });
    }

    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        return btn;
    }

    private void loadDanhSachSanPham() {
        tableModelSanPham.setRowCount(0);
        List<SanPham> danhSachSanPham = sanPhamBUS.getAll();
        for (SanPham sp : danhSachSanPham) {
            tableModelSanPham.addRow(new Object[]{
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getSoLuong(),
                    sp.getDonGia(),
                    sp.getDonViTienTe(),
                    sp.getMaLoai(),
                    sp.getXuatXu()
            });
        }
    }

    private void loadChiTietHoaDon(int maHD) {
        tableModelChiTiet.setRowCount(0);
        List<ChiTietHoaDon> chiTietList = chiTietHoaDonBUS.getChiTietByMaHD(maHD);
        for (ChiTietHoaDon cthd : chiTietList) {
            SanPham sp = sanPhamBUS.timTheoMa(cthd.getMaSP());
            String tenSP = sp != null ? sp.getTenSP() : "Không tìm thấy";
            tableModelChiTiet.addRow(new Object[]{
                    cthd.getMaSP(),
                    tenSP,
                    cthd.getSoLuong(),
                    cthd.getDonGia(),
                    cthd.getThanhTien()
            });
        }
    }

    private void lamMoi() {
        txtTimKiem.setText("");
        loadHoaDonList();
        loadDanhSachSanPham();
        tableModelChiTiet.setRowCount(0);
    }

    public void loadHoaDonList() {
        tableModelHoaDon.setRowCount(0);
        List<HoaDon> hoaDonList = hoaDonBUS.getAllHoaDon();
        for (HoaDon hd : hoaDonList) {
            LocalDate ngayLap = hd.getNgayLap().toLocalDate();
            tableModelHoaDon.addRow(new Object[]{
                    hd.getMaHD(),
                    hd.getMaKH(),
                    hd.getMaNV(),
                    ngayLap.format(formatter),
                    hd.getTongTien()
            });
        }
        sorter.setRowFilter(null);
    }

    private void suaHoaDon() {
        int row = tableHoaDon.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = tableHoaDon.convertRowIndexToModel(row);
        try {
            hoaDonBUS.suaHoaDon(
                    Integer.parseInt(txtMaHD.getText()),
                    txtMaKH.getText(),
                    txtMaNV.getText(),
                    txtNgayLap.getText(),
                    txtTongTien.getText()
            );
            JOptionPane.showMessageDialog(this, "Sửa hóa đơn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadHoaDonList();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaHoaDon() {
        int row = tableHoaDon.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = tableHoaDon.convertRowIndexToModel(row);
        int maHD = Integer.parseInt(tableModelHoaDon.getValueAt(modelRow, 0).toString());
        try {
            hoaDonBUS.xoaHoaDon(maHD);
            JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadHoaDonList();
            loadDanhSachSanPham();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void timKiemHoaDon() {
        String keyword = txtTimKiem.getText().trim();
        if (keyword.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword));
        }
    }

    private void clearFields() {
        txtMaHD.setText("");
        txtMaKH.setText("");
        txtMaNV.setText("");
        txtNgayLap.setText("");
        txtTongTien.setText("");
        tableModelChiTiet.setRowCount(0);
    }
}