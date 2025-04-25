package entity;

import java.util.ArrayList;
import java.util.List;

import Control.ProjectRepository;

/**
 * Represents the manager entity.
 */

public class HDBManager extends User {
    
    private Project currentProjectHandling;
    private List<String> createdProjects = new ArrayList<>();
    private List<Applicant> applicationWithdrawalRequests = new ArrayList<>();
    private List<Applicant> allApplicantsOfAllProjects = new ArrayList<>();

   

    public HDBManager(String name, String nric, int age, String maritalStatus, String password, String userType) {
        super(name, nric, age, maritalStatus, password, userType);  
    }

    @Override
    public String getRole()
    {
        return "Manager";
    }

    public List<String> getCreatedProjects() {
        createdProjects.clear();
        for (Project p : ProjectRepository.getAllProjects()){
            if(p.getManagerInCharge().getNric().equalsIgnoreCase(this.getNric())){
                createdProjects.add(p.getProjectId());
            }
        }
        return createdProjects;
    }

    public Project getCurrentProjectHandling() {
        return currentProjectHandling;
    }

    public void setCurrentProjectHandling(Project p) {
        this.currentProjectHandling = p;
    }

    public List<Applicant> getApplicationWithdrawalRequests() {
        return applicationWithdrawalRequests;
    }

    public List<Applicant> getAllApplicantsOfAllProjects() {
        return allApplicantsOfAllProjects;
    }

    public void addApplicantToAllApplicants(Applicant applicant) {
        if (!allApplicantsOfAllProjects.contains(applicant)) {
            allApplicantsOfAllProjects.add(applicant);
        }
    }

    public void removeApplicantFromAllApplicants(Applicant applicant) {
        allApplicantsOfAllProjects.remove(applicant);
    }

    public void addWithdrawalRequest(Applicant applicant) {
        if (!applicationWithdrawalRequests.contains(applicant)) {
            applicationWithdrawalRequests.add(applicant);
        }
    }

    public void removeWithdrawalRequest(Applicant applicant) {
        applicationWithdrawalRequests.remove(applicant);
    }
}
