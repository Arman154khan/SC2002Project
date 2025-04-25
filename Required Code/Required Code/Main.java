import Control.*;
import Boundary.ApplicantMenu;
import Boundary.OfficerMenu;
import Boundary.ManagerMenu;
import entity.*;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void saveAllData() {
        ProjectRepository.saveProjectsToCSV();
        ApplicationRepository.saveApplicationsToCSV();
        EnquiryRepository.saveEnquiriesToCSV();
        RegistrationRepository.saveRegistrationsToCSV();
        OfficerRepository.saveOfficersToCSV();
        ApplicantRepository.saveApplicantsToCSV();
        ManagerRepository.saveManagersToCSV();
    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        ManagerRepository.loadManagersFromCSV();
        ProjectRepository.loadProjectsFromCSV();
        ProjectRepository.autoUpdateAllProjectsVisibility();

        OfficerRepository.loadOfficersFromCSV();
        ApplicantRepository.loadApplicantsFromCSV();
        
        ApplicationRepository.loadApplicationsFromCSV(ProjectRepository.getAllProjects());
        EnquiryRepository.loadEnquiriesFromCSV(ProjectRepository.getAllProjects());
        RegistrationRepository.loadRegistrationsFromCSV(ProjectRepository.getAllProjects(), OfficerRepository.getAllOfficers());

        List<Project> allProjects = ProjectRepository.getAllProjects();
        List<Officer> allOfficers = OfficerRepository.getAllOfficers();
        List<Applicant> allApplicants = ApplicantRepository.getAllApplicants();
        List<HDBManager> allManagers = ManagerRepository.getAllManagers();
        List<Application> allApplications = ApplicationRepository.getAllApplications();
        List<Enquiry> allEnquiries = EnquiryRepository.getAllEnquiries();
        List<Registration> allRegistrations = RegistrationRepository.getAllRegistrations();

        LoginSystem loginSystem = new LoginSystem();
        User loggedInUser = loginSystem.start();

        if (loggedInUser.getRole().equals("Applicant")) {
            ApplicantMenu menu = new ApplicantMenu((Applicant) loggedInUser,  ProjectRepository.getAllProjects(), allApplications, allEnquiries);
            menu.show();
        } else if (loggedInUser.getRole().equals("Officer")) {
            OfficerMenu menu = new OfficerMenu((Officer) loggedInUser, ProjectRepository.getAllProjects(), allApplications, allEnquiries, allApplicants);
            menu.show();
        } else if (loggedInUser.getRole().equals("Manager")) {
            ManagerMenu menu = new ManagerMenu((HDBManager) loggedInUser, ProjectRepository.getAllProjects(), allApplications, allRegistrations, allEnquiries, allOfficers, allApplicants);
            menu.show();
        } else {
            System.out.println("Unrecognized user type. Exiting.");
        }

        saveAllData();
    }
}
