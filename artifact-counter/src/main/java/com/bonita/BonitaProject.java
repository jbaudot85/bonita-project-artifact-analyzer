package com.bonita;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class BonitaProject 
{
    public static BonitaBDMItemCounter seekBDMProperties(String projectRootPath)
    {
        BDMGrep grep = new BDMGrep();
        List result = grep.recursive_exec(projectRootPath + "\\bdm\\");

        ArrayList<BonitaBDMItemCounter> list = new ArrayList<BonitaBDMItemCounter>(result);
        BonitaBDMItemCounter aggregate = new BonitaBDMItemCounter();
        for (int i=0;i<list.size();i++)
        {
            aggregate.aggregate(list.get(i));
        }

        return aggregate;
    }

    public static BonitaDiagramItemCounter seekDiagramProperties(String projectRootPath)
    {
        DiagramGrep grep = new DiagramGrep();
        List result = grep.recursive_exec(projectRootPath + "\\diagrams\\");

        ArrayList<BonitaDiagramItemCounter> list = new ArrayList<BonitaDiagramItemCounter>(result);
        BonitaDiagramItemCounter aggregate = new BonitaDiagramItemCounter();
        for (int i=0;i<list.size();i++)
        {
            aggregate.aggregate(list.get(i));
        }

        return aggregate;
    }

    public static ArrayList<String> seekFragments(String projectRootPath)
    {
        FormGrep grep = new FormGrep();
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

    public static int seekPagesCount(String projectRootPath)
    {
        FormGrep grep = new FormGrep();
        List result = grep.recursive_exec(projectRootPath + "\\web_page\\");
        return result.size();
    }

    public static ArrayList<String> seekCustomWidgets(String projectRootPath)
    {
        FormGrep grep = new FormGrep();
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
            FormGrep g = new FormGrep(widgetNames.get(w));
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
            FormGrep g = new FormGrep(widgetNames.get(w));
            for (int f=0;f<fragmentNames.size();f++)
            {
                ArrayList<File> list = new ArrayList<File>(g.recursive_exec(projectRootPath + "web_fragments\\" + fragmentNames.get(f)+"\\"));
                wcount[w] += list.size() * fcount[f];
            }
        }
    }
}
