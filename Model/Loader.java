package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    
    public static List<Patient> loadPatients(String filePath) {
        List<Patient> patients = new ArrayList<>();
        List<String> lines = loadFile(filePath);
        
        if (lines.size() <= 1) return patients;
        
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] fields = parseCSVLine(line);
            
            if (fields.length >= 14) {
                try {
                    String patientId = fields[0];
                    String firstName = fields[1];
                    String lastName = fields[2];
                    Date dob = new Date(fields[3]);
                    long nhsNumber = Long.parseLong(fields[4]);
                    String gender = fields[5];
                    String phone = fields[6];
                    String email = fields[7];
                    String address = fields[8];
                    String postcode = fields[9];
                    String emergencyContact = fields[10];
                    long emergencyPhone = Long.parseLong(fields[11]);
                    Date registrationDate = new Date(fields[12]);
                    String gpSurgeryId = fields[13];
                    
                    Patient patient = new Patient(patientId, firstName, lastName, phone, email, dob, 
                                                 nhsNumber, gender, address, postcode, emergencyContact, 
                                                 emergencyPhone, registrationDate, gpSurgeryId);
                    patients.add(patient);
                } catch (Exception e) {
                    System.err.println("Error parsing patient line: " + line);
                    System.err.println("Exception: " + e.getMessage());
                    System.err.println("Field count: " + fields.length);
                }
            } else {
                System.err.println("Not enough fields (" + fields.length + ") in line: " + line);
            }
        }
        return patients;
    }
    
    public static List<ClinicalStaff> loadClinicians(String filePath) {
        List<ClinicalStaff> clinicians = new ArrayList<>();
        List<String> lines = loadFile(filePath);
        
        if (lines.size() <= 1) return clinicians;
        
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] fields = line.split(",");
            
            if (fields.length >= 12) {
                try {
                    String clinicianId = fields[0];
                    String firstName = fields[1];
                    String lastName = fields[2];
                    String title = fields[3];
                    String speciality = fields[4];
                    String gmcNumber = fields[5];
                    String phone = fields[6];
                    String email = fields[7];
                    String workplaceId = fields[8];
                    String workplaceType = fields[9];
                    String employmentStatus = fields[10];
                    Date startDate = new Date(fields[11]);
                    
                    Date dob = new Date(1980, 1, 1); // Default DOB for clinicians
                    
                    ClinicalStaff clinician = new ClinicalStaff(clinicianId, firstName, lastName, phone, email, 
                                                                dob, title, speciality, gmcNumber, workplaceId, 
                                                                workplaceType, employmentStatus, startDate);
                    clinicians.add(clinician);
                } catch (Exception e) {
                    System.err.println("Error parsing clinician line: " + line);
                }
            }
        }
        return clinicians;
    }
    
    public static List<Appointment> loadAppointments(String filePath) {
        List<Appointment> appointments = new ArrayList<>();
        List<String> lines = loadFile(filePath);
        
        if (lines.size() <= 1) return appointments;
        
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] fields = line.split(",");
            
            if (fields.length >= 12) {
                try {
                    String appointmentId = fields[0];
                    String patientId = fields[1];
                    String clinicianId = fields[2];
                    String facilityId = fields[3];
                    Date appointmentDate = new Date(fields[4]);
                    String appointmentTime = fields[5];
                    int durationMinutes = Integer.parseInt(fields[6]);
                    String appointmentType = fields[7];
                    String status = fields[8];
                    String reason = fields[9];
                    String notes = fields[10].replace("\"", "");
                    Date createdDate = new Date(fields[11]);
                    Date lastModified = new Date(fields[12]);
                    
                    Appointment appointment = new Appointment(appointmentId, patientId, clinicianId, facilityId, 
                                                             appointmentDate, appointmentTime, durationMinutes, 
                                                             appointmentType, status, reason, notes, 
                                                             createdDate, lastModified);
                    appointments.add(appointment);
                } catch (Exception e) {
                    System.err.println("Error parsing appointment line: " + line);
                }
            }
        }
        return appointments;
    }
    
    public static List<Prescription> loadPrescriptions(String filePath) {
        List<Prescription> prescriptions = new ArrayList<>();
        List<String> lines = loadFile(filePath);
        
        if (lines.size() <= 1) return prescriptions;
        
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] fields = parseCSVLine(line);
            
            if (fields.length >= 14) {
                try {
                    String prescriptionId = fields[0];
                    String patientId = fields[1];
                    String clinicianId = fields[2];
                    String appointmentId = fields[3];
                    Date prescriptionDate = new Date(fields[4]);
                    String medicationName = fields[5];
                    String dosage = fields[6];
                    String frequency = fields[7];
                    int durationDays = Integer.parseInt(fields[8]);
                    String quantity = fields[9];
                    String instructions = fields[10];
                    String pharmacyName = fields[11];
                    String status = fields[12];
                    Date issueDate = new Date(fields[13]);
                    Date collectionDate = fields.length > 14 && !fields[14].isEmpty() ? new Date(fields[14]) : null;
                    
                    Prescription prescription = new Prescription(prescriptionId, patientId, clinicianId, appointmentId,
                                                                prescriptionDate, medicationName, dosage, frequency,
                                                                durationDays, quantity, instructions, pharmacyName,
                                                                status, issueDate, collectionDate);
                    prescriptions.add(prescription);
                } catch (Exception e) {
                    System.err.println("Error parsing prescription line: " + line);
                }
            }
        }
        return prescriptions;
    }
    
    public static List<Refferal> loadReferrals(String filePath) {
        List<Refferal> referrals = new ArrayList<>();
        List<String> lines = loadFile(filePath);
        
        if (lines.size() <= 1) return referrals;
        
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] fields = parseCSVLine(line);
            
            if (fields.length >= 10) {
                try {
                    String referralId = fields[0];
                    String patientId = fields[1];
                    String referringClinicianId = fields[2];
                    String referredToClinicianId = fields[3];
                    String referringFacilityId = fields[4];
                    String referredToFacilityId = fields[5];
                    Date referralDate = new Date(fields[6]);
                    String urgencyLevel = fields[7];
                    String referralReason = fields[8];
                    String clinicalSummary = fields[9].replace("\"", "");
                    
                    Refferal referral = new Refferal(referralId, patientId, referringClinicianId, referredToClinicianId,
                                                     referringFacilityId, referredToFacilityId, referralDate,
                                                     urgencyLevel, referralReason, clinicalSummary);
                    referrals.add(referral);
                } catch (Exception e) {
                    System.err.println("Error parsing referral line: " + line);
                }
            }
        }
        return referrals;
    }
    
    public static void savePrescription(String filePath, Prescription prescription) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%d,%s,%s,%s,%s,%s,%s%n",
                prescription.getPrescription_id(),
                prescription.getPatient_id(),
                prescription.getClinicin_id(),
                prescription.getAppointment_id(),
                prescription.getPrescription_date(),
                prescription.getMedication_name(),
                prescription.getDosage(),
                prescription.getFrequency(),
                prescription.getDuration_days(),
                prescription.getQuantity(),
                prescription.getInstructions(),
                prescription.getPharmacy_name(),
                prescription.getStatus(),
                prescription.getIssue_date(),
                prescription.getCollection_date() != null ? prescription.getCollection_date().toString() : "");
            writer.write(line);
        } catch (IOException e) {
            System.err.println("Error saving prescription: " + e.getMessage());
        }
    }
    
    public static void saveReferral(String filePath, Refferal referral) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,\"%s\"%n",
                referral.getReferral_id(),
                referral.getPatient_id(),
                referral.getReferring_clnician_id(),
                referral.getReferred_to_clinician_id(),
                referral.getReferring_facility_id(),
                referral.getReferred_to_facility_id(),
                referral.getReferred_date(),
                referral.getUrgency_level(),
                referral.getReferral_reason(),
                referral.getClinical_summary());
            writer.write(line);
        } catch (IOException e) {
            System.err.println("Error saving referral: " + e.getMessage());
        }
    }
    
    public static void saveAllPrescriptionsToFile(String filePath, List<Prescription> prescriptions) {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            writer.write("prescription_id,patient_id,clinician_id,appointment_id,prescription_date,medication_name,dosage,frequency,duration_days,quantity,instructions,pharmacy_name,status,issue_date,collection_date\n");
            for (Prescription prescription : prescriptions) {
                String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%d,%s,%s,%s,%s,%s,%s%n",
                    prescription.getPrescription_id(),
                    prescription.getPatient_id(),
                    prescription.getClinicin_id(),
                    prescription.getAppointment_id(),
                    prescription.getPrescription_date(),
                    prescription.getMedication_name(),
                    prescription.getDosage(),
                    prescription.getFrequency(),
                    prescription.getDuration_days(),
                    prescription.getQuantity(),
                    prescription.getInstructions(),
                    prescription.getPharmacy_name(),
                    prescription.getStatus(),
                    prescription.getIssue_date(),
                    prescription.getCollection_date() != null ? prescription.getCollection_date().toString() : "");
                writer.write(line);
            }
        } catch (IOException e) {
            System.err.println("Error saving prescriptions: " + e.getMessage());
        }
    }
    
    public static void saveAllReferralsToFile(String filePath, List<Refferal> referrals) {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            writer.write("referral_id,patient_id,referring_clinician_id,referred_to_clinician_id,referring_facility_id,referred_to_facility_id,referral_date,urgency_level,referral_reason,clinical_summary\n");
            for (Refferal referral : referrals) {
                String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,\"%s\"%n",
                    referral.getReferral_id(),
                    referral.getPatient_id(),
                    referral.getReferring_clnician_id(),
                    referral.getReferred_to_clinician_id(),
                    referral.getReferring_facility_id(),
                    referral.getReferred_to_facility_id(),
                    referral.getReferred_date(),
                    referral.getUrgency_level(),
                    referral.getReferral_reason(),
                    referral.getClinical_summary());
                writer.write(line);
            }
        } catch (IOException e) {
            System.err.println("Error saving referrals: " + e.getMessage());
        }
    }
    
    private static List<String> loadFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error loading file: " + filePath);
            e.printStackTrace();
        }
        return lines;
    }

    private static String[] parseCSVLine(String line) {
        List<String> fields = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(currentField.toString().trim());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }
        fields.add(currentField.toString().trim());
        
        return fields.toArray(new String[0]);
    }
}
