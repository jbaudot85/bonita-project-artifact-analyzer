package com.bonita;

public class BonitaDiagramItemCounter
{
    public int Diagrams;
    public int Pools;
    public int Lanes;

    public int Tasks;
    public int ServiceTasks;

    public int ConnectorCalls;
    public int GroovyExpressions;

    public BonitaDiagramItemCounter()
    {
        Diagrams = 0;
        Pools = 0;
        Lanes = 0;
        Tasks = 0;
        ServiceTasks = 0;
        ConnectorCalls = 0;
        GroovyExpressions = 0;
    }

    public void aggregate(BonitaDiagramItemCounter other)
    {
        Diagrams += other.Diagrams;
        Pools += other.Pools;
        Lanes += other.Lanes;
        Tasks += other.Tasks;
        ServiceTasks += other.ServiceTasks;
        ConnectorCalls += other.ConnectorCalls;
        GroovyExpressions += other.GroovyExpressions;
    }

    public int AnyTypeOfTasks()
    {
        return Tasks + ServiceTasks;
    }
}
