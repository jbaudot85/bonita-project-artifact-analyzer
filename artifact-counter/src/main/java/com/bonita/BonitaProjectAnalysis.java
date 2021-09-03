package com.bonita;

import java.util.ArrayList;
import java.util.Arrays;

public class BonitaProjectAnalysis
{
    private static void intarray_aggregate(int[] a, int[] b, int[] c)
    {
        for (int i=0;i<a.length;i++)
        {
            c[i]= a[i] + b[i];
        }
    }

    public static void exec(String projectsPath, String projectName, ProjectCSV projectCsv, WidgetCSV widgetCsv)
    {
        String projectPath = projectsPath + projectName + "\\";

        // BDM
        BonitaBDMItemCounter bdmItems = BonitaProject.seekBDMProperties(projectPath);

        // Diagrams
        BonitaDiagramItemCounter diagramItems = BonitaProject.seekDiagramProperties(projectPath);

        // Fragments
        ArrayList<String> fragmentNames = BonitaProject.seekFragments(projectPath);

        // Pages & forms
        int pageCount = BonitaProject.seekPagesCount(projectPath);

        // Widgets
        ArrayList<String> widgetNames = UIDWidgetList.providedWidgetNames();
        int[] widget_usage_count_direct = new int[widgetNames.size()];
        int[] widget_usage_count_fragments = new int[widgetNames.size()];
        int[] widget_usage_count = new int[widgetNames.size()];
        BonitaProject.countWidgetOccurence_Direct(projectPath,widgetNames,widget_usage_count_direct);
        BonitaProject.countWidgetOccurence_ThroughFragments(projectPath,widgetNames,fragmentNames,widget_usage_count_fragments);
        intarray_aggregate(widget_usage_count_direct,widget_usage_count_fragments,widget_usage_count);

        // Custom Widgets
        ArrayList<String> customWidgetNames = BonitaProject.seekCustomWidgets(projectPath);
        int[] cwidget_usage_count_direct = new int[customWidgetNames.size()];
        int[] cwidget_usage_count_fragments = new int[customWidgetNames.size()];
        int[] cwidget_usage_count = new int[customWidgetNames.size()];
        BonitaProject.countWidgetOccurence_Direct(projectPath,customWidgetNames,cwidget_usage_count_direct);
        BonitaProject.countWidgetOccurence_ThroughFragments(projectPath,customWidgetNames,fragmentNames,cwidget_usage_count_fragments);
        intarray_aggregate(cwidget_usage_count_direct,cwidget_usage_count_fragments,cwidget_usage_count);

        // Statistics & report
        int cwidget_count = customWidgetNames.size();
        int all_cwidget_usage_count = Arrays.stream(cwidget_usage_count).sum();
        projectCsv.AddDataLine(projectName,
            bdmItems.BusinessObjects, bdmItems.AnyTypeOfFields(), 
            diagramItems.Diagrams, diagramItems.Pools, diagramItems.Lanes, diagramItems.AnyTypeOfTasks(), diagramItems.ConnectorCalls, diagramItems.GroovyExpressions,
            pageCount, cwidget_count, fragmentNames.size());
        widgetCsv.AddDataLine(projectName,cwidget_count, all_cwidget_usage_count, widget_usage_count);
    }
}
