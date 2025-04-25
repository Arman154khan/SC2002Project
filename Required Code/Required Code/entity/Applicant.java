package entity;

import java.util.ArrayList;

import entity.enums.ApplicationStatus;
import entity.enums.FlatType;

/**
 * Represents an Applicant user in the BTO system.
 */
public class Applicant extends User {

    private FlatType flatTypeApplied;
    private ApplicationStatus applicationStatus;
    private ArrayList<Enquiry> enquiries;

    public Applicant(String name, String nric, int age, String maritalStatus, String password, String userType) {
        super(name, nric, age, maritalStatus, password, userType);
        this.age = age;
        this.name = name;
        this.maritalStatus = maritalStatus;
        this.enquiries = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "Applicant";
    }

    public String getApplicantNRIC() {
        return nric;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public FlatType getFlatTypeApplied() {
        return flatTypeApplied;
    }

    public void setFlatTypeApplied(FlatType flatTypeApplied) {
        this.flatTypeApplied = flatTypeApplied;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public ArrayList<Enquiry> getEnquiries() {
        return enquiries;
    }

    public void addEnquiry(Enquiry enquiry) {
        this.enquiries.add(enquiry);
    }

    // public String getFlat() {
    //     
    //     throw new UnsupportedOperationException("Unimplemented method 'getFlat'");
    // }
}
