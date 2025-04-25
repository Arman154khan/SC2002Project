package Boundary;

import entity.*;
import entity.enums.FlatType;
import Control.*;
import java.util.List;
import java.util.Scanner;

/**
 * CLI for Applicant interactions, including applying for BTO projects and managing enquiries.
 */
public class ApplicantMenu {

    private Applicant applicant;
    private List<Project> allProjects;
    private List<Application> allApplications;
    private List<Enquiry> allEnquiries;
    private Scanner sc = new Scanner(System.in);


    public ApplicantMenu(Applicant applicant, List<Project> allProjects, List<Application> allApplications, List<Enquiry> allEnquiries) {
        this.applicant = applicant;
        this.allProjects = allProjects;
        this.allApplications = allApplications;
        this.allEnquiries = allEnquiries;
    }

    public void show() {
        ProjectApplicationService appService = new ProjectApplicationService(applicant);
        ApplicantEnquiryService enquiryService = new ApplicantEnquiryService(EnquiryRepository.getAllEnquiries());
        boolean exit = false;

        while (!exit) {
            System.out.println("\n===== Applicant Menu =====");
            System.out.println("1. View All Projects (Alphabetical)"); 
            System.out.println("2. Filter by Flat Type");
            System.out.println("3. Filter by Neighborhood");
            System.out.println("4. Filter by Opening Date");
            System.out.println("5. Apply for a Project");
            System.out.println("6. Withdraw Application");
            System.out.println("7. View Application Status");
            System.out.println("8. View Applied Project");
            System.out.println("9. Submit Enquiry");
            System.out.println("10. View My Enquiries");
            System.out.println("11. Edit an Enquiry");
            System.out.println("12. Delete an Enquiry");
            System.out.println("13. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput(1, 13);
            FilterType filter;

            switch (choice) {
                case 1: 
                    filter = new FilterType();
                    filter.filter(ProjectRepository.getAllProjects());
                    break;

                case 2:
                    System.out.println("Select flat type: 1. 2-Room  2. 3-Room");
                    int flatOption = getIntInput(1, 2);
                    FlatType type = (flatOption == 1) ? FlatType.TWO_ROOM : FlatType.THREE_ROOM;
                    FilterFlatType flatFilter = new FilterFlatType(type);
                    flatFilter.filter(ProjectRepository.getAllProjects());
                    break;

                case 3:
                    System.out.print("Enter neighborhood to filter by: ");
                    String hood = sc.nextLine();
                    FilterLocation locFilter = new FilterLocation(hood);
                    locFilter.filter(ProjectRepository.getAllProjects());
                    break;

                case 4:
                    System.out.print("Enter opening date (YYYY-MM-DD): ");
                    String date = sc.nextLine();
                    FilterOpeningDate dateFilter = new FilterOpeningDate(null);
                    dateFilter.filter(ProjectRepository.getAllProjects());
                    break;

                case 5:
                    System.out.print("Enter project name to apply for: ");
                    String projectName = sc.nextLine();
                    System.out.println("Select Flat Type: 1. 2-Room  2. 3-Room");
                    int flatChoice = getIntInput(1, 2);
                    FlatType chosen = (flatChoice == 1) ? FlatType.TWO_ROOM : FlatType.THREE_ROOM;
                    Project project = findProjectByName(projectName, chosen);
                    if (project == null) {
                        System.out.println("Project not found.");
                        break;
                    }
                    appService.applyForProject(project, chosen, ApplicationRepository.getAllApplications());
                    break;

                case 6:
                    System.out.print("Enter Application ID to withdraw: ");
                    String withdrawId = sc.nextLine();
                    Application toWithdraw = findApplicationById(withdrawId);
                    if (toWithdraw != null) {
                        appService.withdrawApplication(toWithdraw);
                    } else {
                        System.out.println("Application not found.");
                    }
                    break;

                case 7:
                    appService.viewApplicationStatus(ApplicationRepository.getAllApplications());
                    break;

                case 8:
                    appService.viewAppliedProject(ApplicationRepository.getAllApplications(), ProjectRepository.getAllProjects());
                    break;

                case 9:
                    System.out.print("Enter your question: ");
                    String question = sc.nextLine();
                
                    String projectId = null;
                    for (Application app : ApplicationRepository.getAllApplications()) {
                        if (app.getApplicantNRIC().equalsIgnoreCase(applicant.getNric())) {
                            projectId = app.getProject().getProjectId();
                            break;
                        }
                    }
                
                    if (projectId != null) {
                        enquiryService.submitEnquiry(projectId, question, applicant.getName(), ProjectRepository.getAllProjects());
                    } else {
                        System.out.println("You have not applied to any project. Cannot submit enquiry.");
                    }
                    break;
                

                case 10:
                    List<Enquiry> enquiries = EnquiryRepository.getAllEnquiries();

                    if (enquiries.isEmpty()) {
                        System.out.println("No enquiries found.");
                    } 
                    else {
                        for (Enquiry e : enquiries) {
                            System.out.println("----------------------------");
                            System.out.println("Enquiry ID: " + e.getEnquiryId());
                            System.out.println("Enquirer: " + e.getEnquirerName());
                            System.out.println("Question: " + e.getQuestion());
                            System.out.println("Answer  : " + (e.isResolved() ? e.getAnswer() : "Not answered yet"));
                            System.out.println("Status  : " + (e.isResolved() ? "Resolved" : "Pending"));
                        }
                    }
                    break;
                

                case 11:
                    System.out.print("Enter Enquiry ID to edit: ");
                    String editId = sc.nextLine();
                    Enquiry toEdit = enquiryService.findEnquiryById(editId);
                    if (toEdit != null) {
                        System.out.print("Enter new question: ");
                        String newQ = sc.nextLine();
                        enquiryService.editEnquiry(toEdit, newQ);
                    } else {
                        System.out.println("Enquiry not found.");
                    }
                    break;

                case 12:
                System.out.print("Enter Enquiry ID to delete: ");
                String deleteId = sc.nextLine().trim();
                
                boolean found = false;
                for (Enquiry e : EnquiryRepository.getAllEnquiries()) {
                    if (e.getEnquiryId().equalsIgnoreCase(deleteId)) {
                        EnquiryRepository.removeEnquiry(e);
                        System.out.println("Enquiry deleted.");
                        found = true;
                        break;
                    }
                }
                
                if (!found) {
                    System.out.println("Enquiry not found.");
                }
                
                    break;

                case 13:
                    exit = true;
                    break;
            }
        }

        System.out.println("Logging out...");
    }

    private Project findProjectByName(String name, FlatType chosen) {
        for (Project p : ProjectRepository.getAllProjects()) {
            if (p.isEligible(applicant, chosen)){
                if (p.getProjectName().equalsIgnoreCase(name)) return p;
            }  
        }
        return null;
    }

    private Application findApplicationById(String id) {
        for (Application a : ApplicationRepository.getAllApplications()) {
            if (a.getApplicationID().equalsIgnoreCase(id)) return a;
        }
        return null;
    }

    private int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(sc.nextLine());
                if (input >= min && input <= max) return input;
                System.out.print("Invalid. Try again: ");
            } catch (Exception e) {
                System.out.print("Invalid input. Try again: ");
            }
        }
    }
}
