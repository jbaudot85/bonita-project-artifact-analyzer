package com.bonita;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class App 
{
    public static ArrayList<String> seekProjectFragments(String projectRootPath)
    {
        WidgetGrep grep = new WidgetGrep();
        List result = grep.recursive_exec(projectRootPath + "\\web_fragments\\");

        ArrayList<File> list = new ArrayList<File>(result);
        ArrayList<String> fragments = new ArrayList<String>();
        for (int i=0;i<list.size();i++)
        {
            String fname = list.get(i).getName();
            fname = fname.substring(0, fname.length() - 5); // remove ".json"
            fragments.add(fname);
            System.out.println("Found fragment: "+fname);
        }

        return fragments;
    }

    public static void countWidgetOccurence_Direct(String projectRootPath, ArrayList<String> widgetNames, int[] wcount)
    {
        for (int w=0;w<widgetNames.size();w++)
        {
            WidgetGrep g = new WidgetGrep(widgetNames.get(w));
            ArrayList<File> list = new ArrayList<File>(g.recursive_exec(projectRootPath + "web_page\\"));
            wcount[w] += list.size();
        }
    }

    public static void countWidgetOccurence_ThroughFragments(String projectRootPath, ArrayList<String> widgetNames, int[] wcount)
    {
        ArrayList<String> fragments = seekProjectFragments(projectRootPath);
        int[] fcount = new int[fragments.size()];

        countWidgetOccurence_Direct(projectRootPath,fragments,fcount);

        for (int w=0;w<widgetNames.size();w++)
        {
            WidgetGrep g = new WidgetGrep(widgetNames.get(w));
            for (int f=0;f<fragments.size();f++)
            {
                ArrayList<File> list = new ArrayList<File>(g.recursive_exec(projectRootPath + "web_fragments\\" + fragments.get(f)+"\\"));
                wcount[w] += list.size() * fcount[f];
            }
        }
    }

    public static void aggregate(int[] a, int[] b, int[] c)
    {
        for (int i=0;i<a.length;i++)
        {
            c[i]= a[i] + b[i];
        }
    }

    
    public static void main( String[] args )
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

        ArrayList<String> projectsNames = new ArrayList<>();
        projectsNames.add("app-HRManagement");
        projectsNames.add("app-InvoiceManagement");
        projectsNames.add("app-Ticketing");
        projectsNames.add("credit-card-dispute-resolution");
        projectsNames.add("expense-report-example");
        projectsNames.add("procurement-example");
        projectsNames.add("showroom-cloud");
        projectsNames.add("tahiti");

        String projectsPath = "C:\\code\\bonita-from-the-field\\";
        CSV csv = new CSV(widgetNames);

        for (int p=0;p<projectsNames.size();p++)
        {
            int[] wcount_direct = new int[widgetNames.size()];
            int[] wcount_fragments = new int[widgetNames.size()];
            int[] wcount = new int[widgetNames.size()];

            String projectPath = projectsPath + projectsNames.get(p) + "\\";

            countWidgetOccurence_Direct(projectPath,widgetNames,wcount_direct);
            countWidgetOccurence_ThroughFragments(projectPath,widgetNames,wcount_fragments);
            aggregate(wcount_direct,wcount_fragments,wcount);

            csv.AddDataLine(projectsNames.get(p), wcount);
        }

        csv.exportTo("widgets.csv");
    }
}
