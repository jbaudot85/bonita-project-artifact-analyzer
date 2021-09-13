package com.bonita;

import java.util.ArrayList;

public class App 
{   
    public static void main( String[] args )
    {
        WidgetCSV widgetCsv = new WidgetCSV();
        AssetCSV assetCsv = new AssetCSV();
        ArrayList<AJSAsset> assets = new ArrayList<AJSAsset>();

        // Git projects
        {
            ArrayList<String> projectNames = new ArrayList<>();
            projectNames.add("app-HRManagement");
            projectNames.add("app-InvoiceManagement");
            projectNames.add("app-Ticketing");
            projectNames.add("credit-card-dispute-resolution");
            projectNames.add("expense-report-example");
            projectNames.add("procurement-example");
            projectNames.add("showroom-cloud");
            projectNames.add("tahiti");
            projectNames.add("serenity");

            String projectsPath = "C:\\data\\git\\";
            
            for (int p=0;p<projectNames.size();p++)
            {
                BonitaProjectAnalysis.exec(assets, projectsPath, projectNames.get(p), widgetCsv, assetCsv);     
            }
        }

        // Workspace projects
        {
            ArrayList<String> projectNames = new ArrayList<>();
            projectNames.add("DemandeTeletravail-1.2");
            projectNames.add("CECILIAPEREZ_Alta de Persona Juridica-4.3.0");
            projectNames.add("CECILIAPEREZ_Reporte de Eventos-2.8");
            projectNames.add("DongheeBaik_Security Advisory Process-1.0");
            projectNames.add("FabienDelannoy_SESAM_SaisieAchat-1.3");
            projectNames.add("HariPrasadAlla_TravelReqAppDiagram-1.0");
            projectNames.add("JonathanSeguraGomez_Consumer_Finance_Vehiculo-1.0");
            projectNames.add("josemiguelsiunavarro_repositoriobp_20200211_PR007");
            projectNames.add("MatthewRiding_Process Killing Diagram-3.0");
            projectNames.add("MatthewRiding_SnpmDownAlert-1.0");
            projectNames.add("MatthewRobinson_AAAdCompFaculty");
            projectNames.add("soo_workspace_bpm_tc"); 

            String projectsPath = "C:\\data\\workspace\\";
            
            for (int p=0;p<projectNames.size();p++)
            {
                BonitaProjectAnalysis.exec(assets, projectsPath, projectNames.get(p), widgetCsv, assetCsv);     
            }
        }

        // Gather cross project data
        assets.forEach(asset -> assetCsv.AddDataLine(asset));

        // Export reports
        widgetCsv.exportTo("widgets.csv");
        assetCsv.exportTo("assets.csv");
    }
}
