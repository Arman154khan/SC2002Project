package entity;
import entity.enums.ApplicationStatus;
import entity.enums.FlatType;
import java.time.LocalDate;
/**
 * Represents the application entity. 
 */
public class Application {
    private String applicationID;
    private Project project;
    private String applicantNRIC;
    private FlatType flatType;
    private ApplicationStatus status;
    private LocalDate applicationDate;
    private boolean isWithdrawn;
    
    public Application(String applicationId, Project project, String applicantNric, FlatType flatType)
    {
        this.applicationID = applicationId;
        this.project = project;
        this.applicantNRIC = applicantNric;
        this.flatType = flatType;
        this.status = ApplicationStatus.PENDING;
        this.applicationDate = LocalDate.now();
        this.isWithdrawn = false; 
    }

    public String getApplicationID()
    {
        return applicationID;
    }

    public Project getProject()
    {
        return project;
    }

    public String getApplicantNRIC()
    {
        return applicantNRIC;
    }

    public ApplicationStatus getStatus()
    {
        return status;
    }
    
    public LocalDate getApplicationDate()
    {
        return applicationDate;
    }

    public boolean isWithdrawn()
    {
        return isWithdrawn;
    }

    public void setStatus(ApplicationStatus status)
    {
        this.status = status;
    }

    public void setWithdrawn(boolean isWithdrawn) {
        this.isWithdrawn = isWithdrawn;
    }
    
    public void requestWithdrawal()
    {
        this.isWithdrawn = true;
    }

    public boolean isBookable()
    {
        return ApplicationStatus.SUCCESSFUL.equals(status)&&!isWithdrawn;
    }

    public FlatType getFlatType() {
        return flatType;
    }
    
}
