package Model;

import java.util.ArrayList;
import java.util.List;

public class PrescritpionManager {
    private static PrescritpionManager instance;
    private List<Prescription> prescriptions;

    private PrescritpionManager() {
        prescriptions = new ArrayList<>();
    }

    public static PrescritpionManager getInstance() {
        if (instance == null) {
            instance = new PrescritpionManager();
        }
        return instance;
    }

    public void create_prescription(Prescription prescription) {
        if (prescription != null) {
            prescriptions.add(prescription);
        }
    }

    public boolean update_prescription(String prescription_id, Prescription updatedPrescription) {
        Prescription prescription = get_prescription_by_id(prescription_id);
        if (prescription != null) {
            int index = prescriptions.indexOf(prescription);
            prescriptions.set(index, updatedPrescription);
            return true;
        }
        return false;
    }

    public Prescription get_prescription_by_id(String prescription_id) {
        for (Prescription prescription : prescriptions) {
            if (prescription.getPrescription_id().equals(prescription_id)) {
                return prescription;
            }
        }
        return null;
    }

    public List<Prescription> get_prescriptions_by_patient(String patient_id) {
        List<Prescription> result = new ArrayList<>();
        for (Prescription prescription : prescriptions) {
            if (prescription.getPatient_id().equals(patient_id)) {
                result.add(prescription);
            }
        }
        return result;
    }
}
