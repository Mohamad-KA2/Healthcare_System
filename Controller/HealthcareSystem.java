import Model.*;
import java.util.List;

public class HealthcareSystem {
    private static HealthcareSystem instance;
    private AppointmentManager appointmentManager;
    private PrescritpionManager prescriptionManager;
    private RefferalManager referralManager;
    private static final String DATA_PATH = "Healthcare_System/";
    
    private HealthcareSystem() {
        appointmentManager = AppointmentManager.getInstance();
        prescriptionManager = PrescritpionManager.getInstance();
        referralManager = RefferalManager.getInstance();
        loadAllData();
    }
    
    public static HealthcareSystem getInstance() {
        if (instance == null) {
            instance = new HealthcareSystem();
        }
        return instance;
    }
    
    private void loadAllData() {
        List<Patient> patients = Loader.loadPatients(DATA_PATH + "patients.csv");
        for (Patient p : patients) {
            appointmentManager.register_patient(p);
        }
        
        List<Prescription> prescriptions = Loader.loadPrescriptions(DATA_PATH + "prescriptions.csv");
        for (Prescription p : prescriptions) {
            prescriptionManager.create_prescription(p);
        }
        
        List<Refferal> referrals = Loader.loadReferrals(DATA_PATH + "referrals.csv");
        for (Refferal r : referrals) {
            referralManager.create_referral(r);
        }
    }
    
    public List<Patient> getAllPatients() {
        return appointmentManager.view_all_patients();
    }
    
    public Patient findPatient(String patientId) {
        return appointmentManager.find_patient(patientId);
    }
    
    public void registerPatient(Patient patient) {
        appointmentManager.register_patient(patient);
    }
    
    public boolean updatePatientInformation(String patientId, Patient updatedPatient) {
        return appointmentManager.change_patient_information(patientId, updatedPatient);
    }
    
    public void createPrescription(Prescription prescription) {
        prescriptionManager.create_prescription(prescription);
        Loader.savePrescription(DATA_PATH + "prescriptions.csv", prescription);
    }
    
    public Prescription getPrescriptionById(String prescriptionId) {
        return prescriptionManager.get_prescription_by_id(prescriptionId);
    }
    
    public List<Prescription> getPrescriptionsByPatient(String patientId) {
        return prescriptionManager.get_prescriptions_by_patient(patientId);
    }
    
    public boolean updatePrescription(String prescriptionId, Prescription updatedPrescription) {
        return prescriptionManager.update_prescription(prescriptionId, updatedPrescription);
    }
    
    public void createReferral(Refferal referral) {
        referralManager.create_referral(referral);
        Loader.saveReferral(DATA_PATH + "referrals.csv", referral);
    }
    
    public Refferal getReferralById(String referralId) {
        return referralManager.get_referral_by_id(referralId);
    }
    
    public List<Refferal> getReferralsByPatient(String patientId) {
        return referralManager.get_referrals_by_patient(patientId);
    }
    
    public boolean updateReferral(String referralId, Refferal updatedReferral) {
        return referralManager.update_referral(referralId, updatedReferral);
    }
    
    public List<Appointment> loadAppointments() {
        return Loader.loadAppointments(DATA_PATH + "appointments.csv");
    }
    
    public List<ClinicalStaff> loadClinicians() {
        return Loader.loadClinicians(DATA_PATH + "clinicians.csv");
    }
    
    public static void main(String[] args) {
        System.out.println("=== Healthcare System Demo ===\n");
        
        HealthcareSystem system = HealthcareSystem.getInstance();
        
        System.out.println("1. Data Loaded Successfully");
        System.out.println("   Total Patients: " + system.getAllPatients().size());
        
        System.out.println("\n2. Creating New Patient...");
        Date dob = new Date(1995, 6, 15);
        Date regDate = new Date(2025, 12, 23);
        Patient newPatient = new Patient(
            "P999", "Alice", "Cooper", "07999888777", "alice.cooper@email.com",
            dob, 999888777L, "F", "999 Test Street", "B99 9ZZ",
            "Bob Cooper", 788999666L, regDate, "S001"
        );
        system.registerPatient(newPatient);
        System.out.println("   Created: " + newPatient.getUser_id() + " - " + 
                          newPatient.getFirst_name() + " " + newPatient.getLast_name());
        
        System.out.println("\n3. Creating New Prescription...");
        Date prescriptionDate = new Date(2025, 12, 23);
        Prescription newPrescription = new Prescription(
            "RX999", "P999", "C001", "", prescriptionDate,
            "Amoxicillin", "500mg", "Three times daily", 7,
            "21 capsules", "Take with food", "Boots Pharmacy",
            "Issued", prescriptionDate, null
        );
        system.createPrescription(newPrescription);
        System.out.println("   Created: " + newPrescription.getPrescription_id() + 
                          " - " + newPrescription.getMedication_name());
        
        System.out.println("\n4. Creating New Referral...");
        Refferal newReferral = new Refferal(
            "R999", "P999", "C001", "C005", "S001", "H001",
            prescriptionDate, "Routine", "Chest pain investigation",
            "30-year-old female with intermittent chest discomfort"
        );
        system.createReferral(newReferral);
        System.out.println("   Created: " + newReferral.getReferral_id() + 
                          " - " + newReferral.getReferral_reason());
        
        System.out.println("\n5. Querying Patient Data...");
        Patient patient = system.findPatient("P001");
        if (patient != null) {
            System.out.println("   Found: " + patient.getFirst_name() + " " + patient.getLast_name());
            List<Prescription> prescriptions = system.getPrescriptionsByPatient("P001");
            System.out.println("   Prescriptions: " + prescriptions.size());
        }
        
        System.out.println("\n=== Demo Complete ===");
        System.out.println("New records saved to prescriptions.csv and referrals.csv");
    }
}
