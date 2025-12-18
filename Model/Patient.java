package Model;

public class Patient extends Person {
    private long nhs_number;
    private String gender;
    private String adress;
    private String postcode;
    private String emergencey_contact_number;
    private long emergency_contact_phone;
    private Date regestration_date;
    private String gp_surgery_id;

    public Patient(String user_id, String first_name, String last_name, String phone_number, String email, Date date_of_birth, long nhs_number, String gender, String adress, String postcode, String emergencey_contact_number, long emergency_contact_phone, Date regestration_date, String gp_surgery_id) {
        super(user_id, first_name, last_name, phone_number, email, date_of_birth);
        this.nhs_number = nhs_number;
        this.nhs_number = nhs_number;
        this.gender = gender;
        this.adress = adress;
        this.postcode = postcode;
        this.emergencey_contact_number = emergencey_contact_number;
        this.emergency_contact_phone = emergency_contact_phone;
        this.regestration_date = regestration_date;
        this.gp_surgery_id = gp_surgery_id;
    }

    public long getNhs_number() {
        return nhs_number;
    }

    public void setNhs_number(long nhs_number) {
        this.nhs_number = nhs_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getEmergencey_contact_number() {
        return emergencey_contact_number;
    }

    public void setEmergencey_contact_number(String emergencey_contact_number) {
        this.emergencey_contact_number = emergencey_contact_number;
    }

    public long getEmergency_contact_phone() {
        return emergency_contact_phone;
    }

    public void setEmergency_contact_phone(long emergency_contact_phone) {
        this.emergency_contact_phone = emergency_contact_phone;
    }

    public Date getRegestration_date() {
        return regestration_date;
    }

    public void setRegestration_date(Date regestration_date) {
        this.regestration_date = regestration_date;
    }

    public String getGp_surgery_id() {
        return gp_surgery_id;
    }

    public void setGp_surgery_id(String gp_surgery_id) {
        this.gp_surgery_id = gp_surgery_id;
    }
}