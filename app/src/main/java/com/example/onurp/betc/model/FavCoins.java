package com.example.onurp.betc.model;

public class FavCoins {
    private  String fromSymbol;
    private  String toSymbol;
    private  String marketName;
    private  String concatInfo;
    private  String coinFullName;
    private  String coinImageUrl;

    public FavCoins(){

    }

    public FavCoins(String fromSymbol,String toSymbol,String marketName,String concatCurrency,String coinFullName,String coinImageUrl){
        this.fromSymbol = fromSymbol;
        this.toSymbol = toSymbol;
        this.marketName = marketName;
        this.concatInfo = concatCurrency;
        this.coinFullName = coinFullName;
        this.coinImageUrl = coinImageUrl;
    }

    public String getCoinImageUrl() {
        return coinImageUrl;
    }

    public void setCoinImageUrl(String coinImageUrl) {
        this.coinImageUrl = coinImageUrl;
    }

    public String getCoinFullName() {
        return coinFullName;
    }

    public void setCoinFullName(String coinFullName) {
        this.coinFullName = coinFullName;
    }

    public String getConcatInfo() {
        return concatInfo;
    }

    public void setConcatInfo(String concatInfo) {
        this.concatInfo = concatInfo;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getFromSymbol() {
        return fromSymbol;
    }

    public void setFromSymbol(String fromSymbol) {
        this.fromSymbol = fromSymbol;
    }

    public String getToSymbol() {
        return toSymbol;
    }

    public void setToSymbol(String toSymbol) {
        this.toSymbol = toSymbol;
    }
}
