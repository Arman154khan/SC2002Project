// package Control;

// import entity.*;
// import entity.enums.FlatType;
// import java.util.ArrayList;
// import java.util.List;

// /**
//  * Provides functionality to applicants. 
//  */

// public class ApplicantService {

//     public boolean applyForProject(Project project, Applicant applicant) {
//         if (project.getPendingApplicants().contains(applicant) || 
//             project.getApprovedApplicants().contains(applicant)) {
//             System.out.println("You have already applied or been approved.");
//             return false;
//         }
//         project.addPendingApplicant(applicant);
//         System.out.println("Application submitted successfully.");
//         return true;
//     }

//     public boolean withdrawFromProject(Project project, Applicant applicant) {
//         if (project.getPendingApplicants().remove(applicant)) {
//             System.out.println("Application withdrawn.");
//             return true;
//         } else {
//             System.out.println("No pending application found.");
//             return false;
//         }
//     }

//     public boolean isEligible(Project project, Applicant applicant, FlatType flatType) {
//         return project.isEligible(applicant, flatType);
//     }

//     public List<Project> getEligibleProjects(Applicant applicant, List<Project> allProjects, FlatType flatType) {
//         List<Project> result = new ArrayList<>();
//         for (Project p : allProjects) {
//             if (p.isVisible() && p.isEligible(applicant, flatType)) {
//                 result.add(p);
//             }
//         }
//         return result;
//     }

//     public List<Project> getAppliedProjects(Applicant applicant, List<Project> allProjects) {
//         List<Project> result = new ArrayList<>();
//         for (Project p : allProjects) {
//             if (p.getPendingApplicants().contains(applicant) || p.getApprovedApplicants().contains(applicant)) {
//                 result.add(p);
//             }
//         }
//         return result;
//     }
// }
