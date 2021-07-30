package com.bonita;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.io.PrintWriter;
import java.util.stream.*;

public class CSV
{
    private String convertToCSV(String[] data)
    {
        return Stream.of(data)
          .collect(Collectors.joining(","));
    }
    
    public void exportTo(String filepath,ArrayList<String> names,int[] count)
    {
        List<String[]> dataLines = new ArrayList<>();

        String[] head = new String[names.size()];
        head = names.toArray(head);
        dataLines.add(head);

        String[] values = Arrays.stream(count).mapToObj(String::valueOf).toArray(String[]::new);
        dataLines.add(values);

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
