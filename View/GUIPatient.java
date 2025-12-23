package View;

import Model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GUIPatient extends JFrame {
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private AppointmentManager appointmentManager;
    private String currentUserId;
    
    public GUIPatient(String userId) {
        this.currentUserId = userId;
        appointmentManager = AppointmentManager.getInstance();
        loadData();
        
        setTitle("Patient Portal");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        String[] columns = {"ID", "First Name", "Last Name", "Gender", "Phone", "Email"};
        tableModel = new DefaultTableModel(columns, 0);
        patientTable = new JTable(tableModel);
        refreshTable();
        
        JScrollPane scrollPane = new JScrollPane(patientTable);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Patient");
        JButton viewButton = new JButton("View Details");
        JButton editButton = new JButton("Edit Patient");
        JButton deleteButton = new JButton("Delete Patient");
        JButton backButton = new JButton("Back to Main");
        
        if (!currentUserId.startsWith("A")) {
            deleteButton.setEnabled(false);
            deleteButton.setToolTipText("Only Admins can delete patients");
        }
        
        addButton.addActionListener(e -> addPatient());
        viewButton.addActionListener(e -> viewPatient());
        editButton.addActionListener(e -> editPatient());
        deleteButton.addActionListener(e -> deletePatient());
        backButton.addActionListener(e -> {
            new GUIMain();
            dispose();
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private void loadData() {
        String basePath = "/Users/mohamadka/Desktop/Healthcare_git/";
        List<Patient> patients = Loader.loadPatients(basePath + "patients.csv");
        for (Patient p : patients) {
            appointmentManager.register_patient(p);
        }
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Patient> patients = appointmentManager.view_all_patients();
        for (Patient p : patients) {
            tableModel.addRow(new Object[]{
                p.getUser_id(), p.getFirst_name(), p.getLast_name(),
                p.getGender(), p.getPhone_number(), p.getEmail()
            });
        }
    }
    
    private void addPatient() {
        JDialog dialog = new JDialog(this, "Add Patient", true);
        dialog.setLayout(new GridLayout(14, 2, 5, 5));
        dialog.setSize(450, 550);
        
        JTextField idField = new JTextField();
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField postcodeField = new JTextField();
        JTextField nhsField = new JTextField();
        JTextField dobField = new JTextField("1990-01-01");
        JTextField emergencyNameField = new JTextField();
        JTextField emergencyPhoneField = new JTextField();
        JTextField gpSurgeryField = new JTextField("S001");
        
        dialog.add(new JLabel("Patient ID:"));
        dialog.add(idField);
        dialog.add(new JLabel("First Name:"));
        dialog.add(firstNameField);
        dialog.add(new JLabel("Last Name:"));
        dialog.add(lastNameField);
        dialog.add(new JLabel("Gender (M/F):"));
        dialog.add(genderField);
        dialog.add(new JLabel("Phone:"));
        dialog.add(phoneField);
        dialog.add(new JLabel("Email:"));
        dialog.add(emailField);
        dialog.add(new JLabel("Address:"));
        dialog.add(addressField);
        dialog.add(new JLabel("Postcode:"));
        dialog.add(postcodeField);
        dialog.add(new JLabel("NHS Number:"));
        dialog.add(nhsField);
        dialog.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        dialog.add(dobField);
        dialog.add(new JLabel("Emergency Contact Name:"));
        dialog.add(emergencyNameField);
        dialog.add(new JLabel("Emergency Contact Phone:"));
        dialog.add(emergencyPhoneField);
        dialog.add(new JLabel("GP Surgery ID:"));
        dialog.add(gpSurgeryField);
        
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            try {
                Date dob = new Date(dobField.getText());
                Date regDate = new Date(java.time.LocalDate.now());
                long nhsNumber = Long.parseLong(nhsField.getText());
                long emergencyPhone = Long.parseLong(emergencyPhoneField.getText());
                
                Patient newPatient = new Patient(
                    idField.getText(), 
                    firstNameField.getText(), 
                    lastNameField.getText(),
                    phoneField.getText(), 
                    emailField.getText(), 
                    dob, 
                    nhsNumber,
                    genderField.getText(), 
                    addressField.getText(), 
                    postcodeField.getText(),
                    emergencyNameField.getText(), 
                    emergencyPhone, 
                    regDate, 
                    gpSurgeryField.getText()
                );
                appointmentManager.register_patient(newPatient);
                refreshTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Patient added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        dialog.add(saveButton);
        dialog.add(cancelButton);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void viewPatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient");
            return;
        }
        
        String patientId = (String) tableModel.getValueAt(selectedRow, 0);
        Patient patient = appointmentManager.find_patient(patientId);
        
        if (patient != null) {
            String details = String.format(
                "Patient Details:\n\nID: %s\nName: %s %s\nGender: %s\nPhone: %s\nEmail: %s\nAddress: %s\nPostcode: %s",
                patient.getUser_id(), patient.getFirst_name(), patient.getLast_name(),
                patient.getGender(), patient.getPhone_number(), patient.getEmail(),
                patient.getAdress(), patient.getPostcode()
            );
            JOptionPane.showMessageDialog(this, details);
        }
    }
    
    private void editPatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to edit");
            return;
        }
        
        String patientId = (String) tableModel.getValueAt(selectedRow, 0);
        Patient patient = appointmentManager.find_patient(patientId);
        
        if (patient == null) return;
        
        JDialog dialog = new JDialog(this, "Edit Patient", true);
        dialog.setLayout(new GridLayout(9, 2, 5, 5));
        dialog.setSize(400, 400);
        
        JTextField firstNameField = new JTextField(patient.getFirst_name());
        JTextField lastNameField = new JTextField(patient.getLast_name());
        JTextField genderField = new JTextField(patient.getGender());
        JTextField phoneField = new JTextField(patient.getPhone_number());
        JTextField emailField = new JTextField(patient.getEmail());
        JTextField addressField = new JTextField(patient.getAdress());
        JTextField postcodeField = new JTextField(patient.getPostcode());
        
        dialog.add(new JLabel("First Name:"));
        dialog.add(firstNameField);
        dialog.add(new JLabel("Last Name:"));
        dialog.add(lastNameField);
        dialog.add(new JLabel("Gender:"));
        dialog.add(genderField);
        dialog.add(new JLabel("Phone:"));
        dialog.add(phoneField);
        dialog.add(new JLabel("Email:"));
        dialog.add(emailField);
        dialog.add(new JLabel("Address:"));
        dialog.add(addressField);
        dialog.add(new JLabel("Postcode:"));
        dialog.add(postcodeField);
        
        JButton saveButton = new JButton("Save Changes");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            try {
                patient.setFirst_name(firstNameField.getText());
                patient.setLast_name(lastNameField.getText());
                patient.setGender(genderField.getText());
                patient.setPhone_number(phoneField.getText());
                patient.setEmail(emailField.getText());
                patient.setAdress(addressField.getText());
                patient.setPostcode(postcodeField.getText());
                
                appointmentManager.change_patient_information(patientId, patient);
                refreshTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Patient updated successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        dialog.add(saveButton);
        dialog.add(cancelButton);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void deletePatient() {
        if (!currentUserId.startsWith("A")) {
            JOptionPane.showMessageDialog(this, 
                "Access Denied: Only Admins can delete patient records.",
                "Permission Denied", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to delete");
            return;
        }
        
        String patientId = (String) tableModel.getValueAt(selectedRow, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete patient " + patientId + "?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                appointmentManager.cancel_appointment(patientId);
                refreshTable();
                JOptionPane.showMessageDialog(this, "Patient deleted successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }
}
