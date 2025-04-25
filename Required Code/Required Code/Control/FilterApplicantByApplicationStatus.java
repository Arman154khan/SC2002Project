package Control;

import entity.Applicant;
import java.util.List;

/**
 * Filter for applicant on the basis of application status
 */

public class FilterApplicantByApplicationStatus extends ApplicantFilterType {

    private String applicationStatus;

    public FilterApplicantByApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    @Override
    public void filter(List<Applicant> allApplicants) {
        System.out.println("Applicants with application status: " + applicationStatus);
        for (Applicant a : allApplicants) {
            if (a.getApplicationStatus().equals(applicationStatus)) {
                System.out.printf("- %s (%s)%n", a.getName(), a.getNric());
            }
        }
    }
}
