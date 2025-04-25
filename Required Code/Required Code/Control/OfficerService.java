package Control;

import entity.*;
import entity.enums.ApplicationStatus;
import entity.enums.FlatType;

import java.util.List;
/**
 * Officer functionality management. 
 */

public class OfficerService {

    private Officer officer;
    private List<Project> allProjects;
    private List<Application> allApplications;
    private List<Enquiry> allEnquiries;

    public OfficerService(Officer officer, List<Project> projects, List<Application> applications, List<Enquiry> enquiries) {
        this.officer = officer;
        this.allProjects = projects;
        this.allApplications = applications;
        this.allEnquiries = enquiries;
    }

    public boolean registerAsOfficer(Project project) {
        if (officer.getAssignedProjectId() != null && officer.getAssignedProjectId().equalsIgnoreCase(project.getProjectId())) {
            System.out.println("Already assigned to project.");
            return false;
        }

        if (officer.isRegistrationPending()) {
            System.out.println("You already have a pending registration.");
            return false;
        }

        Registration newReg = new Registration(project, officer);
        //allRegistrations.add(newReg)
        project.addOfficerRegistration(officer);
        return true;
    }

    public boolean bookFlatForApplicant(Application application) {
        String assignedProjectId = officer.getAssignedProjectId();
        if (assignedProjectId == null) {
            System.out.println("Officer not assigned to any project.");
            return false;
        }

        if (!application.isBookable()) {
            System.out.println("Application is not in 'Successful' state or has been withdrawn.");
            return false;
        }

        Project assignedProject = getProjectById(assignedProjectId);
        if (!application.getProject().getProjectId().equals(assignedProjectId)) {
            System.out.println("This application is not part of your assigned project.");
            return false;
        }

        if (assignedProject.getFlatUnitCount(application.getFlatType()) == 0) {
            System.out.println("No available units for selected flat type.");
            return false;
        }

        assignedProject.updateFlatAvailability(application.getFlatType(), -1);
        application.setStatus(ApplicationStatus.BOOKED);
        System.out.println("Flat booked successfully for applicant: " + application.getApplicantNRIC());
        return true;
    }

    public String generateReceipt(Application app, Project project) {
        return "======== Flat Booking Receipt ========\n"
                + "Application ID: " + app.getApplicationID() + "\n"
                + "Applicant NRIC: " + app.getApplicantNRIC() + "\n"
                + "Project: " + project.getProjectName() + " (" + project.getProjectId() + ")\n"
                + "Flat Type: " + app.getFlatType() + "\n"
                + "Status: " + app.getStatus() + "\n"
                + "=====================================";
    }

    public void viewAssignedProject() {
        String projectId = officer.getAssignedProjectId();
        Project project = getProjectById(projectId);

        if (project != null) {
            System.out.println("Project: " + project.getProjectName());
            System.out.println("Visible: " + project.isVisible());
            System.out.println("Start-end: " + project.getApplicationOpeningDate() + " to " + project.getApplicationClosingDate());
            System.out.println("Flats: ");
            System.out.println("TWO_ROOM: " + project.getFlatUnitCount(FlatType.TWO_ROOM) + " left");
            System.out.println("THREE_ROOM: " + project.getFlatUnitCount(FlatType.THREE_ROOM) + " left");
        } else {
            System.out.println("No project found.");
        }
    }

    private Project getProjectById(String id) {
        for (Project p : allProjects) {
            if (p.getProjectId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    public void viewApplicantsForProject() {
        if (officer == null || officer.getAssignedProjectId() == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }
    
        String assignedProjectId = officer.getAssignedProjectId();
        Project assignedProject = null;
    
        for (Project p : allProjects) {
            if (p.getProjectId().equalsIgnoreCase(assignedProjectId)) {
                assignedProject = p;
                break;
            }
        }
    
        if (assignedProject == null) {
            System.out.println("Assigned project not found.");
            return;
        }
    
        System.out.println("Applicants for Project: " + assignedProject.getProjectName());
        boolean hasApplicants = false;
    
        for (Application a : allApplications) {
            if (a.getProject().getProjectId().equalsIgnoreCase(assignedProjectId)) {
                System.out.println("- NRIC: " + a.getApplicantNRIC() + " | Flat Type: " + a.getFlatType() + " | Status: " + a.getStatus());
                hasApplicants = true;
            }
        }
    
        if (!hasApplicants) {
            System.out.println("No applications submitted for this project.");
        }
    }    
    
}
