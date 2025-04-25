package Control;

import entity.Applicant;
import entity.enums.ApplicationStatus;
import entity.enums.FlatType;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Generates report for each filter
 */

public class GenerateReport {

    public static void launchReportMenu(List<Applicant> allApplicants, Scanner sc) {
        ApplicantRepository.loadApplicantsFromCSV();
        System.out.println("===== Report Filter Menu =====");
        System.out.println("Filter by:");
        System.out.println("1. Flat Type");
        System.out.println("2. Marital Status");
        System.out.println("3. Application Status");
        System.out.println("4. Combine Filters");
        System.out.println("5. View All Applicants");
        System.out.print("Enter your choice: ");

        int choice = getIntInput(sc, 1, 5);

        switch (choice) {
            case 1 -> {
                FlatType flatType = getFlatTypeInput(sc);
                generateFilteredReport(ApplicantRepository.getAllApplicants(), flatType, null, null);
            }
            case 2 -> {
                String maritalStatus = getMaritalStatusInput(sc);
                generateFilteredReport(ApplicantRepository.getAllApplicants(), null, maritalStatus, null);
            }
            case 3 -> {
                for (Applicant a : ApplicantRepository.getAllApplicants()) {
                    System.out.println("NRIC: " + a.getNric() + " | Status: " + a.getApplicationStatus());
                }                
                ApplicationStatus status = getStatusInput(sc);
                generateFilteredReport(ApplicantRepository.getAllApplicants(), null, null, status);
            }
            case 4 -> {
                FlatType flatType = getFlatTypeInput(sc);
                String maritalStatus = getMaritalStatusInput(sc);
                ApplicationStatus status1 = getStatusInput(sc);
                generateFilteredReport(ApplicantRepository.getAllApplicants(), flatType, maritalStatus, status1);
            }
            case 5 -> generate(allApplicants);
        }
    }

    public static void generate(List<Applicant> applicants) {
        System.out.println("=== FULL APPLICANT REPORT ===");
        printApplicants(applicants);
    }

    public static void generateFilteredReport(List<Applicant> applicants, FlatType flatTypeFilter, String maritalStatusFilter, ApplicationStatus statusFilter) {
        List<Applicant> filtered = applicants.stream()
            .filter(a -> statusFilter == null || a.getApplicationStatus() == statusFilter)
            .filter(a -> flatTypeFilter == null || a.getFlatTypeApplied() == flatTypeFilter)
            .filter(a -> maritalStatusFilter == null || a.getMaritalStatus().equalsIgnoreCase(maritalStatusFilter))
            .collect(Collectors.toList());
    
        System.out.println("=== FILTERED APPLICANT REPORT ===");
        printApplicants(filtered);
    }
    

    private static void printApplicants(List<Applicant> applicants) {
        if (applicants.isEmpty()) {
            System.out.println("No applicants match the criteria.");
            return;
        }

        System.out.printf("%-20s %-15s %-10s %-10s %-10s%n", "Name", "NRIC", "Flat Type", "Marital", "Status");
        System.out.println("-------------------------------------------------------------------");
        for (Applicant a : applicants) {
            System.out.printf("%-20s %-15s %-10s %-10s %-10s%n",
                a.getName(),
                a.getNric(),
                a.getFlatTypeApplied(),
                a.getMaritalStatus(),
                a.getApplicationStatus());
        }
    }

    private static FlatType getFlatTypeInput(Scanner sc) {
        System.out.println("Choose Flat Type: ");
        System.out.println("1. TWO_ROOM");
        System.out.println("2. THREE_ROOM");
        int option = getIntInput(sc, 1, 2);
        return (option == 1) ? FlatType.TWO_ROOM : FlatType.THREE_ROOM;
    }

    private static String getMaritalStatusInput(Scanner sc) {
        System.out.println("Choose Marital Status:");
        System.out.println("1. Married");
        System.out.println("2. Single");
        int choice = getIntInput(sc, 1, 2);
        return (choice == 1) ? "Married" : "Single";
    }

    private static ApplicationStatus getStatusInput(Scanner sc) {
        System.out.println("Choose Application Status:");
        ApplicationStatus[] statuses = ApplicationStatus.values();
        for (int i = 0; i < statuses.length; i++) {
            System.out.println((i + 1) + ". " + statuses[i]);
        }
        int index = getIntInput(sc, 1, statuses.length);
        return statuses[index - 1];
    }

    private static int getIntInput(Scanner sc, int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(sc.nextLine().trim());
                if (input >= min && input <= max) return input;
                System.out.print("Invalid number. Enter between " + min + " and " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
