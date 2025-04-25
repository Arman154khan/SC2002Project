package entity;

import java.time.LocalDate;
import java.util.*;
import entity.enums.FlatType;

/**
 * Represents the project.
 */

public class Project {
    private String projectId;
    private String projectName;
    private String neighbourhood;
    private LocalDate applicationOpeningDate;
    private LocalDate applicationClosingDate;
    private Map<FlatType, Integer> flatUnits;
    private HDBManager managerInCharge;
    private int availableOfficerSlots;
    private final int maxOfficerSlots = 10;
    private boolean isVisible;

    private List<Officer> officerList = new ArrayList<>();
    private List<Officer> officerRegistrations = new ArrayList<>();
    private List<Enquiry> enquiries = new ArrayList<>();
    private List<Applicant> approvedApplicants = new ArrayList<>();
    private List<Applicant> pendingApplicants = new ArrayList<>();

    public Project(String projectId, String projectName, String neighborhood,
                   int twoRoomUnits, int threeRoomUnits,
                   LocalDate opening, LocalDate closing,
                   HDBManager managerInCharge) {

        this.projectId = projectId;
        this.projectName = projectName;
        this.neighbourhood = neighborhood;
        this.flatUnits = new HashMap<>();
        this.flatUnits.put(FlatType.TWO_ROOM, twoRoomUnits);
        this.flatUnits.put(FlatType.THREE_ROOM, threeRoomUnits);
        this.applicationOpeningDate = opening;
        this.applicationClosingDate = closing;
        this.isVisible = false;
        this.availableOfficerSlots = maxOfficerSlots;
        this.managerInCharge = managerInCharge;
    }


    public void updateFlatAvailability(FlatType flatType, int increment) {
        flatUnits.put(flatType, flatUnits.getOrDefault(flatType, 0) + increment);
    }

    public int getFlatUnitCount(FlatType type) {
        return flatUnits.getOrDefault(type, 0);
    }

    public void setFlatUnits(FlatType flatType, int count) {
        flatUnits.put(flatType, count);
    }

    public Map<FlatType, Integer> getFlatUnits() {
        return flatUnits;
    }

   

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String id) {
        this.projectId = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String name) {
        this.projectName = name;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public LocalDate getApplicationOpeningDate() {
        return applicationOpeningDate;
    }

    public void setApplicationOpeningDate(LocalDate openingDate) {
        this.applicationOpeningDate = openingDate;
    }

    public LocalDate getApplicationClosingDate() {
        return applicationClosingDate;
    }

    public void setApplicationClosingDate(LocalDate closingDate) {
        this.applicationClosingDate = closingDate;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisibility(boolean visibility) {
        this.isVisible = visibility;
    }

    public boolean isInApplicationPeriod() {
        LocalDate today = LocalDate.now();
        return !today.isBefore(applicationOpeningDate) && !today.isAfter(applicationClosingDate);
    }

    public boolean isFlatAvailable(FlatType flatType) {
        return flatUnits.getOrDefault(flatType, 0) > 0;
    }

    public HDBManager getManagerInCharge() {
        return managerInCharge;
    }

    public void setManagerInCharge(HDBManager managerInCharge) {
        this.managerInCharge = managerInCharge;
    }

    public void autoUpdateVisibilityStatus() {
        LocalDate today = LocalDate.now();
        if (isVisible && today.isAfter(applicationClosingDate)) {
            setVisibility(false);
            if (managerInCharge.getCurrentProjectHandling() == this) {
                managerInCharge.setCurrentProjectHandling(null);
            }
            System.out.println("Project '" + projectName + "' auto-hidden (past application period).");
        }
    }

    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    public void addEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
    }

    public void removeEnquiry(Enquiry enquiry) {
        enquiries.remove(enquiry);
    }

    public List<Applicant> getApprovedApplicants() {
        return approvedApplicants;
    }

    public List<Applicant> getPendingApplicants() {
        return pendingApplicants;
    }

    public List<Applicant> getAllApplicants() {
        List<Applicant> all = new ArrayList<>();
        all.addAll(approvedApplicants);
        all.addAll(pendingApplicants);
        return all;
    }

    public void addPendingApplicant(Applicant applicant) {
        pendingApplicants.add(applicant);
    }

    public void moveToApprovedApplicants(Applicant applicant) {
        if (pendingApplicants.remove(applicant)) {
            approvedApplicants.add(applicant);
        }
    }

    public void removePendingApplicant(Applicant applicant) {
        pendingApplicants.remove(applicant);
    }

    public boolean isEligible(Applicant applicant, FlatType flatType) {
        if (!isVisible) return false;

        int age = applicant.age;
        String maritalStatus = applicant.maritalStatus;

        return switch (flatType) {
            case TWO_ROOM -> (maritalStatus.equalsIgnoreCase("Married") && age >= 21) ||
                             (maritalStatus.equalsIgnoreCase("Single") && age >= 35);
            case THREE_ROOM -> maritalStatus.equalsIgnoreCase("Married") && age >= 21;
            default -> false;
        };
    }

    public List<Officer> getOfficers() {
        return officerList;
    }

    public List<Officer> getOfficerRegistrations() {
        return officerRegistrations;
    }

    public void addOfficerRegistration(Officer officer) {
        officerRegistrations.add(officer);
    }

    public void removeOfficerRegistration(Officer officer) {
        officerRegistrations.remove(officer);
    }

    public boolean assignOfficerSlot(Officer officer) {
        if (availableOfficerSlots > 0) {
            officerList.add(officer);
            availableOfficerSlots--;
            return true;
        }
        return false;
    }

    public void releaseOfficerSlot(Officer officer) {
        if (officerList.remove(officer)) {
            availableOfficerSlots++;
        }
    }

    public int getAvailableOfficerSlots() {
        return availableOfficerSlots;
    }

    public int getMaxOfficerSlot() {
        return maxOfficerSlots;
    }

}

