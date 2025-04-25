package Control;

import entity.Applicant;
import entity.enums.FlatType;

import java.util.List;

/**
 * Filter for applicant on the basis of flat type
 */
public class FilterApplicantByFlatType extends ApplicantFilterType {

    private FlatType flatType;

    public FilterApplicantByFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    public void filter(List<Applicant> allApplicants) {
        System.out.println("Applicants applying for flat type: " + flatType);
        for (Applicant a : allApplicants) {
            if (a.getFlatTypeApplied().equals(flatType)) {
                System.out.printf("- %s (%s)%n", a.getName(), a.getNric());
            }
        }
    }
}
