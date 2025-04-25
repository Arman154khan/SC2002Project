package Control;
import entity.Enquiry;
import entity.HDBManager;
import entity.Project;
import interfaces.EnquiryManagementSystem;
import java.util.List;
/**
* Manager handles the enquiries via this service class. 
*/
public class ManagerEnquiryService implements EnquiryManagementSystem {

    private CommitteeEnquiryService committeeService;

    public ManagerEnquiryService()
    {
        this.committeeService = new CommitteeEnquiryService();
    }



     public void viewAllEnquiries(List<Enquiry> enquiries) {
        System.out.println("\n=== All Enquiries ===");
        for (Enquiry e : enquiries) {
            System.out.println(e);
        }
    }

    public void respondToProjectEnquiry(HDBManager manager, List<Enquiry> allEnquiries, String enquiryId, String answer) {
    for (Enquiry e : allEnquiries) {
        if (e.getEnquiryId().equalsIgnoreCase(enquiryId)) {
            Project project = e.getLinkedProject();
            if (project.getManagerInCharge().equals(manager)) {
                e.setAnswer(answer);
                e.setResolved(true);
                System.out.println("Reply submitted for enquiry ID: " + enquiryId);
                return;
            } else {
                System.out.println("Error: This enquiry does not belong to your project.");
                return;
            }
        }
    }
    System.out.println("Enquiry ID not found.");
}

    

     @Override
     public void viewEnquiry(Enquiry enquiry)
     {
        committeeService.viewEnquiry(enquiry);
     }

     
}
