package qlvpp.gui;

import qlvpp.model.ChiTietPhieuNhap;
import qlvpp.bus.ChiTietPhieuNhapBUS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ChiTietPhieuNhapGUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private int maPN;

    public ChiTietPhieuNhapGUI(int maPN) {
        this.maPN = maPN;
        setTitle("Chi Tiết Phiếu Nhập: " + maPN);
        setSize(600, 400);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new Object[]{"Mã SP", "Số lượng", "Đơn giá", "Thành tiền"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();
    }

    private void loadData() {
        ChiTietPhieuNhapBUS bus = new ChiTietPhieuNhapBUS();
        List<ChiTietPhieuNhap> list = bus.getByMaPN(maPN);
        model.setRowCount(0);
        for (ChiTietPhieuNhap ct : list) {
            model.addRow(new Object[]{
                ct.getMaSP(),
                ct.getSoLuong(),
                ct.getGiaNhap(),
                ct.getThanhTien()
            });
        }
    }
}