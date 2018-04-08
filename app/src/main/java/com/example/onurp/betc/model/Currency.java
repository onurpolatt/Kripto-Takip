package com.example.onurp.betc.model;

public class Currency {
    private String cc;
    private String name;
    private String symbol;

    public Currency(){

    }

    public Currency(String cc,String name,String symbol){
        this.cc = cc;
        this.name = name;
        this.symbol = symbol;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return getName()+" ("+getCc()+")";
    }
}
