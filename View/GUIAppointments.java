package View;

import Model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GUIAppointments extends JFrame {
    private JTable appointmentTable;
    private DefaultTableModel tableModel;
    private List<Appointment> appointments;
    
    public GUIAppointments() {
        loadData();
        
        setTitle("Appointments Management");
        setSize(1500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        String[] columns = {"ID", "Patient ID", "Clinician ID", "Facility ID", "Date", "Time", 
                            "Duration (min)", "Type", "Status", "Reason", "Notes", 
                            "Created Date", "Last Modified"};
        tableModel = new DefaultTableModel(columns, 0);
        appointmentTable = new JTable(tableModel);
        refreshTable();
        
        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Appointment");
        JButton editButton = new JButton("Edit Status");
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");
        
        addButton.addActionListener(e -> addAppointment());
        editButton.addActionListener(e -> editAppointment());
        deleteButton.addActionListener(e -> deleteAppointment());
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
        String basePath = "Healthcare_System/";
        appointments = Loader.loadAppointments(basePath + "appointments.csv");
    }
    
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Appointment a : appointments) {
            tableModel.addRow(new Object[]{
                a.getAppointment_id(), 
                a.getPatient_id(), 
                a.getClinician_id(),
                a.getFacility_id(),
                a.getAppointment_date() != null ? a.getAppointment_date().toString() : "",
                a.getAppointment_time(),
                a.getDuration_minutes(),
                a.getAppointment_type(), 
                a.getStatus(),
                a.getReason_for_visit(),
                a.getNotes(),
                a.getCreated_date() != null ? a.getCreated_date().toString() : "",
                a.getLast_modified() != null ? a.getLast_modified().toString() : ""
            });
        }
    }
    
    private void addAppointment() {
        JDialog dialog = new JDialog(this, "Add Appointment", true);
        dialog.setLayout(new GridLayout(7, 2, 5, 5));
        dialog.setSize(400, 300);
        
        JTextField idField = new JTextField();
        JTextField patientIdField = new JTextField();
        JTextField clinicianIdField = new JTextField();
        JTextField dateField = new JTextField("2025-12-12");
        JTextField timeField = new JTextField("10:00");
        JTextField typeField = new JTextField();
        
        dialog.add(new JLabel("Appointment ID:"));
        dialog.add(idField);
        dialog.add(new JLabel("Patient ID:"));
        dialog.add(patientIdField);
        dialog.add(new JLabel("Clinician ID:"));
        dialog.add(clinicianIdField);
        dialog.add(new JLabel("Date (YYYY-MM-DD):"));
        dialog.add(dateField);
        dialog.add(new JLabel("Time:"));
        dialog.add(timeField);
        dialog.add(new JLabel("Type:"));
        dialog.add(typeField);
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                Date appDate = new Date(dateField.getText());
                Date created = new Date(2025, 12, 12);
                Appointment newAppointment = new Appointment(
                    idField.getText(), patientIdField.getText(), clinicianIdField.getText(),
                    "S001", appDate, timeField.getText(), 30, typeField.getText(),
                    "Scheduled", "New appointment", "", created, created
                );
                appointments.add(newAppointment);
                refreshTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Appointment added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        
        dialog.add(saveButton);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void editAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment");
            return;
        }
        
        String appointmentId = (String) tableModel.getValueAt(selectedRow, 0);
        Appointment appointment = null;
        for (Appointment a : appointments) {
            if (a.getAppointment_id().equals(appointmentId)) {
                appointment = a;
                break;
            }
        }
        
        if (appointment == null) return;
        
        String[] statuses = {"Scheduled", "Completed", "Cancelled", "No-Show"};
        String newStatus = (String) JOptionPane.showInputDialog(this,
            "Select new status:", "Edit Status",
            JOptionPane.QUESTION_MESSAGE, null, statuses, appointment.getStatus());
            
        if (newStatus != null) {
            appointment.setStatus(newStatus);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Status updated!");
        }
    }
    
    private void deleteAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Delete this appointment?", "Confirm", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            String appointmentId = (String) tableModel.getValueAt(selectedRow, 0);
            appointments.removeIf(a -> a.getAppointment_id().equals(appointmentId));
            refreshTable();
            JOptionPane.showMessageDialog(this, "Appointment deleted!");
        }
    }
}
