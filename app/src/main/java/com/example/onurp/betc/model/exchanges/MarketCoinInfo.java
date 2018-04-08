package com.example.onurp.betc.model.exchanges;

public class MarketCoinInfo
{
    private String CURRENCY;

    private String COINNAME;

    private String PRICE;

    private String LASTUPDATE;

    private String LASTTRADEID;

    private String VOLUME24HOUR;

    private String CHANGEDAY;

    private String FROMSYMBOL;

    private String LASTVOLUMETO;

    private String TOTALVOLUME24H;

    private String LOW24HOUR;

    private String MARKET;

    private String HIGH24HOUR;

    private String CHANGEPCTDAY;

    private String MKTCAP;

    private String TOSYMBOL;

    private String LASTVOLUME;

    private String VOLUME24HOURTO;

    private String OPEN24HOUR;

    private String CHANGE24HOUR;

    private String SUPPLY;

    private String TOTALVOLUME24HTO;

    private String CHANGEPCT24HOUR;

    public MarketCoinInfo(String PRICE, String VOLUME24HOUR, String FROMSYMBOL, String CHANGEPCT24HOUR,String COINNAME,String TOSYMBOL) {
        this.PRICE = PRICE;
        this.VOLUME24HOUR = VOLUME24HOUR;
        this.FROMSYMBOL = FROMSYMBOL;
        this.CHANGEPCT24HOUR = CHANGEPCT24HOUR;
        this.COINNAME = COINNAME;
        this.TOSYMBOL = TOSYMBOL;
    }

    public MarketCoinInfo(String CURRENCY,String MARKET,String FROMSYMBOL) {
        this.CURRENCY = CURRENCY;
        this.MARKET = MARKET;
    }

    public String getCURRENCY() {
        return CURRENCY;
    }

    public void setCURRENCY(String CURRENCY) {
        this.CURRENCY = CURRENCY;
    }

    public String getCOINNAME() {
        return COINNAME;
    }

    public void setCOINNAME(String COINNAME) {
        this.COINNAME = COINNAME;
    }

    public String getPRICE ()
    {
        return PRICE;
    }

    public void setPRICE (String PRICE)
    {
        this.PRICE = PRICE;
    }

    public String getLASTUPDATE ()
    {
        return LASTUPDATE;
    }

    public void setLASTUPDATE (String LASTUPDATE)
    {
        this.LASTUPDATE = LASTUPDATE;
    }

    public String getLASTTRADEID ()
    {
        return LASTTRADEID;
    }

    public void setLASTTRADEID (String LASTTRADEID)
    {
        this.LASTTRADEID = LASTTRADEID;
    }

    public String getVOLUME24HOUR ()
    {
        return VOLUME24HOUR;
    }

    public void setVOLUME24HOUR (String VOLUME24HOUR)
    {
        this.VOLUME24HOUR = VOLUME24HOUR;
    }

    public String getCHANGEDAY ()
    {
        return CHANGEDAY;
    }

    public void setCHANGEDAY (String CHANGEDAY)
    {
        this.CHANGEDAY = CHANGEDAY;
    }

    public String getFROMSYMBOL ()
    {
        return FROMSYMBOL;
    }

    public void setFROMSYMBOL (String FROMSYMBOL)
    {
        this.FROMSYMBOL = FROMSYMBOL;
    }

    public String getLASTVOLUMETO ()
    {
        return LASTVOLUMETO;
    }

    public void setLASTVOLUMETO (String LASTVOLUMETO)
    {
        this.LASTVOLUMETO = LASTVOLUMETO;
    }

    public String getTOTALVOLUME24H ()
    {
        return TOTALVOLUME24H;
    }

    public void setTOTALVOLUME24H (String TOTALVOLUME24H)
    {
        this.TOTALVOLUME24H = TOTALVOLUME24H;
    }

    public String getLOW24HOUR ()
    {
        return LOW24HOUR;
    }

    public void setLOW24HOUR (String LOW24HOUR)
    {
        this.LOW24HOUR = LOW24HOUR;
    }

    public String getMARKET ()
    {
        return MARKET;
    }

    public void setMARKET (String MARKET)
    {
        this.MARKET = MARKET;
    }

    public String getHIGH24HOUR ()
    {
        return HIGH24HOUR;
    }

    public void setHIGH24HOUR (String HIGH24HOUR)
    {
        this.HIGH24HOUR = HIGH24HOUR;
    }

    public String getCHANGEPCTDAY ()
    {
        return CHANGEPCTDAY;
    }

    public void setCHANGEPCTDAY (String CHANGEPCTDAY)
    {
        this.CHANGEPCTDAY = CHANGEPCTDAY;
    }

    public String getMKTCAP ()
    {
        return MKTCAP;
    }

    public void setMKTCAP (String MKTCAP)
    {
        this.MKTCAP = MKTCAP;
    }

    public String getTOSYMBOL ()
    {
        return TOSYMBOL;
    }

    public void setTOSYMBOL (String TOSYMBOL)
    {
        this.TOSYMBOL = TOSYMBOL;
    }

    public String getLASTVOLUME ()
    {
        return LASTVOLUME;
    }

    public void setLASTVOLUME (String LASTVOLUME)
    {
        this.LASTVOLUME = LASTVOLUME;
    }

    public String getVOLUME24HOURTO ()
    {
        return VOLUME24HOURTO;
    }

    public void setVOLUME24HOURTO (String VOLUME24HOURTO)
    {
        this.VOLUME24HOURTO = VOLUME24HOURTO;
    }

    public String getOPEN24HOUR ()
    {
        return OPEN24HOUR;
    }

    public void setOPEN24HOUR (String OPEN24HOUR)
    {
        this.OPEN24HOUR = OPEN24HOUR;
    }

    public String getCHANGE24HOUR ()
    {
        return CHANGE24HOUR;
    }

    public void setCHANGE24HOUR (String CHANGE24HOUR)
    {
        this.CHANGE24HOUR = CHANGE24HOUR;
    }

    public String getSUPPLY ()
    {
        return SUPPLY;
    }

    public void setSUPPLY (String SUPPLY)
    {
        this.SUPPLY = SUPPLY;
    }

    public String getTOTALVOLUME24HTO ()
    {
        return TOTALVOLUME24HTO;
    }

    public void setTOTALVOLUME24HTO (String TOTALVOLUME24HTO)
    {
        this.TOTALVOLUME24HTO = TOTALVOLUME24HTO;
    }

    public String getCHANGEPCT24HOUR ()
    {
        return CHANGEPCT24HOUR;
    }

    public void setCHANGEPCT24HOUR (String CHANGEPCT24HOUR)
    {
        this.CHANGEPCT24HOUR = CHANGEPCT24HOUR;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [PRICE = "+PRICE+", LASTUPDATE = "+LASTUPDATE+", LASTTRADEID = "+LASTTRADEID+", VOLUME24HOUR = "+VOLUME24HOUR+", CHANGEDAY = "+CHANGEDAY+", FROMSYMBOL = "+FROMSYMBOL+", LASTVOLUMETO = "+LASTVOLUMETO+", TOTALVOLUME24H = "+TOTALVOLUME24H+", LOW24HOUR = "+LOW24HOUR+", MARKET = "+MARKET+", HIGH24HOUR = "+HIGH24HOUR+", CHANGEPCTDAY = "+CHANGEPCTDAY+", MKTCAP = "+MKTCAP+", TOSYMBOL = "+TOSYMBOL+", LASTVOLUME = "+LASTVOLUME+", VOLUME24HOURTO = "+VOLUME24HOURTO+", OPEN24HOUR = "+OPEN24HOUR+", CHANGE24HOUR = "+CHANGE24HOUR+", SUPPLY = "+SUPPLY+", TOTALVOLUME24HTO = "+TOTALVOLUME24HTO+", CHANGEPCT24HOUR = "+CHANGEPCT24HOUR+"]";
    }
}
