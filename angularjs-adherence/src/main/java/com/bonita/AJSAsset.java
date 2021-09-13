package com.bonita;

import java.util.ArrayList;

public class AJSAsset {
    public String Name;
    public ArrayList<String> WidgetNames;
    public ArrayList<String> ProjectNames;

    public AJSAsset(String name)
    {
        Name = name;
        WidgetNames = new ArrayList<String>();
        ProjectNames = new ArrayList<String>();
    }

    public void AddImplementation(String widgetName, String projectName)
    {
        WidgetNames.add(widgetName);
        ProjectNames.add(projectName);
    }
    
}
