package Control;

import java.time.LocalDate;
import java.util.List;

import entity.*;
import entity.enums.FlatType;

/**
 * Projects are managed here. 
 */

public class ProjectManagementService {

    private HDBManager manager;

    public ProjectManagementService(HDBManager manager) {
        this.manager = manager;
    }

    public boolean createProject(String id, String name, String neighborhood, int twoRoomUnits, int threeRoomUnits, LocalDate openingDate, LocalDate closingDate) {

        List<Project> allProjects = ProjectRepository.getAllProjects();

        for (Project p : allProjects) {
            if (p.getProjectName().equalsIgnoreCase(name)) {
                System.out.println("Project name already in use.");
                return false;
            }
        }

        Project project = new Project(id, name, neighborhood, twoRoomUnits, threeRoomUnits, openingDate, closingDate, manager);
        manager.getCreatedProjects().add(project.getProjectId());
        ProjectRepository.addProject(project);
        System.out.println("Project created successfully.");

        return true;
    }

    public boolean editProject(Project project, String newName, String newNeighborhood, int new2RoomUnits, int new3RoomUnits, LocalDate newOpenDate, LocalDate newCloseDate) {
        if (!manager.getCreatedProjects().contains(project.getProjectId())) {
            System.out.println("You can only edit your own projects.");
            return false;
        }

        if (project.isVisible() && !project.isInApplicationPeriod()) {
            System.out.println("Cannot edit application dates: project is visible and must remain in current window.");
            return false;
        }

        project.setProjectName(newName);
        project.setNeighbourhood(newNeighborhood);
        project.setFlatUnits(FlatType.TWO_ROOM, new2RoomUnits);
        project.setFlatUnits(FlatType.THREE_ROOM, new3RoomUnits);
        project.setApplicationOpeningDate(newOpenDate);
        project.setApplicationClosingDate(newCloseDate);

        System.out.println("Project edited successfully.");
        return true;
    }

    public boolean deleteProject(Project project) {
        if (!manager.getCreatedProjects().contains(project.getProjectId())) {
            System.out.println("You can only delete your own projects.");
            return false;
        }
    
        manager.getCreatedProjects().remove(project.getProjectId());
        ProjectRepository.removeProject(project);
    
        if (project.equals(manager.getCurrentProjectHandling())) {
            manager.setCurrentProjectHandling(null);
        }
    
        System.out.println("Project deleted.");
        return true;
    }

    public boolean toggleProjectVisibility(Project project) {
        if (!manager.getCreatedProjects().contains(project.getProjectId())) {
            System.out.println("You can only toggle visibility for your projects.");
            return false;
        }

        boolean newVisibility = !project.isVisible();

        if (newVisibility) {
            if (!project.isInApplicationPeriod()) {
                System.out.println("Project application period is not active yet.");
                return false;
            }

            if (manager.getCurrentProjectHandling() != null) {
                System.out.println("You are already handling another active project.");
                return false;
            }

            manager.setCurrentProjectHandling(project);
        } else {
            if (project.equals(manager.getCurrentProjectHandling())) {
                manager.setCurrentProjectHandling(null);
            }
        }

        project.setVisibility(newVisibility);
        System.out.println("Project visibility is now set to: " + newVisibility);
        return true;
    }
}
