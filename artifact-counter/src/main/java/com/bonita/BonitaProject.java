package com.bonita;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class BonitaProject 
{
    public static BonitaProcessPropertyList seekProcessProperties(String projectRootPath)
    {
        ProcessGrep grep = new ProcessGrep();
        List result = grep.recursive_exec(projectRootPath + "\\diagrams\\");

        ArrayList<BonitaProcessPropertyList> list = new ArrayList<BonitaProcessPropertyList>(result);
        BonitaProcessPropertyList aggregate = new BonitaProcessPropertyList();
        for (int i=0;i<list.size();i++)
        {
            aggregate.aggregate(list.get(i));
        }

        return aggregate;
    }

    public static ArrayList<String> seekFragments(String projectRootPath)
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

    public static ArrayList<String> seekCustomWidgets(String projectRootPath)
    {
        WidgetGrep grep = new WidgetGrep();
        List result = grep.recursive_exec(projectRootPath + "\\web_widgets\\");

        ArrayList<File> list = new ArrayList<File>(result);
        ArrayList<String> cwidgets = new ArrayList<String>();
        for (int i=0;i<list.size();i++)
        {
            String cwname = list.get(i).getName();
            cwname = cwname.substring(0, cwname.length() - 5); // remove ".json"
            cwidgets.add(cwname);
            System.out.println("Found custom widget: "+cwname);
        }

        return cwidgets;
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

    public static void countWidgetOccurence_ThroughFragments(String projectRootPath, ArrayList<String> widgetNames,ArrayList<String> fragmentNames, int[] wcount)
    {
        int[] fcount = new int[fragmentNames.size()];

        countWidgetOccurence_Direct(projectRootPath,fragmentNames,fcount);

        for (int w=0;w<widgetNames.size();w++)
        {
            WidgetGrep g = new WidgetGrep(widgetNames.get(w));
            for (int f=0;f<fragmentNames.size();f++)
            {
                ArrayList<File> list = new ArrayList<File>(g.recursive_exec(projectRootPath + "web_fragments\\" + fragmentNames.get(f)+"\\"));
                wcount[w] += list.size() * fcount[f];
            }
        }
    }
}
