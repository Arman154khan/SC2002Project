package Control;

import entity.Applicant;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the repository that manages all data for the Applicant CSV. 
 */

public final class ApplicantRepository {

    private static final String FILENAME = "csv/ApplicantList.csv";
    private static final List<Applicant> allApplicants = new ArrayList<>();

    private ApplicantRepository() {}

    public static void addApplicant(Applicant applicant) {
        allApplicants.add(applicant);
    }

    public static List<Applicant> getAllApplicants() {
        return new ArrayList<>(allApplicants);
    }

    public static void clearAllApplicants() {
        allApplicants.clear();
    }

    public static void removeApplicant(Applicant applicant) {
        allApplicants.remove(applicant);
    }

    public static void loadApplicantsFromCSV() {
        clearAllApplicants();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line = reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] fields = line.split(",");

                if (fields.length < 5) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }

                String name = fields[0].trim();
                String nric = fields[1].trim();
                int age = Integer.parseInt(fields[2].trim());
                String maritalStatus = fields[3].trim();
                String password = fields[4].trim();

                Applicant applicant = new Applicant(name, nric, age, maritalStatus, password, "Applicant");
                addApplicant(applicant);
            }

            System.out.println("Applicants loaded from CSV.");
        } catch (IOException e) {
            System.out.println("Error loading applicants: " + e.getMessage());
        }
    }

    public static void saveApplicantsToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            writer.write("Name,NRIC,Age,MaritalStatus,Password");
            writer.newLine();

            for (Applicant a : allApplicants) {
                writer.write(String.join(",",
                    a.getName(),
                    a.getNric(),
                    String.valueOf(a.getAge()),
                    a.getMaritalStatus(),
                    a.getPassword()
                ));
                writer.newLine();
            }

            System.out.println("Applicants saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving applicants: " + e.getMessage());
        }
    }
}
