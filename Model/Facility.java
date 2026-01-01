package Model;

public class Facility {
    private String facility_id;
    private String facility_name;
    private String facility_type;
    private String address;
    private String postcode;
    private String phone_number;
    private String opening_hours;
    private String email;
    private int capacity;
    private String manager_name;
    private String specialities_offered;

    public Facility(String facility_id, String facility_name, String facility_type, String address, String postcode, String phone_number, String opening_hours, String email, int capacity, String manager_name, String specialities_offered) {
        this.facility_id = facility_id;
        this.facility_name = facility_name;
        this.facility_type = facility_type;
        this.address = address;
        this.postcode = postcode;
        this.phone_number = phone_number;
        this.opening_hours = opening_hours;
        this.email = email;
        this.capacity = capacity;
        this.manager_name = manager_name;
        this.specialities_offered = specialities_offered;
    }

    public String getFacility_id() {
        return facility_id;
    }

    public void setFacility_id(String facility_id) {
        this.facility_id = facility_id;
    }

    public String getFacility_name() {
        return facility_name;
    }

    public void setFacility_name(String facility_name) {
        this.facility_name = facility_name;
    }

    public String getFacility_type() {
        return facility_type;
    }

    public void setFacility_type(String facility_type) {
        this.facility_type = facility_type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(String opening_hours) {
        this.opening_hours = opening_hours;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public String getSpecialities_offered() {
        return specialities_offered;
    }

    public void setSpecialities_offered(String specialities_offered) {
        this.specialities_offered = specialities_offered;
    }
}
