package com.bonita;

import java.util.ArrayList;

public class UIDWidgetList
{
    public static ArrayList<String> providedWidgetNames()
    {
        ArrayList<String> widgetNames = new ArrayList<>();
        widgetNames.add("pbAutocomplete");
        widgetNames.add("pbButton");
        widgetNames.add("pbChart");
        widgetNames.add("pbCheckbox");
        widgetNames.add("pbChecklist");
        widgetNames.add("pbContainer");
        widgetNames.add("pbDataTable");
        widgetNames.add("pbDatePicker");
        widgetNames.add("pbDateTimePicker");
        widgetNames.add("pbFileViewer");
        widgetNames.add("pbFormContainer");
        widgetNames.add("pbImage");
        widgetNames.add("pbInput");
        widgetNames.add("pbLink");
        widgetNames.add("pbModalContainer");
        widgetNames.add("pbRadioButtons");
        widgetNames.add("pbRichTextarea");
        widgetNames.add("pbSaveButton");
        widgetNames.add("pbSelect");
        widgetNames.add("pbTabContainer");
        widgetNames.add("pbTable");
        widgetNames.add("pbTabsContainer");
        widgetNames.add("pbText");
        widgetNames.add("pbTextarea");
        widgetNames.add("pbTitle");
        widgetNames.add("pbUpload");
        return widgetNames;
    }

    public static boolean IsProvided(String widgetName)
    {
        ArrayList<String> pwidgetNames = providedWidgetNames();
        for (int i=0;i<pwidgetNames.size();i++)
        {
            if (pwidgetNames.get(i).equalsIgnoreCase(widgetName)) return true;
        }
        return false;
    }
}
