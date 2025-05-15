package qlvpp.gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.List;
import qlvpp.bus.NhanVienBUS;
import qlvpp.model.NhanVien;

public class NhanVienGUI extends JPanel {
    private NhanVienBUS nhanVienBUS;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaNV, txtHo, txtTen, txtDiaChi, txtLuongThang;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem;
    private DecimalFormat decimalFormat = new DecimalFormat("#,###");

    public NhanVienGUI() {
        nhanVienBUS = new NhanVienBUS();
        initComponents();
        loadNhanVienList();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        JLabel headerLabel = new JLabel("QUẢN LÝ NHÂN VIÊN");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // Input + Buttons
        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();

        // Table
        tableModel = new DefaultTableModel(new String[]{"Mã NV", "Họ", "Tên", "Địa Chỉ", "Lương Tháng"}, 0);
        table = new JTable(tableModel);
        customizeTable();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtMaNV.setText(tableModel.getValueAt(row, 0).toString());
                    txtHo.setText(tableModel.getValueAt(row, 1).toString());
                    txtTen.setText(tableModel.getValueAt(row, 2).toString());
                    txtDiaChi.setText(tableModel.getValueAt(row, 3).toString());
                    // Định dạng Lương Tháng bằng DecimalFormat trước khi hiển thị
                    Object luongThang = tableModel.getValueAt(row, 4);
                    if (luongThang instanceof Number) {
                        txtLuongThang.setText(decimalFormat.format(luongThang));
                    } else {
                        txtLuongThang.setText(luongThang.toString());
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(1, 1, 1, 1, new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Add to layout
        add(headerPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 15));
        Border titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150)),
                "Thông tin chi tiết", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14), new Color(70, 130, 180)
        );
        inputPanel.setBorder(BorderFactory.createCompoundBorder(titledBorder,
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        inputPanel.setBackground(Color.WHITE);

        Font labelFont = new Font("Arial", Font.PLAIN, 13);
        Font fieldFont = new Font("Arial", Font.PLAIN, 13);

        addInputField(inputPanel, "Mã Nhân Viên:", txtMaNV = createStyledTextField(), labelFont, fieldFont);
        addInputField(inputPanel, "Họ:", txtHo = createStyledTextField(), labelFont, fieldFont);
        addInputField(inputPanel, "Tên:", txtTen = createStyledTextField(), labelFont, fieldFont);
        addInputField(inputPanel, "Địa Chỉ:", txtDiaChi = createStyledTextField(), labelFont, fieldFont);
        addInputField(inputPanel, "Lương Tháng:", txtLuongThang = createStyledTextField(), labelFont, fieldFont);

        return inputPanel;
    }

    private void addInputField(JPanel panel, String labelText, JTextField field, Font labelFont, Font fieldFont) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setForeground(new Color(70, 70, 70));
        panel.add(label);

        field.setFont(fieldFont);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        panel.add(field);
    }

    private JTextField createStyledTextField() {
        JTextField tf = new JTextField();
        tf.setPreferredSize(new Dimension(200, 30));
        return tf;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(new Color(245, 245, 245));

        btnThem = createButton("Thêm", new Color(76, 175, 80), Color.WHITE);
        btnSua = createButton("Sửa", new Color(41, 128, 185), Color.WHITE);
        btnXoa = createButton("Xóa", new Color(192, 57, 43), Color.WHITE);
        btnTimKiem = createButton("Tìm Kiếm", new Color(241, 196, 15), Color.BLACK);

        btnThem.addActionListener(e -> themNhanVien());
        btnSua.addActionListener(e -> suaNhanVien());
        btnXoa.addActionListener(e -> xoaNhanVien());
        btnTimKiem.addActionListener(e -> timKiemNhanVien());

        panel.add(btnThem);
        panel.add(btnSua);
        panel.add(btnXoa);
        panel.add(btnTimKiem);

        return panel;
    }

    private JButton createButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(8, bg.darker()),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(bg.brighter());
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }

    private void customizeTable() {
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(new Color(220, 220, 220));
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(220, 240, 255));
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        header.setReorderingAllowed(false);

        // Định dạng số cho cột "Lương Tháng" và căn lề trái
        DefaultTableCellRenderer numberRenderer = new DefaultTableCellRenderer() {
            DecimalFormat formatter = new DecimalFormat("#,###");
            @Override
            public void setValue(Object value) {
                if (value instanceof Number) {
                    setText(formatter.format(value));
                } else {
                    setText(value == null ? "" : value.toString());
                }
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(LEFT);  // căn trái
                return c;
            }
        };
        // Cột lương là cột thứ 4 (index 4)
        table.getColumnModel().getColumn(4).setCellRenderer(numberRenderer);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 248, 248));
                if (isSelected) c.setBackground(new Color(220, 240, 255));
                setBorder(noFocusBorder);
                return c;
            }
        });
    }

    private void loadNhanVienList() {
        tableModel.setRowCount(0);
        List<NhanVien> list = nhanVienBUS.getAllNhanVien();
        for (NhanVien nv : list) {
            tableModel.addRow(new Object[]{
                    nv.getMaNV(), nv.getHo(), nv.getTen(), nv.getDiaChi(), nv.getLuongThang()
            });
        }
    }

    private void themNhanVien() {
        try {
            nhanVienBUS.themNhanVien(
                    txtMaNV.getText(),
                    txtHo.getText(),
                    txtTen.getText(),
                    txtDiaChi.getText(),
                    txtLuongThang.getText()
            );
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadNhanVienList();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaNhanVien() {
        try {
            nhanVienBUS.suaNhanVien(
                    txtMaNV.getText(),
                    txtHo.getText(),
                    txtTen.getText(),
                    txtDiaChi.getText(),
                    txtLuongThang.getText()
            );
            JOptionPane.showMessageDialog(this, "Sửa nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadNhanVienList();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaNhanVien() {
        try {
            nhanVienBUS.xoaNhanVien(txtMaNV.getText());
            JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadNhanVienList();
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void timKiemNhanVien() {
        try {
            List<NhanVien> result = nhanVienBUS.timKiemNhanVien(txtTen.getText());
            tableModel.setRowCount(0);
            for (NhanVien nv : result) {
                tableModel.addRow(new Object[]{
                        nv.getMaNV(), nv.getHo(), nv.getTen(), nv.getDiaChi(), nv.getLuongThang()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            loadNhanVienList(); // Tải lại danh sách nếu tìm kiếm thất bại
        }
    }

    private void clearFields() {
        txtMaNV.setText("");
        txtHo.setText("");
        txtTen.setText("");
        txtDiaChi.setText("");
        txtLuongThang.setText("");
    }

    private static class RoundedBorder extends AbstractBorder {
        private final int radius;
        private final Color color;

        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(color);
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
            g2.dispose();
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 2, radius);
        }

        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(radius, radius, radius, radius);
            return insets;
        }
    }
}