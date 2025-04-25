package Control;

import entity.*;
import entity.enums.RegistrationStatus;

import java.util.List;

/**
 * Includes the officer functionality
 */

public class HDBOfficerManagementService {
    private Project currentProjectHandling;

    public HDBOfficerManagementService(Project currentProjectHandling) {
        this.currentProjectHandling = currentProjectHandling;
    }

    public boolean approveOfficerRegistration(Registration registration) {
        if (registration.getStatus() != RegistrationStatus.PENDING) {
            System.out.println("Cannot approve: Registration already processed.");
            return false;
        }

        if (currentProjectHandling.getOfficers().size() >= currentProjectHandling.getMaxOfficerSlot()) {
            System.out.println("Cannot approve: Max officer slots reached.");
            return false;
        }

        currentProjectHandling.assignOfficerSlot(registration.getOfficer());
        currentProjectHandling.removeOfficerRegistration(registration.getOfficer());
        registration.updateRegistrationStatus(RegistrationStatus.APPROVED);
        registration.getOfficer().assignProject(registration.getProject().getProjectId());
        System.out.println("Officer registration approved.");
        return true;
    }

    public boolean rejectOfficerRegistration(Registration registration) {
        if (registration.getStatus() != RegistrationStatus.PENDING) {
            System.out.println("Cannot reject: Registration already processed.");
            return false;
        }

        registration.updateRegistrationStatus(RegistrationStatus.REJECTED);
        currentProjectHandling.removeOfficerRegistration(registration.getOfficer());
        System.out.println("Officer registration rejected.");
        return true;
    }

    public void viewPendingAndApprovedOfficers(Project project) {
        System.out.println("=== PENDING HDB OFFICER REGISTRATIONS ===");
        for (Officer o : project.getOfficerRegistrations()) {
            if (o.isRegistrationPending() == true) {
                System.out.println("• " + o.getName() + " (Pending)");
            }
        }

        System.out.println("\n=== APPROVED HDB OFFICERS ===");
        for (Officer officer : project.getOfficers()) {
            System.out.println("• " + officer.getName());
        }
    }
}