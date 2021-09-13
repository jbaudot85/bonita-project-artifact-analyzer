package com.bonita;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.stream.*;

// This class aims at building and exporting CSV files
public class AssetCSV
{
    List<String[]> dataLines;
    public AssetCSV()
    {
        dataLines = new ArrayList<>();
        addDataHead();
    }

    private void addDataHead()
    {
        String[] head = new String[2];
        head[0] = "Asset Name";
        head[1] = "# widgets using it";
        dataLines.add(head);
    }

    public void AddDataLine(AJSAsset asset)
    {
        String[] line = new String[2];
        line[0] = asset.Name;
        line[1] = String.valueOf(asset.WidgetNames.size());
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
