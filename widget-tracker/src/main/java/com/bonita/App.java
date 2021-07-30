package com.bonita;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{
    public static ArrayList<String> seekProjectFragments(String projectRootPath)
    {
        Grep grep = new Grep();
        List result = grep.recursive(projectRootPath + "\\web_fragments\\");

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
            Grep g = new Grep(widgetNames.get(w));
            ArrayList<File> list = new ArrayList<File>(g.recursive(projectRootPath + "web_page\\"));
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
            Grep g = new Grep(widgetNames.get(w));
            for (int f=0;f<fragments.size();f++)
            {
                ArrayList<File> list = new ArrayList<File>(g.recursive(projectRootPath + "web_fragments\\" + fragments.get(f)+"\\"));
                wcount[w] += list.size() * fcount[f];
            }
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

        int[] wcount_direct = new int[widgetNames.size()];
        int[] wcount_fragments = new int[widgetNames.size()];

        String projectPath = "C:\\code\\bonita-from-the-field\\tahiti\\";

        countWidgetOccurence_Direct(projectPath,widgetNames,wcount_direct);
        countWidgetOccurence_ThroughFragments(projectPath,widgetNames,wcount_fragments);

        CSV csv = new CSV();
        csv.exportTo("direct.csv",widgetNames,wcount_direct);
        csv.exportTo("fragments.csv",widgetNames,wcount_fragments);
    }
}
