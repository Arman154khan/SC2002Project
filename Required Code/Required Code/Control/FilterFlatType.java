package Control;
import entity.Project;
import entity.enums.FlatType;

import java.util.HashMap;
import java.util.List;

/**
 * Filter by flat type 
 */
public class FilterFlatType extends FilterType
{

    private FlatType type;

    public FilterFlatType(FlatType type)
    {
        this.type = type;
    }

    @Override
    public void filter(List<Project> allProjects)
    {
        System.out.println(" Projects with flat type: " + type);
        for(Project p: allProjects)
        {
            for(FlatType i : p.getFlatUnits().keySet())
            {
                if(i.equals(type) && p.getFlatUnitCount(i) > 0)
                {
                    System.out.println("-" + p.getProjectName());
                    break;
                }
            }
        }
    }
}
