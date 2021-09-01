package com.bonita;

public class BonitaProcessItemCounter
{
    public int Processes;
    public int Pools;
    public int Lanes;

    public int Tasks;
    public int ServiceTasks;

    public int ConnectorCalls;
    public int GroovyExpressions;

    public BonitaProcessItemCounter()
    {
        Processes = 0;
        Pools = 0;
        Lanes = 0;
        Tasks = 0;
        ServiceTasks = 0;
        ConnectorCalls = 0;
        GroovyExpressions = 0;
    }

    public void aggregate(BonitaProcessItemCounter other)
    {
        Processes += other.Processes;
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
