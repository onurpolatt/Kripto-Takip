package com.example.onurp.betc.model.graphs;

public class GraphData {
    private String open;

    private String time;

    private String volumeto;

    private String volumefrom;

    private String high;

    private String low;

    private String close;

    public String getOpen ()
    {
        return open;
    }

    public void setOpen (String open)
    {
        this.open = open;
    }

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getVolumeto ()
    {
        return volumeto;
    }

    public void setVolumeto (String volumeto)
    {
        this.volumeto = volumeto;
    }

    public String getVolumefrom ()
    {
        return volumefrom;
    }

    public void setVolumefrom (String volumefrom)
    {
        this.volumefrom = volumefrom;
    }

    public String getHigh ()
    {
        return high;
    }

    public void setHigh (String high)
    {
        this.high = high;
    }

    public String getLow ()
    {
        return low;
    }

    public void setLow (String low)
    {
        this.low = low;
    }

    public String getClose ()
    {
        return close;
    }

    public void setClose (String close)
    {
        this.close = close;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [open = "+open+", time = "+time+", volumeto = "+volumeto+", volumefrom = "+volumefrom+", high = "+high+", low = "+low+", close = "+close+"]";
    }
}
