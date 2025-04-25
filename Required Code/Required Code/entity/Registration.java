package entity;

import entity.enums.RegistrationStatus;

/**
 * represents registration 
 */

public class Registration {
    
    private Project project;
    private RegistrationStatus status;
    private Officer officer;


    public Registration(Project project, Officer officer)
    {
        this.project = project;
        this.officer = officer;
        this.status = RegistrationStatus.PENDING;
    }

    public Project getProject()
    {
        return project;
    }

    public Officer getOfficer()
    {
        return officer;
    }

    public RegistrationStatus getStatus()
    {
        return status;
    }

    public void updateRegistrationStatus(RegistrationStatus status)
    {
        this.status = status;
    }

    public boolean assignProject(Project project)
    {
        if(status == RegistrationStatus.APPROVED)
        {
            officer.setAssignedProjectId(project.getProjectId());
            return true;
        }
        return false;
    }

}
