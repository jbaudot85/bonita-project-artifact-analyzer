package com.bonita;

import java.util.ArrayList;
import java.util.Arrays;

public class App 
{   
    public static void main( String[] args )
    {
        ArrayList<String> projectNames = new ArrayList<>();
        projectNames.add("app-HRManagement");
        projectNames.add("app-InvoiceManagement");
        projectNames.add("app-Ticketing");
        projectNames.add("credit-card-dispute-resolution");
        projectNames.add("expense-report-example");
        projectNames.add("procurement-example");
        projectNames.add("showroom-cloud");
        projectNames.add("tahiti");
        projectNames.add("serenity");

        String projectsPath = "C:\\code\\bonita-from-the-field\\";
        WidgetCSV widgetCsv = new WidgetCSV(ArtifactCounter.widgetNames());
        ProjectCSV projectCsv = new ProjectCSV();

        for (int p=0;p<projectNames.size();p++)
        {
            ArtifactCounter.projectAnalysis(projectsPath, projectNames.get(p), projectCsv, widgetCsv);     
        }

        widgetCsv.exportTo("widgets.csv");
        projectCsv.exportTo("projects.csv");
    }
}
