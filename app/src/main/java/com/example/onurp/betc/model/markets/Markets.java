package com.example.onurp.betc.model.markets;

import java.util.Map;

public class Markets
{

    private Map<String, Map<String, String[]>> exchangePairs;

    Markets() {

    }

    public Map<String, Map<String, String[]>> getExchangePairs() {
        return exchangePairs;
    }

    public void setExchangePairs(Map<String, Map<String, String[]>> exchangePairs) {
        this.exchangePairs = exchangePairs;
    }

    public Map<String, String[]> getTradingPairs(String fromSymbol) {
        return exchangePairs.get(fromSymbol);
    }
}