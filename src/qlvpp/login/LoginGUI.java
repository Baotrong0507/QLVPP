package qlvpp.login;

import qlvpp.gui.MainJFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginGUI() {
        setTitle("Đăng nhập - Bán Văn Phòng Phẩm");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Load hình nền
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("/qlvpp/images/bg_login.png")));
        background.setBounds(0, 0, 400, 500);
        setContentPane(background);
        background.setLayout(null);

        // Panel trắng chứa form
        JPanel panel = new JPanel();
        panel.setBounds(50, 100, 300, 280);
        panel.setBackground(new Color(255, 255, 255, 220));
        panel.setLayout(null);
        background.add(panel);

        JLabel lblTitle = new JLabel("My Account", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setBounds(0, 10, 300, 30);
        panel.add(lblTitle);

        JLabel lblUser = new JLabel("Login:");
        lblUser.setBounds(20, 60, 80, 25);
        panel.add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(100, 60, 160, 25);
        panel.add(txtUsername);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(20, 100, 80, 25);
        panel.add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(100, 100, 160, 25);
        panel.add(txtPassword);

        JButton btnLogin = new JButton("Sign In");
        btnLogin.setBounds(60, 150, 100, 30);
        panel.add(btnLogin);

        JButton btnExit = new JButton("Exist");
        btnExit.setBounds(175, 150, 100, 30);
        panel.add(btnExit);
        // Xử lý đăng nhập
        btnExit.addActionListener(e -> System.exit(0));
        
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = txtUsername.getText();
                String pass = new String(txtPassword.getPassword());

                if (user.equals("admin") && pass.equals("123456")) {
                    JOptionPane.showMessageDialog(null, "Đăng nhập thành công!");
                    dispose(); // tắt màn hình login
                    // mở giao diện chính tại đây, ví dụ:
                     new MainJFrame().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Sai tài khoản hoặc mật khẩu!");
                }
            }
        });
    }
}
