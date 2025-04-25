package Boundary;

import entity.*;
import entity.enums.ApplicationStatus;
import entity.enums.FlatType;
import entity.enums.RegistrationStatus;
import Control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * CLI for Managers to manage projects and officer registrations.
 */
public class ManagerMenu {

    private HDBManager manager;
    private List<Project> allProjects;
    private List<Registration> allRegistrations;
    private List<Officer> allOfficers;
    private List<Application> allApplications;
    private List<Applicant> allApplicants;
    private List<Enquiry> allEnquiries;
    private Scanner sc = new Scanner(System.in);

    public ManagerMenu(HDBManager manager, List<Project> allProjects, List<Application> allApplications,
    List<Registration> allRegistrations, List<Enquiry> allEnquiries,
    List<Officer> allOfficers, List<Applicant> allApplicants) {
        this.manager = manager;
        this.allProjects = allProjects;
        this.allRegistrations = allRegistrations;
        this.allApplications = allApplications;
        this.allApplicants = allApplicants;
        this.allEnquiries = allEnquiries;
        this.allOfficers = allOfficers;
    }

    public void show() {
        boolean exit = false;
        ProjectManagementService projectManagementService = new ProjectManagementService(manager);

        while (!exit) {
            System.out.println("\n===== Manager Menu =====");
            System.out.println("1. Create New Project");
            System.out.println("2. Edit Project");
            System.out.println("3. Delete Project");
            System.out.println("4. Toggle Project Visibility");
            System.out.println("5. View All Projects");
            System.out.println("6. View Own Projects");
            System.out.println("7. View Officer Registrations");
            System.out.println("8. Approve/Reject Officer Registration");
            System.out.println("9. Approve/Reject BTO Applications");
            System.out.println("10. Approve/Reject Application Withdrawals");
            System.out.println("11. Generate Report");
            System.out.println("12. View All Enquiries");
            System.out.println("13. Reply to Project Enquiries");
            System.out.println("14. Exit");
            System.out.print("Enter your choice: ");

            int choice = getIntInput(1, 14);

            switch (choice) {
                case 1:
                    String name;
                    while (true) {
                        System.out.print("Enter project name: ");
                        name = sc.nextLine().trim();
                        if (!name.isEmpty()) break;
                        System.out.println("Project name cannot be blank.");
                    }

                    String hood;
                    while (true) {
                        System.out.print("Enter neighborhood: ");
                        hood = sc.nextLine().trim();
                        if (!hood.isEmpty()) break;
                        System.out.println("Neighborhood cannot be blank.");
                    }

                    int twoRoom;
                    while (true) {
                        try {
                            System.out.print("Enter number of 2-room units: ");
                            String input = sc.nextLine().trim();
                            twoRoom = Integer.parseInt(input);
                            if (twoRoom >= 0 && twoRoom <= 500) break;
                            System.out.println("Must be between 0 and 500.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number.");
                        }
                    }

                    int threeRoom;
                    while (true) {
                        try {
                            System.out.print("Enter number of 3-room units: ");
                            String input = sc.nextLine().trim();
                            threeRoom = Integer.parseInt(input);
                            if (threeRoom >= 0 && threeRoom <= 500) break;
                            System.out.println("Must be between 0 and 500.");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number.");
                        }
                    }

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    LocalDate opening;
                    while (true) {
                        try {
                            System.out.print("Enter opening date (YYYY-MM-DD): ");
                            String input = sc.nextLine().trim();
                            opening = LocalDate.parse(input, formatter);
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format.");
                        }
                    }

                    LocalDate closing;
                    while (true) {
                        try {
                            System.out.print("Enter closing date (YYYY-MM-DD): ");
                            String input = sc.nextLine().trim();
                            closing = LocalDate.parse(input, formatter);
                            if (closing.isAfter(opening)) break;
                            System.out.println("Closing date must be after opening date.");
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date format.");
                        }
                    }

                    String id = name.toLowerCase().replaceAll("\\s", "");

                    projectManagementService.createProject(id, name, hood, twoRoom, threeRoom, opening, closing);
                    break;

                case 2:
                    System.out.print("Enter name of project to edit: ");
                    String editProj = sc.nextLine().trim();

                    Project selectedProject = null;

                    for (Project p1 : ProjectRepository.getAllProjects()) {
                        if (editProj.equalsIgnoreCase(p1.getProjectName())) {
                            for (String mid : manager.getCreatedProjects()) {
                                // System.out.println(mid + " " + p1.getProjectId());
                                if (mid.equalsIgnoreCase(p1.getProjectId())) {
                                    selectedProject = p1;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    
                    if (selectedProject == null) {
                        System.out.println("Invalid project name or you do not have permission to edit this project.");
                        break;
                    }

                    System.out.print("Edit Project (Leave blank if no edits)\n");

                    System.out.print("Enter new project name: ");
                    String nameE = sc.nextLine();
                    if (nameE.isEmpty()){
                        nameE = selectedProject.getProjectName();
                    }

                    System.out.print("Enter new neighborhood: ");
                    String hoodE = sc.nextLine();
                    if (hoodE.isEmpty()){
                        hoodE = selectedProject.getNeighbourhood();
                    }
                
                    System.out.print("Enter new number of 2-room units: ");
                    String twoRoomStr = sc.nextLine();
                    
                    int twoRoomE;
                    
                    if (twoRoomStr.isEmpty()) {
                        twoRoomE = selectedProject.getFlatUnitCount(FlatType.TWO_ROOM);
                    } else {
                        while (true) {
                            try {
                                int input = Integer.parseInt(twoRoomStr);
                    
                                int min = 0;
                                int max = 500;
                    
                                if (input >= min && input <= max) {
                                    twoRoomE = input;
                                    break;
                                } else {
                                    System.out.print("Invalid. Try again: ");
                                    twoRoomStr = sc.nextLine();
                                }
                            } catch (NumberFormatException e) {
                                System.out.print("Invalid input. Please enter a number: ");
                                twoRoomStr = sc.nextLine();
                            }
                        }
                    }

                    System.out.print("Enter new number of 3-room units: ");
                    String threeRoomStr = sc.nextLine();

                    int threeRoomE;

                    if (threeRoomStr.isEmpty()) {
                        threeRoomE = selectedProject.getFlatUnitCount(FlatType.THREE_ROOM);
                    } else {
                        while (true) {
                            try {
                                int input = Integer.parseInt(threeRoomStr);

                                int min = 0;
                                int max = 500;

                                if (input >= min && input <= max) {
                                    threeRoomE = input;
                                    break;
                                } else {
                                    System.out.print("Invalid. Try again: ");
                                    threeRoomStr = sc.nextLine();
                                }
                            } catch (NumberFormatException e) {
                                System.out.print("Invalid input. Please enter a number: ");
                                threeRoomStr = sc.nextLine();
                            }
                        }
                    }

                    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    System.out.print("Enter new opening date (YYYY-MM-DD): ");
                    String openingStr = sc.nextLine();

                    LocalDate openingE;
                    if (openingStr.isEmpty()) {
                        openingE = selectedProject.getApplicationOpeningDate();
                    } else {
                        while (true) {
                            try {
                                openingE = LocalDate.parse(openingStr, formatter1);
                                break;
                            } catch (DateTimeParseException e) {
                                System.out.print("Invalid date format. Try again (YYYY-MM-DD): ");
                                openingStr = sc.nextLine();
                            }
                        }
                    }

                    System.out.print("Enter new closing date (YYYY-MM-DD): ");
                    String closingStr = sc.nextLine();

                    LocalDate closingE;
                    if (closingStr.isEmpty()) {
                        closingE = selectedProject.getApplicationOpeningDate();
                    } else {
                        while (true) {
                            try {
                                closingE = LocalDate.parse(closingStr, formatter1);
                                if (closingE.isAfter(openingE)) {
                                    break;
                                } else {
                                    System.out.print("Closing date must be after opening date. Try again: ");
                                    closingStr = sc.nextLine();
                                }
                            } catch (DateTimeParseException e) {
                                System.out.print("Invalid date format. Try again (YYYY-MM-DD): ");
                                closingStr = sc.nextLine();
                            }
                        }
                    }

                    projectManagementService.editProject(selectedProject, nameE, hoodE, twoRoomE, threeRoomE, openingE, closingE);
                    break;

                case 3:
                    System.out.print("Enter name of project to delete: ");
                    String deleteProj = sc.nextLine();

                    Project deletedProject = null;

                    for (Project p1 : ProjectRepository.getAllProjects()) {
                        if (deleteProj.equalsIgnoreCase(p1.getProjectName())) {
                            for (String mid : manager.getCreatedProjects()) {
                                // System.out.println(mid + " " + p1.getProjectId());
                                if (mid.equalsIgnoreCase(p1.getProjectId())) {
                                    deletedProject = p1;
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    
                    if (deletedProject == null) {
                        System.out.println("Invalid project name or you do not have permission to delete this project.");
                        break;
                    }

                    projectManagementService.deleteProject(deletedProject);
                    break;

                case 4:
                    System.out.print("Enter project name to toggle visibility: ");
                    String projectName = sc.nextLine();
                    Project p = findProjectByName(projectName);
                    if (p != null) {
                        projectManagementService.toggleProjectVisibility(p);
                        // System.out.println("Visibility toggled. Now: " + (p.isVisible() ? "OPEN" : "CLOSED"));
                    } else {
                        System.out.println("Project not found.");
                    }
                    break;

                case 5:
                    boolean found1 = false;
                    for (Project proj : ProjectRepository.getAllProjects()) {
                        System.out.println("- " + proj.getProjectName() + " | " + proj.getNeighbourhood() + " | " + (proj.isVisible() ? "OPEN" : "CLOSED"));
                        found1 = true;
                    }

                    if(!found1){
                        System.out.println("No Current Projects");
                    }
                    break;

                case 6:
                    boolean found2 = false;
                    for (Project proj : ProjectRepository.getAllProjects()) {
                        if(proj.getManagerInCharge().getNric().equalsIgnoreCase(manager.getNric())){
                            System.out.println("- " + proj.getProjectName() + " | " + proj.getNeighbourhood() + " | " + (proj.isVisible() ? "OPEN" : "CLOSED\n") +
                            "\nTwo Room Flats: " + proj.getFlatUnitCount(FlatType.TWO_ROOM) + "\n" + "Three Room Flats: " + proj.getFlatUnitCount(FlatType.THREE_ROOM) +
                            "\nApplication Window:" + proj.getApplicationOpeningDate() + " to " + proj.getApplicationClosingDate());
                            found2 = true;
                        }
                    }

                    if(!found2){
                        System.out.println("You have no projects");
                    }
                    break;

                case 7:
                    System.out.println("=== Registrations for Your Projects ===");
                    boolean found = false;
                    for (Registration r : RegistrationRepository.getAllRegistrations()) {
                        if(manager.getCreatedProjects().contains(r.getProject().getProjectId())){
                            System.out.println("Officer: " + r.getOfficer().getName() + " | Project: " + r.getProject().getProjectName() + " | Status: " + r.getStatus());
                            found = true;
                        }
                    }

                    if (!found) {
                        System.out.println("No registrations found.");
                    }

                    break;

                case 8:
                    System.out.print("Enter officer name to process registration: ");
                    String officerName = sc.nextLine();
                    Registration reg = findPendingRegistrationByOfficer(officerName);
                    if (reg == null) {
                        System.out.println("Pending registration not found.");
                        break;
                    }

                    HDBOfficerManagementService hdbOfficerManagementService = new HDBOfficerManagementService(reg.getProject());

                    System.out.println("1. Approve");
                    System.out.println("2. Reject");
                    int decision = getIntInput(1, 2);
                    if (decision == 1) {
                        hdbOfficerManagementService.approveOfficerRegistration(reg);
                    } else {
                        hdbOfficerManagementService.rejectOfficerRegistration(reg);
                    }
                    break;

                case 9:
                    System.out.println("=== Applications for Your Projects ===");

                    List<Application> managerApplications = new ArrayList<>();
                    int index = 1;

                    for (Application a : ApplicationRepository.getAllApplications()) {
                        if (a.getProject().getManagerInCharge().equals(manager) && a.getStatus().equals(ApplicationStatus.PENDING)) {
                            System.out.println(index + ". " + a.getApplicantNRIC() +
                                            " | Project: " + a.getProject().getProjectName() +
                                            " | Flat Type: " + a.getFlatType() +
                                            " | Status: " + a.getStatus());
                            managerApplications.add(a);
                            index++;
                        }
                    }

                    if (managerApplications.isEmpty()) {
                        System.out.println("No applications for your projects.");
                        break;
                    }
                    System.out.print("Enter the number of the application to handle: ");
                    int option = sc.nextInt();
                    sc.nextLine();

                    if (option < 1 || option > managerApplications.size()) {
                        System.out.println("Invalid selection.");
                        break;
                    }

                    Application selectedApp = managerApplications.get(option - 1);

                    System.out.print("Approve (A) or Reject (R)? ");
                    String action = sc.nextLine().trim().toUpperCase();

                    ApplicantManagementService ams = new ApplicantManagementService(selectedApp.getProject());

                    if (action.equals("A")) {
                        ams.approveApplicant(selectedApp);
                    } else if (action.equals("R")) {
                        ams.rejectApplicant(selectedApp);
                    } else {
                        System.out.println("Invalid input.");
                    }
                    break;
                case 10:
                    System.out.println("=== Withdrawals for Your Applications ===");

                    List<Application> applicationWithdrawals = new ArrayList<>();
                    int index1 = 1;

                    for (Application a1 : ApplicationRepository.getAllApplications()) {
                        if (a1.getProject().getManagerInCharge().equals(manager) && a1.getStatus().equals(ApplicationStatus.WITHDRAWPENDING)) {
                            System.out.println(index1 + ". " + a1.getApplicantNRIC() +
                                            " | Project: " + a1.getProject().getProjectName() +
                                            " | Flat Type: " + a1.getFlatType() +
                                            " | Status: " + a1.getStatus());
                            applicationWithdrawals.add(a1);
                            index1++;
                        }
                    }

                    if (applicationWithdrawals.isEmpty()) {
                        System.out.println("No withdrawals for your projects.");
                        break;
                    }

                    System.out.print("Enter the number of the application to handle: ");
                    int option1 = sc.nextInt();
                    sc.nextLine();

                    if (option1 < 1 || option1 > applicationWithdrawals.size()) {
                        System.out.println("Invalid selection.");
                        break;
                    }

                    Application selectedWithdrawal = applicationWithdrawals.get(option1 - 1);

                    System.out.print("Approve (A) or Reject (R)? ");
                    String action1 = sc.nextLine().trim().toUpperCase();

                    ApplicantManagementService ams1 = new ApplicantManagementService(selectedWithdrawal.getProject());

                    if (action1.equals("A")) {
                        ams1.approveApplicationWithdrawal(selectedWithdrawal, manager);
                    } else if (action1.equals("R")) {
                        ams1.rejectApplicationWithdrawal(manager, selectedWithdrawal);
                    } else {
                        System.out.println("Invalid input.");
                    }
                    break;
                case 11:
                    GenerateReport.launchReportMenu(ApplicantRepository.getAllApplicants(), sc);
                break;
            

                case 12:
                    System.out.println("=== All Enquiries ===");

                    for (Enquiry e : EnquiryRepository.getAllEnquiries()) {
                        System.out.println("----------------------------");
                        System.out.println("Enquirer: " + e.getEnquirerName());
                        System.out.println("Question: " + e.getQuestion());
                        System.out.println("Answer  : " + (e.isResolved() ? e.getAnswer() : "Not answered yet"));
                        System.out.println("Status  : " + (e.isResolved() ? "Resolved" : "Pending"));
                    }
                    System.out.println("----------------------------");

                case 13:

                    System.out.println("=== Respond to Project Enquiries ===");

                    List<Enquiry> managerEnquiries = new ArrayList<>();
                    int count = 1;
                    for (Enquiry e : EnquiryRepository.getAllEnquiries()) {
                        if (e.getLinkedProject() != null && e.getLinkedProject().getManagerInCharge().equals(manager)) {
                            System.out.println(count + ". " + e.getQuestion() +
                                            " | From: " + e.getEnquirerName() +
                                            " | Status: " + (e.isResolved() ? "Resolved" : "Pending"));
                            managerEnquiries.add(e);
                            count++;
                        }
                    }

                    if (managerEnquiries.isEmpty()) {
                        System.out.println("No enquiries related to your projects.");
                        break;
                    }

                    System.out.print("Enter the number of the enquiry to respond to: ");
                    int selectedIdx = getIntInput(1, managerEnquiries.size());
                    Enquiry selectedEnquiry = managerEnquiries.get(selectedIdx - 1);

                    System.out.println("Question: " + selectedEnquiry.getQuestion());
                    if (selectedEnquiry.isResolved()) {
                        System.out.println("This enquiry has already been resolved: " + selectedEnquiry.getAnswer());
                        break;
                    }

                    System.out.print("Enter your response: ");
                    String response = sc.nextLine();
                    selectedEnquiry.setAnswer(response);
                    selectedEnquiry.setResolved(true);

                    System.out.println("Response submitted. Enquiry marked as resolved.");
                    break;


                case 14:
                    exit = true;
                    break;
            }
        }

        System.out.println("Logging out...");
    }
    

    private Project findProjectByName(String name) {
        for (Project p : ProjectRepository.getAllProjects()) {
            if (p.getProjectName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }

    private Registration findPendingRegistrationByOfficer(String officerName) {
        for (Registration r : RegistrationRepository.getAllRegistrations()) {
            if (r.getOfficer().getName().equalsIgnoreCase(officerName) &&
                r.getStatus() == RegistrationStatus.PENDING) {
                return r;
            }
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
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
