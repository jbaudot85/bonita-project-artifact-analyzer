package com.bonita;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.FileUtils;

// This class aims at looking for a widget and forms occurences in Json files recursively.
public class FormGrep extends DirectoryWalker
{
    public FormGrep()
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
        if (directory.getName().contains(".metadata")) return false; // Discard metadata subfolder (appears in custom widgets structure)
        return true;
    }

    protected void handleFile(File file, int depth, Collection results) throws IOException
    { 
        if (depth>2) return; // Limit to subdirectories files (hence avoid false positives in e.g. assets)
        if (!file.getName().contains(".json")) return; // Filter JSon files

        results.add(file);
    }
}
