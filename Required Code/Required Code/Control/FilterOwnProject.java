package Control;

import entity.Project;
import entity.Officer;
import java.util.List;

/**
 * Filter projects that one officer is assigned to 
 */

public class FilterOwnProject extends FilterType
{

    private Officer officer;

    public FilterOwnProject(Officer officer)
    {
        this.officer = officer;
    }

    @Override
    public void filter(List<Project> allProjects)
    {
        String assignedProjectId = officer.getAssignedProjectId();
        for(Project p : allProjects)
        {
            if(p.getProjectId().equals(assignedProjectId))
            {
                System.out.println("-" + p.getProjectName());
                break;
            }
        }
    }
    
}
