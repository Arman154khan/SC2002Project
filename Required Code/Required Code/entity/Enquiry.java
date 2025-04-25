package entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.spi.LocaleNameProvider;

/**
 * Represents the enquiry entity
 */

public class Enquiry {
    private String enquiryId;  
    private String question;
    private String answer;
    private boolean isResolved;
    private String enquirerName;
    private Project linkedProject; 

    public Enquiry(String enquiryId, String question, String enquirerName, Project linkedProject) {
        this.enquiryId = enquiryId;
        this.question = question;
        this.enquirerName = enquirerName;
        this.isResolved = false;
        this.answer = "";
        this.linkedProject = linkedProject;
    }

    public String getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(String enquiryId) {
        this.enquiryId = enquiryId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isResolved() {
        return isResolved;
    }

    public void setResolved(boolean resolved) {
        isResolved = resolved;
    }

    public String getEnquirerName() {
        return enquirerName;
    }

    public void setEnquirerName(String enquirerName) {
        this.enquirerName = enquirerName;
    }

    public Project getLinkedProject() {
        return linkedProject;
    }

    public void setLinkedProject(Project linkedProject) {
        this.linkedProject = linkedProject;
    }

    public static Enquiry findEnquiryById(List<Enquiry> enquiries, String id) {
        for (Enquiry e : enquiries) {
            if (e.getEnquiryId().equalsIgnoreCase(id)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Enquiry ID: " + enquiryId + "\n" +
               "Question: " + question + "\n" +
               "Answer: " + (isResolved ? answer : "[Pending]") + "\n" +
               "Enquirer: " + enquirerName + "\n" +
               "Resolved: " + isResolved;
    }

    public Object getProjectId() {
        throw new UnsupportedOperationException("Unimplemented method 'getProjectId'");
    }
}

