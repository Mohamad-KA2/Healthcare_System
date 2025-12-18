package Model;


public class Refferal {
    private String referral_id;
    private String patient_id;
    private String referring_clnician_id;
    private String referred_to_clinician_id;
    private String referring_facility_id;
    private String referred_to_facility_id;
    private Date referred_date;
    private String urgency_level;
    private String referral_reason;
    private String clinical_summary;

    public Refferal(String referral_id, String patient_id, String referring_clnician_id, String referred_to_clinician_id, String referring_facility_id, String referred_to_facility_id, Date referred_date, String urgency_level, String referral_reason, String clinical_summary) {
        this.referral_id = referral_id;
        this.patient_id = patient_id;
        this.referring_clnician_id = referring_clnician_id;
        this.referred_to_clinician_id = referred_to_clinician_id;
        this.referring_facility_id = referring_facility_id;
        this.referred_to_facility_id = referred_to_facility_id;
        this.referred_date = referred_date;
        this.urgency_level = urgency_level;
        this.referral_reason = referral_reason;
        this.clinical_summary = clinical_summary;
    }

    public String getReferral_id() { return referral_id; }
    public void setReferral_id(String referral_id) { this.referral_id = referral_id; }

    public String getPatient_id() { return patient_id; }
    public void setPatient_id(String patient_id) { this.patient_id = patient_id; }

    public String getReferring_clnician_id() { return referring_clnician_id; }
    public void setReferring_clnician_id(String referring_clnician_id) { this.referring_clnician_id = referring_clnician_id; }

    public String getReferred_to_clinician_id() { return referred_to_clinician_id; }
    public void setReferred_to_clinician_id(String referred_to_clinician_id) { this.referred_to_clinician_id = referred_to_clinician_id; }

    public String getReferring_facility_id() { return referring_facility_id; }
    public void setReferring_facility_id(String referring_facility_id) { this.referring_facility_id = referring_facility_id; }

    public String getReferred_to_facility_id() { return referred_to_facility_id; }
    public void setReferred_to_facility_id(String referred_to_facility_id) { this.referred_to_facility_id = referred_to_facility_id; }

    public Date getReferred_date() { return referred_date; }
    public void setReferred_date(Date referred_date) { this.referred_date = referred_date; }

    public String getUrgency_level() { return urgency_level; }
    public void setUrgency_level(String urgency_level) { this.urgency_level = urgency_level; }

    public String getReferral_reason() { return referral_reason; }
    public void setReferral_reason(String referral_reason) { this.referral_reason = referral_reason; }

    public String getClinical_summary() { return clinical_summary; }
    public void setClinical_summary(String clinical_summary) { this.clinical_summary = clinical_summary; }
}
