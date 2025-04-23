package qlvpp.main;
import javax.swing.SwingUtilities;
import qlvpp.login.LoginGUI;
public class Main {
     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGUI().setVisible(true));
    }
}
