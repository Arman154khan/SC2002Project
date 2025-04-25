package Control;

import entity.*;
import entity.enums.ApplicationStatus;
import entity.enums.FlatType;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages all data for the Application CSV.
 */

public final class ApplicationRepository {

    private static final String FILENAME = "csv/ApplicationList.csv";
    private static final List<Application> allApplications = new ArrayList<>();

    private ApplicationRepository() {}

    public static void addApplication(Application application) {
        allApplications.add(application);
    }

    public static List<Application> getAllApplications() {
        return new ArrayList<>(allApplications);
    }

    public static void clearAllApplications() {
        allApplications.clear();
    }

    public static void removeApplication(Application application) {
        allApplications.remove(application);
    }

    public static void loadApplicationsFromCSV(List<Project> allProjects) {
        clearAllApplications();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line = reader.readLine(); 

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] fields = line.split(",");

                if (fields.length < 7) continue;

                String applicationId = fields[0].trim();
                String projectId = fields[1].trim();
                String applicantNric = fields[2].trim();
                FlatType flatType = FlatType.valueOf(fields[3].trim().toUpperCase());
                ApplicationStatus status = ApplicationStatus.valueOf(fields[4].trim().toUpperCase());
                System.out.println(status);
                LocalDate date = LocalDate.parse(fields[5].trim());
                boolean isWithdrawn = Boolean.parseBoolean(fields[6].trim());

                Project project = findProjectById(allProjects, projectId);
                if (project == null) {
                    System.out.println("Skipping application " + applicationId + ": project " + projectId + " not found.");
                    continue;
                }

                Application app = new Application(applicationId, project, applicantNric, flatType);
                app.setStatus(status);
                app.setWithdrawn(isWithdrawn);

                addApplication(app);
            }

            System.out.println("Applications loaded from CSV.");

        } catch (IOException e) {
            System.out.println("Error loading applications: " + e.getMessage());
        }
    }

    public static void saveApplicationsToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            writer.write("ApplicationID,ProjectID,ApplicantNRIC,FlatType,Status,ApplicationDate,IsWithdrawn");
            writer.newLine();

            for (Application a : allApplications) {
                writer.write(String.join(",",
                    a.getApplicationID(),
                    a.getProject().getProjectId(),
                    a.getApplicantNRIC(),
                    a.getFlatType().name(),
                    a.getStatus().name(),
                    a.getApplicationDate().toString(),
                    String.valueOf(a.isWithdrawn())
                ));
                writer.newLine();
            }

            System.out.println("Applications saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving applications: " + e.getMessage());
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
}
