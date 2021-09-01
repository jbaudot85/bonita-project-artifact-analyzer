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
        String[] head = new String[9];
        head[0] = "Project";
        head[1] = "#Processes";
        head[2] = "#Pools";
        head[3] = "#Lanes";
        head[4] = "#Tasks";
        head[5] = "#ConnectCalls";
        head[6] = "#GroovyExps";
        head[7] = "#CWs";
        head[8] = "#Fragments";
        dataLines.add(head);
    }

    public void AddDataLine(String projectName, int processes, int pools, int lanes, int tasks, int connectCalls, int groovyExps, int cws, int fragments)
    {
        String[] line = new String[9];
        line[0] = projectName;
        line[1] = String.valueOf(processes);
        line[2] = String.valueOf(pools);
        line[3] = String.valueOf(lanes);
        line[4] = String.valueOf(tasks);
        line[5] = String.valueOf(connectCalls);
        line[6] = String.valueOf(groovyExps);
        line[7] = String.valueOf(cws);
        line[8] = String.valueOf(fragments);
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
