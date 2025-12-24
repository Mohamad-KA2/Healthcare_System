package View;

import Model.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUILogin extends JFrame {
    private static final String BASE_PATH = "Healthcare_System/";
    
    public GUILogin() {
        setTitle("Login");
        setSize(350, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel userLabel = new JLabel("Enter Your User ID:");
        JTextField userField = new JTextField();
        
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");
        
        loginButton.addActionListener(e -> {
            String userId = userField.getText().trim();
            
            if (userId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your User ID");
                return;
            }
            
            if (userId.startsWith("C")) {
                List<ClinicalStaff> clinicians = Loader.loadClinicians(BASE_PATH + "clinicians.csv");
                for (ClinicalStaff clinician : clinicians) {
                    if (clinician.getUser_id().equals(userId)) {
                        JOptionPane.showMessageDialog(this, 
                            "Welcome " + clinician.getFirst_name() + " " + clinician.getLast_name() + 
                            "\nRole: " + clinician.getTitle());
                        new GUIAdmin(userId);
                        dispose();
                        return;
                    }
                }
            }
            
            if (userId.startsWith("A")) {
                JOptionPane.showMessageDialog(this, "Welcome Admin");
                new GUIAdmin(userId);
                dispose();
                return;
            }
            
            if (userId.startsWith("P")) {
                List<Patient> patients = Loader.loadPatients(BASE_PATH + "patients.csv");
                for (Patient patient : patients) {
                    if (patient.getUser_id().equals(userId)) {
                        JOptionPane.showMessageDialog(this, 
                            "Welcome " + patient.getFirst_name() + " " + patient.getLast_name());
                        new GUIPatient(userId);
                        dispose();
                        return;
                    }
                }
            }
            
            JOptionPane.showMessageDialog(this, "User ID not found");
        });
        
        backButton.addActionListener(e -> {
            new GUIMain();
            dispose();
        });
        
        panel.add(userLabel);
        panel.add(userField);
        panel.add(loginButton);
        panel.add(backButton);
        
        add(panel);
        setVisible(true);
    }
}
