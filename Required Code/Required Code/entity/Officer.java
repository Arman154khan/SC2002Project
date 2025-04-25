package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the officer entity.
 */

public class Officer extends User
{
    private String assignedProjectId;
    private boolean registrationPending;
    private List<String> enquiriesHandled;
    

    public Officer(String name, String nric, int age, String maritalStatus, String password, String userType) {
        super(name, nric, age, maritalStatus, password, userType); 
        this.assignedProjectId = null;
        this.registrationPending = false;
        this.enquiriesHandled = new ArrayList<>();
    }
    

    @Override
    public String getRole()
    {
        return "Officer";
    }

    public void setAssignedProjectId(String assignedProjectId) {
        this.assignedProjectId = assignedProjectId;
    }


    public String getAssignedProjectId(){
        return assignedProjectId;
    }

    public void assignProject(String projectId)
    {
        this.assignedProjectId = projectId;
        this.registrationPending = false;
    }

    public boolean isRegistrationPending()
    {
        return registrationPending;
    }

    public void setRegistrationPending(boolean pending)
    {
        this.registrationPending = pending;
    }

    public List<String> getEnquiriesHandled()
    {
        return enquiriesHandled;
    }

    public void addHandledEnquiry(String enquiryId)
    {
        this.enquiriesHandled.add(enquiryId);
    }
}
