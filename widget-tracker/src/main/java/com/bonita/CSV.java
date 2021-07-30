package com.bonita;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.stream.*;

// This class aims at building and exporting CSV files
public class CSV
{
    List<String[]> dataLines;
    public CSV(ArrayList<String> widgetNames)
    {
        dataLines = new ArrayList<>();
        addDataHead(widgetNames);
    }

    private void addDataHead(ArrayList<String> widgetNames)
    {
        String[] head = new String[widgetNames.size() + 1];
        head[0] = "Project";
        for (int i=0;i<widgetNames.size();i++) head[i+1] = widgetNames.get(i);
        dataLines.add(head);
    }

    public void AddDataLine(String projectName, int[] count)
    {
        String[] line = new String[count.length + 1];
        line[0] = projectName;
        for (int i=0;i<count.length;i++) line[i+1] = String.valueOf(count[i]);
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
