package Control;

import entity.*;
import entity.enums.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Manages the applicant's application for a project
 */

public class ProjectApplicationService 
{

    private Application application;
    private Applicant applicant;

    public ProjectApplicationService(Applicant applicant)
    {
        this.applicant = applicant;
    }

    public Application applyForProject(Project project, FlatType flatType, List <Application> allApplications)
    {
        for (Application a : allApplications){
            if (a.getApplicantNRIC() == applicant.getApplicantNRIC()) {
                System.out.println("You already have an ongoing application.");
                return null;
            }
        }

        if (!project.isVisible()) {
            System.out.println("Project is not visible to applicants.");
            return null;
        }

        if (!project.isEligible(applicant, flatType)) {
            System.out.println("You are not eligible for this project.");
            return null;
        }

        if (!isFlatTypeAllowed(applicant, flatType)) {
            System.out.println("You are not allowed to apply for this flat type.");
            return null;
        }

        if (!project.isFlatAvailable(flatType)) {
            System.out.println("No available units for selected flat type.");
            return null;
        }

        String applicationId = "App" + applicant.getApplicantNRIC();
        Application newApp = new Application(applicationId, project, applicant.getNric(), flatType);
        ApplicationRepository.addApplication(newApp);
        this.application = newApp;

        project.addPendingApplicant(applicant);
        
        System.out.println("Application submitted for project: " + project.getProjectName());
        return newApp;
    }

    
    private boolean isFlatTypeAllowed(Applicant applicant, FlatType flatType) {
        String maritalStatus = applicant.getMaritalStatus();
        int age = applicant.getAge();

        if (maritalStatus.equalsIgnoreCase("Single")) {
            return age >= 35 && flatType.equals(FlatType.TWO_ROOM);
        } else if (maritalStatus.equalsIgnoreCase("Married")) {
            return age >= 21 && (flatType.equals(FlatType.TWO_ROOM) || flatType.equals(FlatType.THREE_ROOM));
        }
        return false;
    }

    public void withdrawApplication(Application app)
    {
        if (app.getStatus().equals(ApplicationStatus.PENDING)){
            app.getProject().getManagerInCharge().addWithdrawalRequest(applicant);
            app.setStatus(ApplicationStatus.WITHDRAWPENDING);
            app.setWithdrawn(false);
            System.out.println("Withdrawal request submitted");
        } else if (app.getStatus().equals(ApplicationStatus.BOOKED)) {
            System.out.println("You cannot withdraw from a booked application.");
        } else {
            System.out.println("No active application to withdraw from.");
        }
    }

    public List<Project> viewEligibleProjects(List<Project> allProjects, boolean isOfficerOfCurrentProject, boolean isMarried)
    {
        List <Project> eligible = new ArrayList<>();
        for (Project p: allProjects)
        {
            if(p.isVisible())
            {
                eligible.add(p);
            }
        }
        return eligible;
    }

    public void viewApplicationStatus(List<Application> allApplications) {
        boolean found = false;
    
        for (Application a : allApplications) {
            if (a.getApplicantNRIC().equals(applicant.getApplicantNRIC())) {
                System.out.println("Application ID: " + a.getApplicationID());
                System.out.println("Project: " + a.getProject().getProjectName());
                System.out.println("Flat Type: " + a.getFlatType());
                System.out.println("Status: " + a.getStatus());
                System.out.println("Withdrawn: " + a.isWithdrawn());
                System.out.println("----------------------------");
                found = true;
                break;
            }
        }
    
        if (!found) {
            System.out.println("No active application found for your NRIC.");
        }
    }

    public void viewAppliedProject(List<Application> allApplications, List<Project> allProjects)
    {
        for(Application a: allApplications)
        {
            if (a.getApplicantNRIC().equals(applicant.getApplicantNRIC()))
            {
                for(Project p: allProjects)
                {
                    if(p.equals(a.getProject()))
                    {
                        System.out.println("Project Name: " + p.getProjectName());
                        System.out.println("Neighbourhood: " + p.getNeighbourhood());
                        System.out.println("Available 2-room flats: " + p.getFlatUnitCount(FlatType.TWO_ROOM));
                        System.out.println("Available 3-room flats: " + p.getFlatUnitCount(FlatType.THREE_ROOM));
                        return;
                    }
                }
            }
        }
        System.out.println("No matching project found.");
    }
}
