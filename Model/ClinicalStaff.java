package Model;
public class ClinicalStaff extends Person {
    private String title;
    private String speciality;
    private String gmc_number;
    private String workplace_id;
    private String workplace_type;
    private String emplyment_status;
    private Date start_date;

    public ClinicalStaff(String user_id, String first_name, String last_name, String phone_number, String email, Date date_of_birth, String title, String speciality, String gmc_number, String workplace_id, String workplace_type, String emplyment_status, Date start_date) {
        super(user_id, first_name, last_name, phone_number, email, date_of_birth);
        this.title = title;
        this.speciality = speciality;
        this.gmc_number = gmc_number;
        this.workplace_id = workplace_id;
        this.workplace_type = workplace_type;
        this.emplyment_status = emplyment_status;
        this.start_date = start_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getGmc_number() {
        return gmc_number;
    }

    public void setGmc_number(String gmc_number) {
        this.gmc_number = gmc_number;
    }

    public String getWorkplace_id() {
        return workplace_id;
    }

    public void setWorkplace_id(String workplace_id) {
        this.workplace_id = workplace_id;
    }

    public String getWorkplace_type() {
        return workplace_type;
    }

    public void setWorkplace_type(String workplace_type) {
        this.workplace_type = workplace_type;
    }

    public String getEmplyment_status() {
        return emplyment_status;
    }

    public void setEmplyment_status(String emplyment_status) {
        this.emplyment_status = emplyment_status;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Patient view_patient_record(String patient_id) {
        AppointmentManager manager = AppointmentManager.getInstance();
        return manager.find_patient(patient_id);
    }
    
    public Prescription create_prescription(String patient_id, String medication_name, String dosage, 
                                           String frequency, int duration_days, String quantity, 
                                           String instructions, String pharmacy_name) {
        Date prescriptionDate = new Date(java.time.LocalDate.now());
        Date issueDate = new Date(java.time.LocalDate.now());
        String prescriptionId = "RX" + System.currentTimeMillis();
        
        Prescription prescription = new Prescription(
            prescriptionId,
            patient_id,
            this.getUser_id(),
            "",
            prescriptionDate,
            medication_name,
            dosage,
            frequency,
            duration_days,
            quantity,
            instructions,
            pharmacy_name,
            "Issued",
            issueDate,
            null
        );
        
        PrescritpionManager manager = PrescritpionManager.getInstance();
        manager.create_prescription(prescription);
        return prescription;
    }
    
    public void update_patient_record(String patient_id, Patient updated_patient) {
        AppointmentManager manager = AppointmentManager.getInstance();
        manager.change_patient_information(patient_id, updated_patient);
    }
    
    public Refferal view_referral_info(String patient_id) {
        RefferalManager manager = RefferalManager.getInstance();
        java.util.List<Refferal> referrals = manager.get_referrals_by_patient(patient_id);
        return referrals.isEmpty() ? null : referrals.get(0);
    }

    public Refferal create_referral(String patient_id, String referred_to_clinician_id, 
                                    String referred_to_facility_id, String urgency_level, 
                                    String referral_reason, String clinical_summary) {
        if (!this.title.equalsIgnoreCase("GP")) {
            System.err.println("Only GPs can create referrals");
            return null;
        }
        
        Date referralDate = new Date(java.time.LocalDate.now());
        String referralId = "R" + System.currentTimeMillis();
        
        Refferal referral = new Refferal(
            referralId,
            patient_id,
            this.getUser_id(),
            referred_to_clinician_id,
            this.workplace_id,
            referred_to_facility_id,
            referralDate,
            urgency_level,
            referral_reason,
            clinical_summary,
            "",
            "New",
            "",
            "",
            referralDate,
            referralDate
        );
        
        RefferalManager manager = RefferalManager.getInstance();
        manager.create_referral(referral);
        return referral;
    }
}