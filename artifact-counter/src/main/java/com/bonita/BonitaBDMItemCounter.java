package com.bonita;

public class BonitaBDMItemCounter
{
    public int BusinessObjects;
    public int Fields;
    public int RelationFields;

    public BonitaBDMItemCounter()
    {
        BusinessObjects = 0;
        Fields = 0;
        RelationFields = 0;
    }

    public void aggregate(BonitaBDMItemCounter other)
    {
        BusinessObjects += other.BusinessObjects;
        Fields += other.Fields;
        RelationFields += other.RelationFields;
    }

    public int AnyTypeOfFields()
    {
        return Fields + RelationFields;
    }
}
