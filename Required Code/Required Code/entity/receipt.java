package entity;
import entity.enums.FlatType;
import entity.enums.ApplicationStatus;
/**
 * Represents the receipt.
 */
public class receipt {
    private String applicantName;
    private String applicantNRIC;
    private ApplicationStatus applicationStatus;
    private FlatType flatType;
    private boolean flatBookingStatus;
    private String projectName;
    private String neighbourhood;

    public receipt(String applicantName, String applicantNRIC, ApplicationStatus applicationStatus, FlatType flatType, boolean flatBookingStatus, String projectName, String neighbourhood)
    {
        this.applicantName = applicantName;
        this.applicantNRIC = applicantNRIC;
        this.applicationStatus = applicationStatus;
        this.flatType = flatType;
        this.flatBookingStatus = flatBookingStatus;
        this.projectName = projectName;
        this.neighbourhood = neighbourhood;
    }

    public String toDisplayString()
    {
        return "===Flat Booking Receipt===\n"
            + "Applicant: " + applicantName + " (" + applicantNRIC + " )\n"
            + "Flat Type: " + flatType + "\n"
            + "Booking Successful" + (flatBookingStatus ? "Yes" : "No") + "\n"
            + "Application Status" + applicationStatus + "\n"
            + "Project: " + projectName + "\n"
            + "Neighbourhood" + neighbourhood + "\n"
            + "====================";
    }
}
