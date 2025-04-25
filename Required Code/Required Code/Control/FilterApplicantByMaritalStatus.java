package Control;

import entity.Applicant;
import java.util.List;
/**
 * Filter for applicant on the basis of marital status.
 */
public class FilterApplicantByMaritalStatus extends ApplicantFilterType {

    private String maritalStatus;

    public FilterApplicantByMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    @Override
    public void filter(List<Applicant> allApplicants) {
        System.out.println("Applicants with marital status: " + maritalStatus);
        for (Applicant a : allApplicants) {
            if (a.getMaritalStatus().equalsIgnoreCase(maritalStatus)) {
                System.out.printf("- %s (%s)%n", a.getName(), a.getNric());
            }
        }
    }
}
