package Control;

import entity.*;
import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * Handles all data for the projects csv.
 */
public final class ProjectRepository {

    private static final String FILENAME = "csv/ProjectList.csv";
    private static List<Project> allProjects = new ArrayList<>();
    private ProjectRepository() {}

    public static void addProject(Project project) {
        allProjects.add(project);
    }

    public static List<Project> getAllProjects() {
        return new ArrayList<>(allProjects);
    }

    public static void clearAllProjects() {
        allProjects.clear();
    }

    public static void removeProject(Project project) {
        allProjects.remove(project);
    }

    public static void loadProjectsFromCSV() {
        clearAllProjects();
        List<HDBManager> allManagers = ManagerRepository.getAllManagers();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line = reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] fields = line.split(",");

                String projectId = fields[0].trim();
                String projectName = fields[1].trim();
                String neighborhood = fields[2].trim();
                int twoRoomUnits = Integer.parseInt(fields[3].trim());
                int threeRoomUnits = Integer.parseInt(fields[4].trim());
                LocalDate openDate = LocalDate.parse(fields[5].trim());
                LocalDate closeDate = LocalDate.parse(fields[6].trim());
                String managerName = fields[7].trim();
                String managerNric = fields[8].trim();
                int visibility = Integer.parseInt(fields[9].trim());

                for(HDBManager m : ManagerRepository.getAllManagers())
                {
                    // System.out.print(m.getNric() + " " + managerNric + "\n");
                    if(managerNric.equalsIgnoreCase(m.getNric()))
                    {
                        Project project = new Project(projectId, projectName, neighborhood, twoRoomUnits, threeRoomUnits, openDate, closeDate, m);
                        if (visibility == 1)
                        {
                            project.setVisibility(true);
                        }
                        else
                        {
                            project.setVisibility(false);
                        }
                        addProject(project);    
                    }
                }
            }

            autoUpdateAllProjectsVisibility();
            System.out.println("Projects loaded and visibility updated.");
        } catch (IOException e) {
            System.out.println("Error loading projects from CSV: " + e.getMessage());
        }
    }

    public static void saveProjectsToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            writer.write("ProjectId,ProjectName,Neighborhood,TwoRoomUnits,ThreeRoomUnits,OpeningDate,ClosingDate,ManagerName, ManagerNric, Visibility");
            writer.newLine();
            String vis;

            for (Project p : allProjects) {
                if(p.isVisible() == true)
                {
                    vis = "1";
                }
                else
                {
                    vis = "0";
                }
                writer.write(String.join(",",
                    p.getProjectId(),
                    p.getProjectName(),
                    p.getNeighbourhood(),
                    String.valueOf(p.getFlatUnitCount(entity.enums.FlatType.TWO_ROOM)),
                    String.valueOf(p.getFlatUnitCount(entity.enums.FlatType.THREE_ROOM)),
                    p.getApplicationOpeningDate().toString(),
                    p.getApplicationClosingDate().toString(),
                    p.getManagerInCharge().getName(),
                    p.getManagerInCharge().getNric(),
                    vis
                ));
                writer.newLine();
            }

            System.out.println("Projects saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving projects to CSV: " + e.getMessage());
        }
    }

    public static void autoUpdateAllProjectsVisibility() {
        for (Project project : allProjects) {
            project.autoUpdateVisibilityStatus();
        }
    }
}
