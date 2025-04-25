package Control;

import entity.*;
import entity.enums.RegistrationStatus;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles data from the registration csv
 */
public final class RegistrationRepository {

    private static final String FILENAME = "csv/RegistrationList.csv";
    private static final List<Registration> allRegistrations = new ArrayList<>();

    private RegistrationRepository() {}

    public static void addRegistration(Registration registration) {
        allRegistrations.add(registration);
    }

    public static List<Registration> getAllRegistrations() {
        return new ArrayList<>(allRegistrations);
    }

    public static void clearAllRegistrations() {
        allRegistrations.clear();
    }

    public static void removeRegistration(Registration registration) {
        allRegistrations.remove(registration);
    }

    public static void loadRegistrationsFromCSV(List<Project> allProjects, List<Officer> allOfficers) {
        clearAllRegistrations();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line = reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] fields = line.split(",");

                if (fields.length < 3) continue;

                String officerNric = fields[0].trim();
                String projectId = fields[1].trim();
                String statusStr = fields[2].trim();

                Project project = findProjectById(allProjects, projectId);
                Officer officer = findOfficerByNric(allOfficers, officerNric);
                RegistrationStatus status = RegistrationStatus.valueOf(statusStr.toUpperCase());

                if (project == null || officer == null) {
                    System.out.println("Warning: Skipping registration due to missing project or officer.");
                    continue;
                }

                Registration reg = new Registration(project, officer);
                reg.updateRegistrationStatus(status);
                addRegistration(reg);
            }

            System.out.println("Registrations loaded from CSV.");
        } catch (IOException e) {
            System.out.println("Error loading registrations: " + e.getMessage());
        }
    }

    public static void saveRegistrationsToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            writer.write("OfficerNRIC,ProjectId,Status");
            writer.newLine();

            for (Registration r : allRegistrations) {
                writer.write(String.join(",",
                    r.getOfficer().getNric(),
                    r.getProject().getProjectId(),
                    r.getStatus().name()
                ));
                writer.newLine();
            }

            System.out.println("Registrations saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving registrations: " + e.getMessage());
        }
    }

    private static Project findProjectById(List<Project> allProjects, String id) {
        for (Project p : allProjects) {
            if (p.getProjectId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    private static Officer findOfficerByNric(List<Officer> allOfficers, String nric) {
        for (Officer o : allOfficers) {
            if (o.getNric().equalsIgnoreCase(nric)) {
                return o;
            }
        }
        return null;
    }
}
