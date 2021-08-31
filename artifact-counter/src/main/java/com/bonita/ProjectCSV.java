package com.bonita;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.stream.*;

// This class aims at building and exporting CSV files
public class ProjectCSV
{
    List<String[]> dataLines;
    public ProjectCSV()
    {
        dataLines = new ArrayList<>();
        addDataHead();
    }

    private void addDataHead()
    {
        String[] head = new String[7];
        head[0] = "Project";
        head[1] = "#Processes";
        head[2] = "#Pools";
        head[3] = "#Lanes";
        head[4] = "#Tasks";
        head[5] = "#CW";
        head[6] = "#Fragments";
        dataLines.add(head);
    }

    public void AddDataLine(String projectName, int processCount, int poolCount, int laneCount, int taskCount, int cwCount, int fragmentCount)
    {
        String[] line = new String[7];
        line[0] = projectName;
        line[1] = String.valueOf(processCount);
        line[2] = String.valueOf(poolCount);
        line[3] = String.valueOf(laneCount);
        line[4] = String.valueOf(taskCount);
        line[5] = String.valueOf(cwCount);
        line[6] = String.valueOf(fragmentCount);
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
