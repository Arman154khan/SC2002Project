package Control;

import entity.Project;
import java.util.List;

/**
 * Filter on the basis of location
 */

public class FilterLocation extends FilterType
{
    private String location;
    
    public FilterLocation(String location)
    {
        this.location = location;
    }

    @Override
    public void filter(List<Project> allProjects)
    {
        System.out.println("Projects in: " + location);

        for(Project p : allProjects)
        {
            if(p.getNeighbourhood().equalsIgnoreCase(location))
            {
                System.out.println("-" + p.getProjectName());
            }
        }
    }
}
