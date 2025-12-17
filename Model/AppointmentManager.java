package Model;

import java.util.ArrayList;
import java.util.List;

public class AppointmentManager {
    private static AppointmentManager instance;
    private ArrayList<Patient> patients_list;

    private AppointmentManager() {
        patients_list = new ArrayList<>();
    }

    public static AppointmentManager getInstance() {
        if (instance == null) {
            instance = new AppointmentManager();
        }
        return instance;
    }

    public List<Patient> view_all_patients() {
        return new ArrayList<>(patients_list);
    }

    public Patient find_patient(String patient_id) {
        for (Patient patient : patients_list) {
            if (patient.getUser_id().equals(patient_id)) {
                return patient;
            }
        }
        return null;
    }

    public String view_patient_notes(String patient_id) {
        Patient patient = find_patient(patient_id);
        if (patient != null) {
            return "Notes for patient: " + patient_id;
        }
        return "Patient not found";
    }

    public Patient view_patient_details(String patient_id) {
        return find_patient(patient_id);
    }

    public void register_patient(Patient patient) {
        if (patient != null) {
            patients_list.add(patient);
        }
    }

    public boolean change_patient_information(String patient_id, Patient updatedPatient) {
        Patient patient = find_patient(patient_id);
        if (patient != null) {
            int index = patients_list.indexOf(patient);
            patients_list.set(index, updatedPatient);
            return true;
        }
        return false;
    }

    public boolean cancel_appointment(String appointment_id) {
        // Logic to cancel appointment would go here
        return true;
    }
}
