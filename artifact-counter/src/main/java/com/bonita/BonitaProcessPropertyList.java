package com.bonita;

public class BonitaProcessPropertyList
{
    public int ProcessCount;
    public int PoolCount;
    public int LaneCount;

    public int TaskCount;
    public int ServiceTaskCount;

    public BonitaProcessPropertyList()
    {
        ProcessCount = 0;
        PoolCount = 0;
        LaneCount = 0;
        TaskCount = 0;
        ServiceTaskCount = 0;
    }

    public void aggregate(BonitaProcessPropertyList other)
    {
        ProcessCount += other.ProcessCount;
        PoolCount += other.PoolCount;
        LaneCount += other.LaneCount;
        TaskCount += other.TaskCount;
        ServiceTaskCount += other.ServiceTaskCount;
    }

    public int AllTaskCount()
    {
        return TaskCount + ServiceTaskCount;
    }
}
