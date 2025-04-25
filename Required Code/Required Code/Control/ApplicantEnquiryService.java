package Control;

import entity.Enquiry;
import entity.Project;
import interfaces.EnquiryManagementSystem;

import java.util.List;

/**
 * Allows applicants to make, edit and view responses to enquiries
 */

public class ApplicantEnquiryService implements EnquiryManagementSystem
{
    private List <Enquiry> enquiryList;
    

    public ApplicantEnquiryService(List<Enquiry> enquiryList)
    {
        this.enquiryList = enquiryList;
    }

    public void submitEnquiry(String projectId, String question, String enquirerName, List<Project> allProjects) {
        String enquiryId = "E" + String.format("%03d", enquiryList.size() + 1);
    
        Project linkedProject = null;
        for (Project p : allProjects) {
            if (p.getProjectId().equalsIgnoreCase(projectId)) {
                linkedProject = p;
                break;
            }
        }
    
        if (linkedProject == null) {
            System.out.println("Project ID not found. Cannot submit enquiry.");
            return;
        }
    
        Enquiry newEnquiry = new Enquiry(enquiryId, question, enquirerName, linkedProject);
        EnquiryRepository.addEnquiry(newEnquiry);
        System.out.println("Enquiry submitted.");
    }
    
    

    public boolean getNotified(String enquirerName)
    {
        for(Enquiry e: enquiryList)
        {
            if(e.getEnquirerName().equals(enquirerName) && e.isResolved())
            {
                return true;
            }
        }
        return false;
    }

    public void viewEnquiry(Enquiry enquiry)
    {
        System.out.println("Q: " + enquiry.getQuestion());
        if(enquiry.isResolved())
        {
            System.out.println("A: " + enquiry.getAnswer());
        }
        else
        {
            System.out.println("Status: Unanswered as of now. ");
        }
    }

    public void editEnquiry(Enquiry enquiry, String newQuestion)
    {
        if (!enquiry.isResolved())
        {
            enquiry.setQuestion(newQuestion);
            System.out.println("Enquiry updated.");
        }
        else
        {
            System.out.println("Cannot edit - enquiry is already answered");
        }
    }

    public void deletedEnquiry(Enquiry enquiry)
    {
        if (!enquiry.isResolved())
        {
            enquiryList.remove(enquiry);
            System.out.println("Enquiry has been successfully deleted.");
        }
        else
        {
            System.out.println("The enquiry cannot be deleted as it has already been answered. ");
        }
    }

    public Enquiry findEnquiryById(String enquiryId) {
        for (Enquiry e : enquiryList) {
            if (e.getEnquiryId().equalsIgnoreCase(enquiryId)) {
                return e;
            }
        }
        return null;
    }

    public void viewAllEnquiries() {
        if (enquiryList.isEmpty()) {
            System.out.println("No enquiries found.");
            return;
        }
    
        System.out.println("\n=== All Enquiries ===");
        for (Enquiry e : enquiryList) {
            System.out.println("Enquiry ID   : " + e.getEnquiryId());
            System.out.println("Enquirer     : " + e.getEnquirerName());
            System.out.println("Question     : " + e.getQuestion());
            System.out.println("Answer       : " + (e.isResolved() ? e.getAnswer() : "[Not yet answered]"));
            System.out.println("Resolved     : " + (e.isResolved() ? "Yes" : "No"));
            System.out.println("-------------");
        }
    }
    
    
    
}
