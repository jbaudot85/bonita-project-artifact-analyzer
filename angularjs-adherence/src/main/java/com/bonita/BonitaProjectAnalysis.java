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

    public static void exec(ArrayList<AJSAsset> assets, String projectsPath, String projectName, WidgetCSV widgetCsv, AssetCSV assetCsv)
    {
        String projectPath = projectsPath + projectName + "\\";

        // Custom Widgets
        ArrayList<String> customWidgetNames = BonitaProject.seekCustomWidgets(projectPath);

        // Assets
        BonitaProject.seekAJSAssets(assets, projectPath, projectName, customWidgetNames);
        // Note: the following line is valid because assets already contains the project analysis
        ArrayList<String> customWidgetNamesUsingAssets = BonitaProject.ListProjectCustomWidgetsUsingAJSAssets(assets,projectName);
        widgetCsv.AddDataLine(projectName,customWidgetNames.size(),assets.size(),customWidgetNamesUsingAssets.size());
        
    }
}
