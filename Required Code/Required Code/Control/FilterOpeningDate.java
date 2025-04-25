package Control;

import entity.Project;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Filter projects by opening date
 */
public class FilterOpeningDate extends FilterType{

    private LocalDate targetDate;

    public FilterOpeningDate(LocalDate date)
    {
        this.targetDate = date;
    }

    @Override
    public void filter(List<Project> allProjects)
    {
        System.out.println("Projects opening on: " + targetDate);
        for(Project p: allProjects)
        {
            if (p.getApplicationOpeningDate().equals(targetDate))
            {
                System.out.println("-" + p.getProjectName());
            }
        }
    }
}
