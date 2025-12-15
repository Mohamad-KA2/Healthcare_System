package Model;
public class AdminStaff extends Person {
    private String role;
    private String department;
    private String employment_status;
    private Date start_date;
    private String line_manager;
    private String access_level;

    public AdminStaff(String user_id, String first_name, String last_name, String phone_number, String email, Date date_of_birth, String role, String department, String employment_status, Date start_date, String line_manager, String access_level) {
        super(user_id, first_name, last_name, phone_number, email, date_of_birth);
        this.role = role;
        this.department = department;
        this.employment_status = employment_status;
        this.start_date = start_date;
        this.line_manager = line_manager;
        this.access_level = access_level;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmployment_status() {
        return employment_status;
    }

    public void setEmployment_status(String employment_status) {
        this.employment_status = employment_status;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public String getLine_manager() {
        return line_manager;
    }

    public void setLine_manager(String line_manager) {
        this.line_manager = line_manager;
    }

    public String getAccess_level() {
        return access_level;
    }

    public void setAccess_level(String access_level) {
        this.access_level = access_level;
    }
    
    // Admin Staff Methods
    public java.util.List<Appointment> view_all_appointments(String filePath) {
        return Loader.loadAppointments(filePath);
    }
    
    public Appointment schedule_appointment(String patient_id, String clinician_id, String facility_id,
                                           String appointment_date, String appointment_time, 
                                           int duration_minutes, String appointment_type, 
                                           String reason_for_visit) {
        String appointmentId = "A" + System.currentTimeMillis();
        Date apptDate = new Date(appointment_date);
        Date createdDate = new Date(java.time.LocalDate.now());
        
        Appointment appointment = new Appointment(
            appointmentId,
            patient_id,
            clinician_id,
            facility_id,
            apptDate,
            appointment_time,
            duration_minutes,
            appointment_type,
            "Scheduled",
            reason_for_visit,
            "",
            createdDate,
            createdDate
        );
        
        return appointment;
    }
    
    public boolean cancel_appointment(String appointment_id, java.util.List<Appointment> appointments) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointment_id().equals(appointment_id)) {
                appointment.setStatus("Cancelled");
                return true;
            }
        }
        return false;
    }
    
    public void update_patient_details(String patient_id, Patient updated_patient) {
        AppointmentManager manager = AppointmentManager.getInstance();
        manager.change_patient_information(patient_id, updated_patient);
    }
}