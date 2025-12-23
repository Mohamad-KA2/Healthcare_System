package View;

import Model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GUIPrescription extends JFrame {
    private JTable prescriptionTable;
    private DefaultTableModel tableModel;
    private PrescritpionManager prescriptionManager;
    private List<Prescription> prescriptions;
    
    public GUIPrescription() {
        prescriptionManager = PrescritpionManager.getInstance();
        loadData();
        
        setTitle("Prescriptions Management");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        String[] columns = {"ID", "Patient ID", "Medication", "Dosage", "Frequency", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        prescriptionTable = new JTable(tableModel);
        refreshTable();
        
        JScrollPane scrollPane = new JScrollPane(prescriptionTable);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Prescription");
        JButton editButton = new JButton("Edit Status");
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");
        
        addButton.addActionListener(e -> addPrescription());
        editButton.addActionListener(e -> editPrescription());
        deleteButton.addActionListener(e -> deletePrescription());
        backButton.addActionListener(e -> {
            new GUIMain();
            dispose();
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private void loadData() {
        String basePath = "/Users/mohamadka/Desktop/Healthcare System/Healthcare_System/";
        prescriptions = Loader.loadPrescriptions(basePath + "prescriptions.csv");
        for (Prescription p : prescriptions) {
            prescriptionManager.create_prescription(p);
        }
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Prescription p : prescriptions) {
            tableModel.addRow(new Object[]{
                p.getPrescription_id(), p.getPatient_id(), p.getMedication_name(),
                p.getDosage(), p.getFrequency(), p.getStatus()
            });
        }
    }
    
    private void addPrescription() {
        JDialog dialog = new JDialog(this, "Add Prescription", true);
        dialog.setLayout(new GridLayout(7, 2, 5, 5));
        dialog.setSize(400, 300);
        
        JTextField idField = new JTextField();
        JTextField patientIdField = new JTextField();
        JTextField clinicianIdField = new JTextField();
        JTextField medicationField = new JTextField();
        JTextField dosageField = new JTextField();
        JTextField frequencyField = new JTextField();
        
        dialog.add(new JLabel("Prescription ID:"));
        dialog.add(idField);
        dialog.add(new JLabel("Patient ID:"));
        dialog.add(patientIdField);
        dialog.add(new JLabel("Clinician ID:"));
        dialog.add(clinicianIdField);
        dialog.add(new JLabel("Medication:"));
        dialog.add(medicationField);
        dialog.add(new JLabel("Dosage:"));
        dialog.add(dosageField);
        dialog.add(new JLabel("Frequency:"));
        dialog.add(frequencyField);
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                Date presDate = new Date(2025, 12, 12);
                Prescription newPrescription = new Prescription(
                    idField.getText(), patientIdField.getText(), clinicianIdField.getText(),
                    "", presDate, medicationField.getText(), dosageField.getText(),
                    frequencyField.getText(), 7, "TBD", "Take as directed",
                    "Local Pharmacy", "Issued", presDate, null
                );
                prescriptionManager.create_prescription(newPrescription);
                prescriptions.add(newPrescription);
                refreshTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Prescription added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        
        dialog.add(saveButton);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void editPrescription() {
        int selectedRow = prescriptionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a prescription");
            return;
        }
        
        String prescriptionId = (String) tableModel.getValueAt(selectedRow, 0);
        Prescription prescription = prescriptionManager.get_prescription_by_id(prescriptionId);
        
        if (prescription == null) return;
        
        String[] statuses = {"Issued", "Collected", "Cancelled"};
        String newStatus = (String) JOptionPane.showInputDialog(this,
            "Select new status:", "Edit Status",
            JOptionPane.QUESTION_MESSAGE, null, statuses, prescription.getStatus());
            
        if (newStatus != null) {
            prescription.setStatus(newStatus);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Status updated!");
        }
    }
    
    private void deletePrescription() {
        int selectedRow = prescriptionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a prescription");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete this prescription?", "Confirm", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            String prescriptionId = (String) tableModel.getValueAt(selectedRow, 0);
            prescriptions.removeIf(p -> p.getPrescription_id().equals(prescriptionId));
            refreshTable();
            JOptionPane.showMessageDialog(this, "Prescription deleted!");
        }
    }
}
