package Model;

import java.util.ArrayList;
import java.util.List;

public class RefferalManager {
    private static RefferalManager instance;
    private List<Refferal> referrals;

    private RefferalManager() {
        referrals = new ArrayList<>();
    }

    public static RefferalManager getInstance() {
        if (instance == null) {
            instance = new RefferalManager();
        }
        return instance;
    }

    public void create_referral(Refferal referral) {
        if (referral != null) {
            referrals.add(referral);
        }
    }

    public boolean update_referral(String referral_id, Refferal updatedReferral) {
        Refferal referral = get_referral_by_id(referral_id);
        if (referral != null) {
            int index = referrals.indexOf(referral);
            referrals.set(index, updatedReferral);
            return true;
        }
        return false;
    }

    public Refferal get_referral_by_id(String referral_id) {
        for (Refferal referral : referrals) {
            if (referral.getReferral_id().equals(referral_id)) {
                return referral;
            }
        }
        return null;
    }

    public List<Refferal> get_referrals_by_patient(String patient_id) {
        List<Refferal> result = new ArrayList<>();
        for (Refferal referral : referrals) {
            if (referral.getPatient_id().equals(patient_id)) {
                result.add(referral);
            }
        }
        return result;
    }
}
