package View;

import Model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GUIReferral extends JFrame {
    private JTable referralTable;
    private DefaultTableModel tableModel;
    private RefferalManager referralManager;
    private List<Refferal> referrals;
    private String currentUserId;
    private static final String BASE_PATH = "Healthcare_System/";
    
    public GUIReferral(String userId) {
        this.currentUserId = userId;
        referralManager = RefferalManager.getInstance();
        loadData();
        
        setTitle("Referrals Management");
        setSize(1600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        String[] columns = {"ID", "Patient ID", "Referring Clinician", "Referred To Clinician", 
                            "Referring Facility", "Referred To Facility", "Referral Date", 
                            "Urgency", "Reason", "Clinical Summary", "Investigations", "Status",
                            "Appointment ID", "Notes", "Created Date", "Last Updated"};
        tableModel = new DefaultTableModel(columns, 0);
        referralTable = new JTable(tableModel);
        refreshTable();
        
        JScrollPane scrollPane = new JScrollPane(referralTable);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Referral");
        JButton viewButton = new JButton("View Details");
        JButton editButton = new JButton("Edit Referral");
        JButton deleteButton = new JButton("Delete Referral");
        JButton backButton = new JButton("Back");
        
        if (currentUserId.startsWith("C")) {
            List<ClinicalStaff> clinicians = Loader.loadClinicians(BASE_PATH + "clinicians.csv");
            boolean isGP = false;
            for (ClinicalStaff clinician : clinicians) {
                if (clinician.getUser_id().equals(currentUserId) && 
                    clinician.getTitle().equalsIgnoreCase("GP")) {
                    isGP = true;
                    break;
                }
            }
            if (!isGP) {
                addButton.setEnabled(false);
                addButton.setToolTipText("Only GPs can create referrals");
            }
        } else if (!currentUserId.startsWith("A")) {
            addButton.setEnabled(false);
            addButton.setToolTipText("Only GPs can create referrals");
        }
        
        if (!currentUserId.startsWith("A")) {
            deleteButton.setEnabled(false);
            deleteButton.setToolTipText("Only Admins can delete referrals");
        }
        
        addButton.addActionListener(e -> addReferral());
        viewButton.addActionListener(e -> viewReferral());
        editButton.addActionListener(e -> editReferral());
        deleteButton.addActionListener(e -> deleteReferral());
        backButton.addActionListener(e -> {
            new GUIAdmin(currentUserId);
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
        String basePath = "Healthcare_System/";
        referrals = Loader.loadReferrals(basePath + "referrals.csv");
        for (Refferal r : referrals) {
            referralManager.create_referral(r);
        }
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Refferal r : referrals) {
            tableModel.addRow(new Object[]{
                r.getReferral_id(), 
                r.getPatient_id(),
                r.getReferring_clnician_id(), 
                r.getReferred_to_clinician_id(),
                r.getReferring_facility_id(),
                r.getReferred_to_facility_id(),
                r.getReferred_date() != null ? r.getReferred_date().toString() : "",
                r.getUrgency_level(),
                r.getReferral_reason(),
                r.getClinical_summary(),
                r.getRequested_investigations(),
                r.getStatus(),
                r.getAppointment_id(),
                r.getNotes(),
                r.getCreated_date() != null ? r.getCreated_date().toString() : "",
                r.getLast_updated() != null ? r.getLast_updated().toString() : ""
            });
        }
    }
    
    private void addReferral() {
        if (currentUserId.startsWith("C")) {
            List<ClinicalStaff> clinicians = Loader.loadClinicians(BASE_PATH + "clinicians.csv");
            boolean isGP = false;
            for (ClinicalStaff clinician : clinicians) {
                if (clinician.getUser_id().equals(currentUserId) && 
                    clinician.getTitle().equalsIgnoreCase("GP")) {
                    isGP = true;
                    break;
                }
            }
            if (!isGP) {
                JOptionPane.showMessageDialog(this, 
                    "Access Denied: Only GPs can create referrals.",
                    "Permission Denied", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else if (!currentUserId.startsWith("A")) {
            JOptionPane.showMessageDialog(this, 
                "Access Denied: Only GPs can create referrals.",
                "Permission Denied", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JDialog dialog = new JDialog(this, "Add Referral", true);
        dialog.setLayout(new GridLayout(8, 2, 5, 5));
        dialog.setSize(400, 350);
        
        JTextField idField = new JTextField();
        JTextField patientIdField = new JTextField();
        JTextField referringField = new JTextField(currentUserId);
        JTextField referredToField = new JTextField();
        JTextField reasonField = new JTextField();
        JTextField urgencyField = new JTextField("Routine");
        JTextArea summaryArea = new JTextArea(3, 20);
        
        dialog.add(new JLabel("Referral ID:"));
        dialog.add(idField);
        dialog.add(new JLabel("Patient ID:"));
        dialog.add(patientIdField);
        dialog.add(new JLabel("Referring Clinician:"));
        dialog.add(referringField);
        dialog.add(new JLabel("Referred To:"));
        dialog.add(referredToField);
        dialog.add(new JLabel("Reason:"));
        dialog.add(reasonField);
        dialog.add(new JLabel("Urgency:"));
        dialog.add(urgencyField);
        dialog.add(new JLabel("Summary:"));
        dialog.add(new JScrollPane(summaryArea));
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                Date refDate = new Date(java.time.LocalDate.now());
                Refferal newReferral = new Refferal(
                    idField.getText(), patientIdField.getText(),
                    referringField.getText(), referredToField.getText(),
                    "S001", "H001", refDate, urgencyField.getText(),
                    reasonField.getText(), summaryArea.getText(),
                    "", "New", "", "", refDate, refDate
                );
                referralManager.create_referral(newReferral);
                referrals.add(newReferral);
                Loader.saveReferral(BASE_PATH + "referrals.csv", newReferral);
                refreshTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Referral added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        
        dialog.add(saveButton);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void viewReferral() {
        int selectedRow = referralTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a referral");
            return;
        }
        
        String referralId = (String) tableModel.getValueAt(selectedRow, 0);
        Refferal referral = referralManager.get_referral_by_id(referralId);
        
        if (referral != null) {
            String details = String.format(
                "Referral Details:\n\nID: %s\nPatient: %s\nReferring: %s\nReferred To: %s\nReason: %s\nUrgency: %s\nSummary: %s",
                referral.getReferral_id(), referral.getPatient_id(),
                referral.getReferring_clnician_id(), referral.getReferred_to_clinician_id(),
                referral.getReferral_reason(), referral.getUrgency_level(),
                referral.getClinical_summary()
            );
            JOptionPane.showMessageDialog(this, details);
        }
    }
    
    private void editReferral() {
        int selectedRow = referralTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a referral to edit");
            return;
        }
        
        String referralId = (String) tableModel.getValueAt(selectedRow, 0);
        Refferal referral = referralManager.get_referral_by_id(referralId);
        
        if (referral == null) return;
        
        JDialog dialog = new JDialog(this, "Edit Referral", true);
        dialog.setLayout(new GridLayout(4, 2, 5, 5));
        dialog.setSize(400, 200);
        
        JTextField urgencyField = new JTextField(referral.getUrgency_level());
        JTextField reasonField = new JTextField(referral.getReferral_reason());
        JTextArea summaryArea = new JTextArea(referral.getClinical_summary());
        summaryArea.setLineWrap(true);
        JScrollPane summaryScroll = new JScrollPane(summaryArea);
        
        dialog.add(new JLabel("Urgency Level:"));
        dialog.add(urgencyField);
        dialog.add(new JLabel("Reason:"));
        dialog.add(reasonField);
        dialog.add(new JLabel("Clinical Summary:"));
        dialog.add(summaryScroll);
        
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            try {
                referral.setUrgency_level(urgencyField.getText());
                referral.setReferral_reason(reasonField.getText());
                referral.setClinical_summary(summaryArea.getText());
                
                referralManager.update_referral(referralId, referral);
                refreshTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Referral updated successfully!");
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
    
    private void deleteReferral() {
        if (!currentUserId.startsWith("A")) {
            JOptionPane.showMessageDialog(this, 
                "Access Denied: Only Admins can delete referrals.",
                "Permission Denied", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int selectedRow = referralTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a referral to delete");
            return;
        }
        
        String referralId = (String) tableModel.getValueAt(selectedRow, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete referral " + referralId + "?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                referrals.removeIf(r -> r.getReferral_id().equals(referralId));
                refreshTable();
                JOptionPane.showMessageDialog(this, "Referral deleted successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
