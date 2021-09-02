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
        String[] head = new String[12];
        head[0] = "Project";
        head[1] = "#BdmObj";
        head[2] = "#BdmFields";
        head[3] = "#Diagrams";
        head[4] = "#Pools";
        head[5] = "#Lanes";
        head[6] = "#Tasks";
        head[7] = "#ConnectCalls";
        head[8] = "#GroovyExps";
        head[9] = "#Pages";
        head[10] = "#CWs";
        head[11] = "#Fragments";
        dataLines.add(head);
    }

    public void AddDataLine(String projectName, int BO, int BF, int diagrams, int pools, int lanes, int tasks, int connectCalls, int groovyExps, int pages, int cws, int fragments)
    {
        String[] line = new String[12];
        line[0] = projectName;
        line[1] = String.valueOf(BO);
        line[2] = String.valueOf(BF);
        line[3] = String.valueOf(diagrams);
        line[4] = String.valueOf(pools);
        line[5] = String.valueOf(lanes);
        line[6] = String.valueOf(tasks);
        line[7] = String.valueOf(connectCalls);
        line[8] = String.valueOf(groovyExps);
        line[9] = String.valueOf(pages);
        line[10] = String.valueOf(cws);
        line[11] = String.valueOf(fragments);
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
