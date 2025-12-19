package View;

import javax.swing.*;
import java.awt.*;

public class GUIAdmin extends JFrame {
    private String currentUserId;
    
    public GUIAdmin(String userId) {
        this.currentUserId = userId;
        setTitle("Admin Portal");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        JButton patientsButton = new JButton("Manage Patients");
        JButton appointmentsButton = new JButton("Manage Appointments");
        JButton prescriptionsButton = new JButton("Manage Prescriptions");
        JButton referralsButton = new JButton("Manage Referrals");
        JButton backButton = new JButton("Back to Main");
        
        patientsButton.addActionListener(e -> {
            new GUIPatient(currentUserId);
            dispose();
        });
        
        appointmentsButton.addActionListener(e -> {
            new GUIAppointments();
            dispose();
        });
        
        prescriptionsButton.addActionListener(e -> {
            new GUIPrescription();
            dispose();
        });
        
        referralsButton.addActionListener(e -> {
            new GUIReferral(currentUserId);
            dispose();
        });
        
        backButton.addActionListener(e -> {
            new GUIMain();
            dispose();
        });
        
        panel.add(titleLabel);
        panel.add(patientsButton);
        panel.add(appointmentsButton);
        panel.add(prescriptionsButton);
        panel.add(referralsButton);
        panel.add(backButton);
        
        add(panel);
        setVisible(true);
    }
}
