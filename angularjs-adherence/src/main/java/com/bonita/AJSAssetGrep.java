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
public class AJSAssetGrep extends DirectoryWalker
{

    public AJSAssetGrep()
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
        if (!file.getName().contains(".js")) return; // Filter .js files

        String regexp_ajsmodule = "^.*" + "angular.module" + ".*$";
        int modulesCount = 0;
        LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        try{
            while (it.hasNext()){
                String line = it.nextLine();
                if(line.matches(regexp_ajsmodule))
                {
                    modulesCount += 1;    
                    System.out.println("Found AngularJS Module in Asset in " + file.getName() );
                }
            }
        }
        finally {LineIterator.closeQuietly(it);}

        if (modulesCount>0)
            results.add(file);
    }
}
