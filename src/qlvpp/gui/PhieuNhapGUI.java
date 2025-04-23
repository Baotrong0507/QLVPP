package qlvpp.gui;

import qlvpp.model.PhieuNhap;
import qlvpp.bus.PhieuNhapBUS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class PhieuNhapGUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;
    private PhieuNhapBUS bus = new PhieuNhapBUS();

    public PhieuNhapGUI() {
        setLayout(new BorderLayout());

        // Bảng dữ liệu
        model = new DefaultTableModel(new Object[]{"Mã PN", "Mã NV", "Mã NCC", "Ngày nhập", "Tổng tiền", ""}, 0);
        table = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Chỉ cột nút "..." cho phép tương tác
            }
        };

        table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);

        // Thanh tìm kiếm
        JPanel topPanel = new JPanel(new BorderLayout());
        txtSearch = new JTextField();
        JButton btnSearch = new JButton("Tìm");
        topPanel.add(txtSearch, BorderLayout.CENTER);
        topPanel.add(btnSearch, BorderLayout.EAST);

        // Các nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Thêm");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnRefresh = new JButton("Lưu/Tải lại");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadData(bus.getAll());

        // Sự kiện
        btnSearch.addActionListener(e -> {
            String keyword = txtSearch.getText().trim();
            loadData(bus.search(keyword));
        });

        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            loadData(bus.getAll());
        });

        btnAdd.addActionListener(e -> {
            ChiTietPhieuNhapForm form = new ChiTietPhieuNhapForm(null, "Thêm phiếu nhập", null);
            form.setVisible(true);
            PhieuNhap pn = form.getPhieuNhap();
            if (pn != null) {
                if (bus.add(pn)) {
                    loadData(bus.getAll());
                } else {
                    JOptionPane.showMessageDialog(this, "Mã phiếu nhập bị trùng!");
                }
            }
        });

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int maPN = (int) model.getValueAt(row, 0);
PhieuNhap old = bus.getAll().stream().filter(p -> p.getMaPN() == maPN).findFirst().orElse(null);
                ChiTietPhieuNhapForm form = new ChiTietPhieuNhapForm(null, "Sửa phiếu nhập", old);
                form.setVisible(true);
                PhieuNhap pn = form.getPhieuNhap();
                if (pn != null) {
                    bus.update(pn);
                    loadData(bus.getAll());
                }
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int maPN = (int) model.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?");
                if (confirm == JOptionPane.YES_OPTION) {
                    bus.delete(maPN);
                    loadData(bus.getAll());
                }
            }
        });
    }

    private void loadData(List<PhieuNhap> list) {
        model.setRowCount(0);
        for (PhieuNhap pn : list) {
            model.addRow(new Object[]{
                pn.getMaPN(),
                pn.getMaNV(),
                pn.getMaNCC(),
                pn.getNgayNhap(),
                pn.getTongTien(),
                "..."
            });
        }
    }

    // Nút "..." mở chi tiết
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setText("...");
        }

        public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus,int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("...");
            button.addActionListener(e -> {
                int maPN = (int) model.getValueAt(row, 0);
                new ChiTietPhieuNhapGUI(maPN).setVisible(true);
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,boolean isSelected, int row, int column) {
            this.row = row;
            return button;
        }
    }
}