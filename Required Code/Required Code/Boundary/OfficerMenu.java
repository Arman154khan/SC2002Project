package Boundary;

import entity.*;
import entity.enums.FlatType;
import Control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * CLI for HDB Officers to manage their assigned BTO project and enquiries.
 */
public class OfficerMenu {

    private Officer officer;
    private List<Project> allProjects;
    private List<Application> allApplications;
    private List<Enquiry> allEnquiries;
    private List<Applicant> allApplicants;
    private Scanner sc = new Scanner(System.in);

    public OfficerMenu(Officer officer, List<Project> allProjects, List<Application> allApplications, List<Enquiry> allEnquiries, List<Applicant> allApplicants) {
        this.officer = officer;
        this.allProjects = allProjects;
        this.allApplications = allApplications;
        this.allEnquiries = allEnquiries;
        this.allApplicants = allApplicants;
    }
    
    public void show() {
        OfficerService officerService = new OfficerService(officer, ProjectRepository.getAllProjects(), ApplicationRepository.getAllApplications(), EnquiryRepository.getAllEnquiries());
        OfficerEnquiryService enquiryService = new OfficerEnquiryService();
        OfficerRegistrationService regService = new OfficerRegistrationService(officer, RegistrationRepository.getAllRegistrations());
        CommitteeEnquiryService committeeService = new CommitteeEnquiryService();
        ReceiptGenerator receiptService = new ReceiptGenerator();

        boolean exit = false;

        while (!exit) {
            System.out.println("\n===== Officer Menu =====");
            System.out.println("1. View Assigned Project");
            System.out.println("2. View Applications for My Project");
            System.out.println("3. Book Flat for Applicant");
            System.out.println("4. Generate Receipt");
            System.out.println("5. View Enquiries");
            System.out.println("6. Respond to Enquiry");
            System.out.println("7. Act as applicant");
            System.out.println("8. Apply to join a project.");
            System.out.println("9. View registration Status");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput(1, 10);

            switch (choice) {
                case 1:
                    officerService.viewAssignedProject();
                    break;

                case 2:
                    officerService.viewApplicantsForProject(); 
                    break;

                case 3:
                    System.out.print("Enter applicant NRIC: ");
                    String nric = sc.nextLine();
                    Application app = findApplicationByNric(nric);
                    if (app == null) {
                        System.out.println("No application found.");
                        break;
                    }

                    boolean booked = officerService.bookFlatForApplicant(app);
                    if (!booked) {
                        System.out.println("Flat booking failed.");
                    }
                    break;

                case 4:
                    System.out.print("Enter applicant NRIC to generate receipt: ");
                    String receiptNric = sc.nextLine();

                    Applicant applicant = findApplicantByNric(receiptNric);
                    Application receiptApp = findApplicationByNric(receiptNric);
                    Project project = getAssignedProject();

                    if (receiptApp != null && applicant != null && project != null) {
                        receipt receipt = receiptService.generateReceipt(receiptApp, applicant, project);
                        System.out.println("=== Receipt ===");
                        System.out.println(receipt);
                    } else {
                        System.out.println("Missing application/applicant/project info. Receipt not generated.");
                    }
                    break;

                case 5:
                    enquiryService.viewEnquiry(officer, EnquiryRepository.getAllEnquiries());
                    break;

                case 6:
                    System.out.print("Enter enquiry ID to respond to: ");
                    String enquiryId = sc.nextLine();
                    Enquiry selected = findEnquiryById(enquiryId);
                    if (selected != null) {
                        System.out.print("Enter your answer: ");
                        String reply = sc.nextLine();
                        committeeService.answerEnquiry(selected, reply);
                    } else {
                        System.out.println("Enquiry not found.");
                    }
                    break;
                
                case 7:
                    Applicant asApplicant = findApplicantByNric(officer.getNric());
                    if (asApplicant == null) {
                        asApplicant = new Applicant(
                            officer.getName(),
                            officer.getNric(),
                            officer.getAge(),
                            officer.getMaritalStatus(),
                            officer.getPassword(),  
                            "Applicant"
                        );
                        ApplicantRepository.getAllApplicants().add(asApplicant); 
                    }
                    ApplicantMenu applicantMenu = new ApplicantMenu(asApplicant, ProjectRepository.getAllProjects(), ApplicationRepository.getAllApplications(), EnquiryRepository.getAllEnquiries());
                    applicantMenu.show();
                    break;

                case 8:
                    System.out.println("What project would you like to apply for? ");
                    String projectName = sc.nextLine();
                    Project p = findProjectByName(projectName);
                    if (p != null)
                    {
                        regService.registerForProject(p);
                        break;
                    }
                    else
                    {
                        System.out.println("Invalid project name!");
                        break;
                    }

                case 9:
                    List <Registration> rList = findRegistrationbyOfficer(officer);
                    if(!rList.isEmpty())
                    {
                        for(Registration r:rList)
                        {
                            regService.viewRegistrationStatus(r);
                        }
                        break;
                    }
                    System.out.println("No active registration");
                    break;
                    

                case 10:
                    exit = true;
                    break;
            }
        }

        System.out.println("Logging out...");
    }

    private Project getAssignedProject() {
        for (Project p : ProjectRepository.getAllProjects()) {
            if (p.getProjectId().equalsIgnoreCase(officer.getAssignedProjectId())) {
                return p;
            }
        }
        return null;
    }

    private Applicant findApplicantByNric(String nric) {
        for (Applicant a : ApplicantRepository.getAllApplicants()) {
            if (a.getNric().equalsIgnoreCase(nric)) return a;
        }
        return null;
    }
    

    private Application findApplicationByNric(String nric) {
        for (Application a : ApplicationRepository.getAllApplications()) {
            if (a.getApplicantNRIC().equalsIgnoreCase(nric)) return a;
        }
        return null;
    }

    private Enquiry findEnquiryById(String id) {
        for (Enquiry e : EnquiryRepository.getAllEnquiries()) {
            if (e.getEnquiryId().equalsIgnoreCase(id)) return e;
        }
        return null;
    }

    private Project findProjectByName(String name) {
        for (Project p : ProjectRepository.getAllProjects()) {
            if (p.getProjectName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }

    private List<Registration> findRegistrationbyOfficer(Officer officer) {
        List <Registration> rList = new ArrayList<>();
        for (Registration r : RegistrationRepository.getAllRegistrations()) {
            if (officer.getNric().equalsIgnoreCase(r.getOfficer().getNric()))
            {
               rList.add(r);
            }
        }
        return rList;
    }

    private int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(sc.nextLine());
                if (input >= min && input <= max) return input;
                System.out.print("Invalid. Enter a number between " + min + " and " + max + ": ");
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
