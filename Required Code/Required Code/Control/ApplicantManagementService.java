package Control;
import java.util.List;

import entity.*;
import entity.enums.ApplicationStatus;
import entity.enums.FlatType;

/**
 * The system helps manage applicants. 
 */

public class ApplicantManagementService {

    private Project currentProject;

    public ApplicantManagementService(Project currentProjectHandling) {
        this.currentProject = currentProjectHandling;
    }

    public void approveApplicant(Application app) {
        FlatType flatType = app.getFlatType();
        
        if (!currentProject.isFlatAvailable(flatType)) {
            System.out.println("Approval failed: no more units for flat type.");
            return;
        }
    
        currentProject.updateFlatAvailability(flatType, -1);
        app.setStatus(ApplicationStatus.BOOKED);

        for(Applicant a : currentProject.getPendingApplicants()){
            if(a.getApplicantNRIC().equals(app.getApplicantNRIC())){
                currentProject.moveToApprovedApplicants(a);
                currentProject.removePendingApplicant(a);
                currentProject.getManagerInCharge().addApplicantToAllApplicants(a);
                a.setFlatTypeApplied(flatType);
                a.setApplicationStatus(ApplicationStatus.BOOKED);
            }
        }
    
        System.out.println("Application approved and flat booked.");
    }

    public void rejectApplicant(Application app) {

        Applicant appl = null;

        for (Applicant a : currentProject.getPendingApplicants()) {
            if (a.getApplicantNRIC().equalsIgnoreCase(app.getApplicantNRIC())) {
                appl = a;
                break;
            }
        }
        
        if (appl == null) {
            System.out.println("Applicant is not in the pending list.");
            return;
        }
    
        currentProject.removePendingApplicant(appl);
        app.setStatus(ApplicationStatus.UNSUCCESFUL);
        appl.setApplicationStatus(ApplicationStatus.UNSUCCESFUL);
    
        System.out.println("Application rejected.");
    }

    public void approveApplicationWithdrawal(Application app, HDBManager manager) {
        Applicant appl = null;

        for (Applicant a : manager.getApplicationWithdrawalRequests()) {
            System.out.println("a");
            if (a.getApplicantNRIC().equalsIgnoreCase(app.getApplicantNRIC())) {
                appl = a;
                break;
            }
        }
        
        if (appl == null) {
            System.out.println("No withdrawal request found.");
            return;
        }
    
        currentProject.removePendingApplicant(appl);
        app.setWithdrawn(true);
        app.setStatus(null);
        manager.removeWithdrawalRequest(appl);
        manager.removeApplicantFromAllApplicants(appl);
    
        System.out.println("Withdrawal approved.");
    }

    public void rejectApplicationWithdrawal(HDBManager manager, Application app) {
        Applicant appl = null;

        for (Applicant a : manager.getApplicationWithdrawalRequests()) {
            if (a.getApplicantNRIC().equals(app.getApplicantNRIC())) {
                appl = a;
                break;
            }
        }
        
        if (appl == null) {
            System.out.println("No withdrawal request found.");
            return;
        }
    
        manager.removeWithdrawalRequest(appl);
        app.setStatus(ApplicationStatus.PENDING);
        app.setWithdrawn(false);

        System.out.println("Withdrawal request rejected.");
    }
}
