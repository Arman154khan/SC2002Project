package Control;
import entity.Project;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * General filter type 
 */
public class FilterType 
{
    protected List <Project> allProjects;
    
    public void filter(List<Project> allProjects)
    {
        this.allProjects = allProjects;

        Collections.sort(allProjects, Comparator.comparing(Project::getProjectName));

        System.out.println("Projects filtered alphabetically: ");

        for(Project p : allProjects)
        {
            System.out.println("-" + p.getProjectName());
        }
    }
}