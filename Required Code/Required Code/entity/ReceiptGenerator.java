package entity;

import entity.enums.FlatType;
import entity.enums.ApplicationStatus;
/**
 * Represents the receipt generator 
*/

public class ReceiptGenerator {


     public receipt generateReceipt (Application application, Applicant applicant, Project project)
     {
        boolean bookingSuccess = application.getStatus() == ApplicationStatus.BOOKED;
        return new receipt(
            applicant.getName(),
            applicant.getNric(),
            application.getStatus(),
            application.getFlatType(),
            bookingSuccess,
            project.getProjectName(),
            project.getNeighbourhood()
        );
     }
}
