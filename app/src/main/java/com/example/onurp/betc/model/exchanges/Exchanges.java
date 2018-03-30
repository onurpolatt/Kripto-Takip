package com.example.onurp.betc.model.exchanges;

import java.util.List;
import java.util.Map;

public class Exchanges {
    private Map<String,Map<String,Map<String, MarketCoinInfo>>> results;

    public Map<String,Map<String,Map<String, MarketCoinInfo>>> getResults() {
        return results;
    }

    public void setResults(Map<String,Map<String,Map<String, MarketCoinInfo>>> results) {
        this.results = results;
    }

}
