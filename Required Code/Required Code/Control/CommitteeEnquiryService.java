package Control;

import entity.Enquiry;
import interfaces.EnquiryManagementSystem;
/**
* Manages the enquiry service
*/

public class CommitteeEnquiryService implements EnquiryManagementSystem {


     public void answerEnquiry(Enquiry enquiry, String answer)
     {
        if(!enquiry.isResolved())
        {
            enquiry.setAnswer(answer);
            System.out.println("Enquiry has been answered");
        }
        else
        {
            System.out.println("Enquiry already resolved. Cannot re-answer.");
        }
     }

     public void viewEnquiry(Enquiry enquiry)
     {
        System.out.println("Q:" + enquiry.getQuestion());
        System.out.println("A:" + (enquiry.isResolved()? enquiry.getAnswer(): "Not answered yet."));
     }
    
}
