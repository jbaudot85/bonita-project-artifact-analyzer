package com.bonita;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.FileUtils;

public class Grep extends DirectoryWalker
{
    public String regexp = null;
    public String text = null;

    public Grep(String _text){
        super();
        if (_text!=null)
        {
            text = "\"id\" : \"" + _text + "\"";
            regexp = "^.*" + text + ".*$";
        }
    }

    public Grep(){
        this(null);
    }

    public List recursive(String directoryPath)
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

    protected boolean handleDirectory(File directory,
                                      int depth, Collection results){
      // Decide if a (sub) directory will be handled for recursive search
      return true;
    }

    protected void handleFile(File file, int depth, Collection results) throws IOException
    { 
        if (!file.getName().contains(".json")) return;
        if (regexp==null)
        {
            // Just return the files having a json extension
            results.add(file);
        }
        else
        {
            // Return the files having a json extension, as many as the number of seeked text occurence in it
            LineIterator it = FileUtils.lineIterator(file, "UTF-8");
            try{
                while (it.hasNext()){
                    String line = it.nextLine();
                    if(line.matches(regexp)){
                        results.add(file);
                        System.out.println("Found occurrence of " + text + " in " + file.getName() );
                    }
                }
            }
            finally {LineIterator.closeQuietly(it);}
        }
    }
}
