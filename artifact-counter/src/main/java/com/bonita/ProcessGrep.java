package com.bonita;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.FileUtils;

// This class aims at looking for process properties in .proc files recursively.
public class ProcessGrep extends DirectoryWalker
{

    public ProcessGrep()
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
        if (!file.getName().contains(".proc")) return; // Filter .proc files

        BonitaProcessItemCounter processProp = new BonitaProcessItemCounter();

        processProp.Processes = 1; //Assuming a process already exist in a .proc file
        String regexp_pool = "^.*" + "<elements xmi:type=\"process:Pool\"" + ".*$";
        String regexp_lane = "^.*" + "<elements xmi:type=\"process:Lane\"" + ".*$";
        String regexp_task = "^.*" + "<elements xmi:type=\"process:Task\"" + ".*$";
        String regexp_stask = "^.*" + "<elements xmi:type=\"process:ServiceTask\"" + ".*$";
        String regexp_conncall = "^.*" + "<connectors xmi:type=\"process:Connector\"" + ".*$";
        String regexp_groovyexp_1 = "^.*" + "xmi:type=\"expression:Expression\"" + ".*$";
        String regexp_groovyexp_2 = "^.*" + "interpreter=\"GROOVY\"" + ".*$";

        LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        try{
            while (it.hasNext()){
                String line = it.nextLine();
                if(line.matches(regexp_pool))
                {
                    processProp.Pools += 1;    
                    System.out.println("Found Pool in " + file.getName() );
                }
                else if(line.matches(regexp_lane))
                {
                    processProp.Lanes += 1;    
                    System.out.println("Found Lane in " + file.getName() );
                }
                else if(line.matches(regexp_task))
                {
                    processProp.Tasks += 1;    
                    System.out.println("Found Task in " + file.getName() );
                }
                else if(line.matches(regexp_stask))
                {
                    processProp.ServiceTasks += 1;    
                    System.out.println("Found ServiceTask in " + file.getName() );
                }
                else if(line.matches(regexp_conncall))
                {
                    processProp.ConnectorCalls += 1;    
                    System.out.println("Found ConnectorCall in " + file.getName() );
                }
                else if(line.matches(regexp_groovyexp_1) && line.matches(regexp_groovyexp_2))
                {
                    processProp.GroovyExpressions += 1;    
                    System.out.println("Found GroovyExpression in " + file.getName() );
                }
            }
        }
        finally {LineIterator.closeQuietly(it);}

        results.add(processProp);
    }
}
