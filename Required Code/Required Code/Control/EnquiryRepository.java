package Control;

import entity.Enquiry;
import entity.Project;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages all data for the enquiry CSV. 
 */

public final class EnquiryRepository {

    private static final String FILENAME = "csv/EnquiryRepository.csv";
    private static final List<Enquiry> allEnquiries = new ArrayList<>();

    private EnquiryRepository() {}

    public static void addEnquiry(Enquiry enquiry) {
        allEnquiries.add(enquiry);
    }

    public static List<Enquiry> getAllEnquiries() {
        return new ArrayList<>(allEnquiries);
    }

    public static void clearAllEnquiries() {
        allEnquiries.clear();
    }

    public static void removeEnquiry(Enquiry enquiry) {
        allEnquiries.remove(enquiry);
    }

    public static void loadEnquiriesFromCSV(List<Project> allProjects) {
        clearAllEnquiries();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line = reader.readLine(); 

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] fields = line.split(",");

                if (fields.length < 6) continue;

                String enquiryId = fields[0].trim();
                String question = fields[1].trim();
                String answer = fields[2].trim();
                boolean isResolved = Boolean.parseBoolean(fields[3].trim());
                String enquirerName = fields[4].trim();
                String projectId = fields[5].trim();

                Project linkedProject = findProjectById(allProjects, projectId);
                if (linkedProject == null) {
                    System.out.println("Skipping enquiry " + enquiryId + ": project " + projectId + " not found.");
                    continue;
                }

                Enquiry enquiry = new Enquiry(enquiryId, question, enquirerName, linkedProject);
                enquiry.setAnswer(answer);
                enquiry.setResolved(isResolved);
                addEnquiry(enquiry);
            }

            System.out.println("Enquiries loaded from CSV.");

        } catch (IOException e) {
            System.out.println("Error loading enquiries: " + e.getMessage());
        }
    }

    public static void saveEnquiriesToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            writer.write("EnquiryId,Question,Answer,IsResolved,EnquirerName,ProjectId");
            writer.newLine();

            for (Enquiry e : allEnquiries) {
                writer.write(String.join(",",
                    e.getEnquiryId(),
                    e.getQuestion().replace(",", " "), 
                    e.getAnswer().replace(",", " "),
                    String.valueOf(e.isResolved()),
                    e.getEnquirerName(),
                    e.getLinkedProject().getProjectId()
                ));
                writer.newLine();
            }

            System.out.println("Enquiries saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving enquiries: " + e.getMessage());
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
