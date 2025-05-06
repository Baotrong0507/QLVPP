/*
package qlvpp.gui;

     import javax.swing.*;
     import java.awt.*;

     public class TrangChuGUI extends JPanel {

         private JButton btnThongKe;
         private MainJFrame parentFrame;

         public TrangChuGUI(MainJFrame parentFrame) {
             this.parentFrame = parentFrame;
             setLayout(new BorderLayout());
             setBackground(Color.WHITE);
             setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

             // Panel chứa các nút (phía trên)
             JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
             buttonPanel.setBackground(Color.WHITE);

             // Nút Thống kê
             btnThongKe = new JButton("Thống kê");
             btnThongKe.setIcon(new ImageIcon("src/qlvpp/images/piechart.png"));
             btnThongKe.setBackground(new Color(0, 120, 215));
             btnThongKe.setForeground(Color.WHITE);
             btnThongKe.setFocusPainted(false);
             btnThongKe.setFont(new Font("Segoe UI", Font.PLAIN, 16));
             btnThongKe.setHorizontalAlignment(SwingConstants.LEFT);
             btnThongKe.setIconTextGap(10);
             btnThongKe.addActionListener(e -> parentFrame.switchPanelFromChild(new ThongKeGUI()));
             buttonPanel.add(btnThongKe);

             // Nút phụ 1 (placeholder)
             JButton btnPlaceholder1 = new JButton("Chưa triển khai");
             //btnPlaceholder1.setIcon(new ImageIcon("src/qlvpp/images/placeholder.png"));
             btnPlaceholder1.setBackground(new Color(100, 100, 100));
             btnPlaceholder1.setForeground(Color.WHITE);
             btnPlaceholder1.setFocusPainted(false);
             btnPlaceholder1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
             btnPlaceholder1.setHorizontalAlignment(SwingConstants.LEFT);
             btnPlaceholder1.setIconTextGap(10);
             buttonPanel.add(btnPlaceholder1);

             // Nút phụ 2 (placeholder)
             JButton btnPlaceholder2 = new JButton("Chưa triển khai");
             //btnPlaceholder2.setIcon(new ImageIcon("src/qlvpp/images/placeholder.png"));
             btnPlaceholder2.setBackground(new Color(100, 100, 100));
             btnPlaceholder2.setForeground(Color.WHITE);
             btnPlaceholder2.setFocusPainted(false);
             btnPlaceholder2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
             btnPlaceholder2.setHorizontalAlignment(SwingConstants.LEFT);
             btnPlaceholder2.setIconTextGap(10);
             buttonPanel.add(btnPlaceholder2);

             // Nút phụ 3 (placeholder)
             JButton btnPlaceholder3 = new JButton("Chưa triển khai");
             //btnPlaceholder3.setIcon(new ImageIcon("src/qlvpp/images/placeholder.png"));
             btnPlaceholder3.setBackground(new Color(100, 100, 100));
             btnPlaceholder3.setForeground(Color.WHITE);
             btnPlaceholder3.setFocusPainted(false);
             btnPlaceholder3.setFont(new Font("Segoe UI", Font.PLAIN, 16));
             btnPlaceholder3.setHorizontalAlignment(SwingConstants.LEFT);
             btnPlaceholder3.setIconTextGap(10);
             buttonPanel.add(btnPlaceholder3);

             add(buttonPanel, BorderLayout.NORTH);

             // Khu vực để chèn hình minh họa (phía dưới)
             JLabel lblIllustration = new JLabel("", SwingConstants.CENTER);
             lblIllustration.setIcon(new ImageIcon("src/qlvpp/images/home_illustration.png"));
             add(lblIllustration, BorderLayout.CENTER);
         }
     }
*/