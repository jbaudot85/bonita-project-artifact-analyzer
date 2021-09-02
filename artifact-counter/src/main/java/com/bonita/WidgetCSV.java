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
        addDataHead(WidgetList.widgetNames());
    }

    private void addDataHead(ArrayList<String> widgetNames)
    {
        String[] head = new String[widgetNames.size() + 3];
        head[0] = "Project";
        head[1] = "CW count";
        head[2] = "All CWs usage";
        for (int i=0;i<widgetNames.size();i++) head[i+3] = widgetNames.get(i);
        dataLines.add(head);
    }

    public void AddDataLine(String projectName, int cwCount, int allcwUsage, int[] wUsage)
    {
        String[] line = new String[wUsage.length + 3];
        line[0] = projectName;
        line[1] = String.valueOf(cwCount);
        line[2] = String.valueOf(allcwUsage);
        for (int i=0;i<wUsage.length;i++) line[i+3] = String.valueOf(wUsage[i]);
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
