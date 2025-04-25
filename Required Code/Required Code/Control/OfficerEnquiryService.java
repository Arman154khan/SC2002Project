package Control;

import entity.*;
import interfaces.EnquiryManagementSystem;

import java.util.List;

/**
* Manages the enquiry system for officers
*/

public class OfficerEnquiryService implements EnquiryManagementSystem {

    private CommitteeEnquiryService committeeService;

    @Override
    public void viewEnquiry(Enquiry enquiry) {
        committeeService.viewEnquiry(enquiry); 
    }
    
    public OfficerEnquiryService()
    {
        this.committeeService = new CommitteeEnquiryService();
    }


     public void viewEnquiry (Officer officer, List<Enquiry> allEnquiries)
     {
        String assignedProject = officer.getAssignedProjectId();
        boolean found = false;

        for (Enquiry e: allEnquiries)
        {
            if(e.getProjectId().equals(assignedProject))
            {
                committeeService.viewEnquiry(e);
                found = true;
                System.out.println("---------------");
            }
            if(!found) 
            {
                System.out.println("No enquiries found for the assigned project. ");
            }
        }
     }
    
}
