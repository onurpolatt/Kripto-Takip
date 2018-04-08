package com.example.onurp.betc.model.graphs;

public class Graphs
{
    private String FirstValueInArray;

    private GraphData[] Data;

    private String TimeFrom;

    private String Type;

    private String Response;

    private ConversionType ConversionType;

    private String TimeTo;

    private String Aggregated;

    public String getFirstValueInArray ()
    {
        return FirstValueInArray;
    }

    public void setFirstValueInArray (String FirstValueInArray)
    {
        this.FirstValueInArray = FirstValueInArray;
    }

    public GraphData[] getData ()
    {
        return Data;
    }

    public void setData (GraphData[] Data)
    {
        this.Data = Data;
    }

    public String getTimeFrom ()
    {
        return TimeFrom;
    }

    public void setTimeFrom (String TimeFrom)
    {
        this.TimeFrom = TimeFrom;
    }

    public String getType ()
    {
        return Type;
    }

    public void setType (String Type)
    {
        this.Type = Type;
    }

    public String getResponse ()
    {
        return Response;
    }

    public void setResponse (String Response)
    {
        this.Response = Response;
    }

    public ConversionType getConversionType ()
    {
        return ConversionType;
    }

    public void setConversionType (ConversionType ConversionType)
    {
        this.ConversionType = ConversionType;
    }

    public String getTimeTo ()
    {
        return TimeTo;
    }

    public void setTimeTo (String TimeTo)
    {
        this.TimeTo = TimeTo;
    }

    public String getAggregated ()
    {
        return Aggregated;
    }

    public void setAggregated (String Aggregated)
    {
        this.Aggregated = Aggregated;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [FirstValueInArray = "+FirstValueInArray+", Data = "+Data+", TimeFrom = "+TimeFrom+", Type = "+Type+", Response = "+Response+", ConversionType = "+ConversionType+", TimeTo = "+TimeTo+", Aggregated = "+Aggregated+"]";
    }
}
