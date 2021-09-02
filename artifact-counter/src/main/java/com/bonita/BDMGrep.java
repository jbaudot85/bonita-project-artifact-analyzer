package com.bonita;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.FileUtils;

// This class aims at looking for BDM properties in .xml files recursively.
public class BDMGrep extends DirectoryWalker
{

    public BDMGrep()
    {
        super();
    }

    public List recursive_exec(String directoryPath)
    {
        File startDirectory = new File(directoryPath);
        List results = new ArrayList();
        try
        {
            walk(startDirectory, results);
        }
        catch (IOException e)
        {
            System.out.println( "Unable to walk through directory " + directoryPath);
            e.printStackTrace();
        }
        return results;
    }

    protected boolean handleDirectory(File directory,int depth, Collection results)
    {
        if (depth>1) return false; // Limit to first childs
        return true;
    }

    protected void handleFile(File file, int depth, Collection results) throws IOException
    { 
        if (depth>2) return; // Limit to subdirectories files
        if (!file.getName().contains(".xml")) return; // Filter .xml files

        BonitaBDMItemCounter bdmItems = new BonitaBDMItemCounter();

        String regexp_BO = "^.*" + "<businessObject qualifiedName=\"" + ".*$";
        String regexp_field = "^.*" + "<field type=\"" + ".*$";
        String regexp_rfield = "^.*" + "<relationField type=\"" + ".*$";

        LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        try{
            while (it.hasNext()){
                String line = it.nextLine();
                if(line.matches(regexp_BO))
                {
                    bdmItems.BusinessObjects += 1;    
                    System.out.println("Found BusinessObject in " + file.getName() );
                }
                else if(line.matches(regexp_field))
                {
                    bdmItems.Fields += 1;    
                    System.out.println("Found Field in " + file.getName() );
                }
                else if(line.matches(regexp_rfield))
                {
                    bdmItems.RelationFields += 1;    
                    System.out.println("Found RelationField in " + file.getName() );
                }
            }
        }
        finally {LineIterator.closeQuietly(it);}

        results.add(bdmItems);
    }
}
