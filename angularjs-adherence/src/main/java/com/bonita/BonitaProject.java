package com.bonita;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BonitaProject 
{

    public static ArrayList<String> seekCustomWidgets(String projectRootPath)
    {
        FormGrep grep = new FormGrep();
        List result = grep.recursive_exec(projectRootPath + "\\web_widgets\\");
        
        ArrayList<File> list = new ArrayList<File>(result);
        ArrayList<String> cwidgets = new ArrayList<String>();
        for (int i=0;i<list.size();i++)
        {
            String cwname = list.get(i).getName();
            cwname = cwname.substring(0, cwname.length() - 5); // remove ".json"

            // Check the widget is not a provided one (this happens in project Studio workspace, not in their git-repo)
            if (!UIDWidgetList.IsProvided(cwname))
            {
                cwidgets.add(cwname);
                System.out.println("Found custom widget: "+cwname);
            }
        }

        return cwidgets;
    }

    public static void seekAJSAssets(ArrayList<AJSAsset> assets, String projectRootPath, String projectName, ArrayList<String> widgetNames)
    {
        AJSAssetGrep grep = new AJSAssetGrep();

        for (int w=0;w<widgetNames.size();w++)
        {
            String assetDir = projectRootPath + "\\web_widgets\\" + widgetNames.get(w)+"\\assets\\js\\";
            List grepResult = grep.recursive_exec(assetDir);
            ArrayList<File> filelist = new ArrayList<File>(grepResult);
            for (int i=0;i<filelist.size();i++)
            {
                String assetName = filelist.get(i).getName();
                assetName = assetName.substring(0, assetName.length());

                int index = -1;
                for (int a=0;a<assets.size();a++)
                {
                    if (assets.get(a).Name.equalsIgnoreCase(assetName)) {index = a; break;}
                }
                if (index==-1)
                {
                    index = assets.size();
                    assets.add(new AJSAsset(assetName));    
                }

                assets.get(index).AddImplementation(widgetNames.get(w), projectName);
            }
        }
    }

    public static ArrayList<String> ListProjectCustomWidgetsUsingAJSAssets(ArrayList<AJSAsset> assets, String projectName)
    {
        ArrayList<String> list = new ArrayList<String>();
        for (int a=0;a<assets.size();a++)
        {
            AJSAsset asset = assets.get(a);
            for (int i=0;i<asset.ProjectNames.size();i++)
            {
                if (!asset.ProjectNames.get(i).equalsIgnoreCase(projectName)) continue;
                if (!list.contains(asset.WidgetNames.get(i))) list.add(asset.WidgetNames.get(i));
            }
        }
        return list;
    }
}
