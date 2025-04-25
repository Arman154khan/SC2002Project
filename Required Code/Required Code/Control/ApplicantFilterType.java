package Control;

import entity.Applicant;
import java.util.List;

/**
 * Filters by applicant 
 */

public abstract class ApplicantFilterType {
    public abstract void filter(List<Applicant> allApplicants);
}
