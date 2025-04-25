package Control;

import entity.*;
import entity.enums.RegistrationStatus;
import java.util.List;

/**
 * For potential officers to reigster to become an officer
 */


public class OfficerRegistrationService {

    private Officer officer;
    private List<Registration>allRegistrations;


    public OfficerRegistrationService(Officer officer, List<Registration> allRegistrations)
    {
        this.officer = officer;
        this.allRegistrations = allRegistrations;
    }

    public Registration registerForProject(Project project)
    {
        Registration registration = new Registration(project, officer);
        RegistrationRepository.addRegistration(registration);
        System.out.println("Registration submitted for project:" + project.getProjectName());
        return registration;
    }

    public void viewRegistrationStatus(Registration registration)
    {
        System.out.println("Project: " + registration.getProject().getProjectName());
        System.out.println("Status: " + registration.getStatus());
    }

    public void viewProjectHandling()
    {
        String assigned = officer.getAssignedProjectId();
        if(assigned != null)
        {
            System.out.println("Currently handling project: " + assigned);
        }
        else
        {
            System.out.println("No project assigned yet.");
        }
    }
    
}
