package com.bonita;

import java.util.ArrayList;
import java.util.Arrays;

public class ArtifactCounter
{
    private static void intarray_aggregate(int[] a, int[] b, int[] c)
    {
        for (int i=0;i<a.length;i++)
        {
            c[i]= a[i] + b[i];
        }
    }

    public static ArrayList<String> widgetNames()
    {
        ArrayList<String> widgetNames = new ArrayList<>();
        widgetNames.add("pbAutocomplete");
        widgetNames.add("pbButton");
        widgetNames.add("pbChart");
        widgetNames.add("pbCheckbox");
        widgetNames.add("pbChecklist");
        widgetNames.add("pbContainer");
        widgetNames.add("pbDataTable");
        widgetNames.add("pbDatePicker");
        widgetNames.add("pbDateTimePicker");
        widgetNames.add("pbFileViewer");
        widgetNames.add("pbFormContainer");
        widgetNames.add("pbImage");
        widgetNames.add("pbInput");
        widgetNames.add("pbLink");
        widgetNames.add("pbModalContainer");
        widgetNames.add("pbRadioButtons");
        widgetNames.add("pbRichTextarea");
        widgetNames.add("pbSaveButton");
        widgetNames.add("pbSelect");
        widgetNames.add("pbTabContainer");
        widgetNames.add("pbTable");
        widgetNames.add("pbTabsContainer");
        widgetNames.add("pbText");
        widgetNames.add("pbTextarea");
        widgetNames.add("pbTitle");
        widgetNames.add("pbUpload");
        return widgetNames;
    }

    public static void projectAnalysis(String projectsPath, String projectName, ProjectCSV projectCsv, WidgetCSV widgetCsv)
    {
        String projectPath = projectsPath + projectName + "\\";

        // Processes
        BonitaProcessPropertyList processProp = BonitaProject.seekProcessProperties(projectPath);

        // Fragments
        ArrayList<String> fragmentNames = BonitaProject.seekFragments(projectPath);

        // Widgets
        int[] widget_usage_count_direct = new int[widgetNames().size()];
        int[] widget_usage_count_fragments = new int[widgetNames().size()];
        int[] widget_usage_count = new int[widgetNames().size()];
        BonitaProject.countWidgetOccurence_Direct(projectPath,widgetNames(),widget_usage_count_direct);
        BonitaProject.countWidgetOccurence_ThroughFragments(projectPath,widgetNames(),fragmentNames,widget_usage_count_fragments);
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
        projectCsv.AddDataLine(projectName,processProp.ProcessCount, processProp.PoolCount, processProp.LaneCount, processProp.AllTaskCount(), cwidget_count, fragmentNames.size());
        widgetCsv.AddDataLine(projectName,cwidget_count, all_cwidget_usage_count, widget_usage_count);
    }
}
