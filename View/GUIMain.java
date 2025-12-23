package View;

import javax.swing.*;
import java.awt.*;

public class GUIMain extends JFrame {
    
    public GUIMain() {
        setTitle("Healthcare Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Healthcare Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        JButton loginButton = new JButton("Login");
        JButton patientButton = new JButton("Patient Portal");
        JButton adminButton = new JButton("Admin Portal");
        
        loginButton.addActionListener(e -> {
            new GUILogin();
            dispose();
        });
        
        patientButton.addActionListener(e -> {
            new GUIPatient("GUEST");
            dispose();
        });
        
        adminButton.addActionListener(e -> {
            new GUIAdmin("ADMIN");
            dispose();
        });
        
        panel.add(titleLabel);
        panel.add(loginButton);
        panel.add(patientButton);
        panel.add(adminButton);
        
        add(panel);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUIMain());
    }
}
