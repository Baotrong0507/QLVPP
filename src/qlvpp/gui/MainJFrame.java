package qlvpp.gui;

import java.awt.*;
import javax.swing.*;
import qlvpp.login.LoginGUI;

public class MainJFrame extends JFrame {

    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private JLabel lblTitle;

    private JButton btnTrangChu;
    private JButton btnNhaCungCap;
    private JButton btnPhieuNhap;
    private JButton btnNhanVien;
    private JButton btnKhachHang;
    private JButton btnSanPham;
    private JButton btnKhuyenMai;
    private JButton btnHoaDon;
    private JButton btnThongKe;
    private JButton btnDangXuat;

    public MainJFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Quản Lý Văn Phòng Phẩm");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ------------------ Sidebar Menu ------------------
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new GridLayout(12, 1, 0, 10));
        sidebarPanel.setPreferredSize(new Dimension(220, 0));
        sidebarPanel.setBackground(new Color(30, 30, 60));

        // ------------------ Tạo nút menu ------------------
        btnTrangChu = createSidebarButton("Trang chủ", "src/qlvpp/images/home.png");
        btnNhaCungCap = createSidebarButton("Nhà Cung Cấp", "src/qlvpp/images/supplier.png");
        btnPhieuNhap = createSidebarButton("Phiếu Nhập", "src/qlvpp/images/import.png");
        btnNhanVien = createSidebarButton("Nhân Viên", "src/qlvpp/images/staff.png");
        btnKhachHang = createSidebarButton("Khách Hàng", "src/qlvpp/images/customer.png");
        btnSanPham = createSidebarButton("Sản Phẩm", "src/qlvpp/images/product.png");
        btnKhuyenMai = createSidebarButton("Chương Trình Khuyến Mãi", "src/qlvpp/images/promo.png");
        btnHoaDon = createSidebarButton("Hóa Đơn", "src/qlvpp/images/invoice.png");
        btnThongKe = createSidebarButton("Thống kê", "src/qlvpp/images/monitoring.png");
        btnDangXuat = createSidebarButton("Đăng xuất", "src/qlvpp/images/logout.png");

        // ------------------ Thêm nút vào sidebar ------------------
        sidebarPanel.add(btnTrangChu);
        sidebarPanel.add(btnNhaCungCap);
        sidebarPanel.add(btnPhieuNhap);
        sidebarPanel.add(btnNhanVien);
        sidebarPanel.add(btnKhachHang);
        sidebarPanel.add(btnSanPham);
        sidebarPanel.add(btnKhuyenMai);
        sidebarPanel.add(btnHoaDon);
        sidebarPanel.add(btnThongKe);
        sidebarPanel.add(btnDangXuat);

        // ------------------ Content Panel ------------------
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        lblTitle = new JLabel("QUẢN LÝ VĂN PHÒNG PHẨM", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(20, 20, 70));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        contentPanel.add(lblTitle, BorderLayout.NORTH);

        // ------------------ Add to Frame ------------------
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        // ------------------ Thêm ActionListener ------------------
        btnTrangChu.addActionListener(e -> switchPanel(new TrangChuGUI(this)));
        btnNhaCungCap.addActionListener(e -> switchPanel(new NhaCungCapGUI()));
        btnPhieuNhap.addActionListener(e -> switchPanel(new PhieuNhapGUI()));
        //btnNhanVien.addActionListener(e -> switchPanel(new NhanVienGUI()));
        //btnKhachHang.addActionListener(e -> switchPanel(new KhachHangGUI()));
        //btnSanPham.addActionListener(e -> switchPanel(new SanPhamGUI()));
        //btnKhuyenMai.addActionListener(e -> switchPanel(new KhuyenMaiGUI()));
        //btnHoaDon.addActionListener(e -> switchPanel(new HoaDonGUI()));
        btnThongKe.addActionListener(e -> switchPanel(new ThongKeGUI()));
        btnDangXuat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc muốn đăng xuất?", 
                "Xác nhận đăng xuất", 
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginGUI().setVisible(true);
            }
        });
    }

    private JButton createSidebarButton(String text, String iconPath) {
        JButton button = new JButton(text);
        try {
            button.setIcon(new ImageIcon(iconPath));
        } catch (Exception e) {
            System.err.println("Không thể tải biểu tượng: " + iconPath);
        }
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(45, 45, 80));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(15);
        return button;
    }

    private void switchPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(lblTitle, BorderLayout.NORTH);
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Phương thức công khai để TrangChuGUI gọi
    public void switchPanelFromChild(JPanel panel) {
        switchPanel(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainJFrame().setVisible(true));
    }
}