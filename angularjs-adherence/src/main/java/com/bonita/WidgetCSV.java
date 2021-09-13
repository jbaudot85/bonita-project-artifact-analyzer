package com.bonita;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.stream.*;

// This class aims at building and exporting CSV files
public class WidgetCSV
{
    List<String[]> dataLines;
    public WidgetCSV()
    {
        dataLines = new ArrayList<>();
        addDataHead();
    }

    private void addDataHead()
    {
        String[] head = new String[4];
        head[0] = "Project";
        head[1] = "#Custom Widgets";
        head[2] = "#AJS Asset";
        head[3] = "#Custom Widgets using Assets";
        dataLines.add(head);
    }

    public void AddDataLine(String projectName, int customWidgetsCount, int ajsAssetCount, int customWidgetsUsingAssetsCount)
    {
        String[] line = new String[4];
        line[0] = projectName;
        line[1] = String.valueOf(customWidgetsCount);
        line[2] = String.valueOf(ajsAssetCount);
        line[3] = String.valueOf(customWidgetsUsingAssetsCount);
        dataLines.add(line);
    }

    private String convertToCSV(String[] data)
    {
        return Stream.of(data)
          .collect(Collectors.joining(","));
    }
    
    public void exportTo(String filepath)
    {
        File csvOutputFile = new File(filepath);
        try (PrintWriter pw = new PrintWriter(csvOutputFile))
        {
            dataLines.stream()
          .map(this::convertToCSV)
          .forEach(pw::println);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Unable to write CSV file: "+filepath);
        }
    }
    
}
