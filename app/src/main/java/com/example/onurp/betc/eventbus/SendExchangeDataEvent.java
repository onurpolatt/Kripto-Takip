package com.example.onurp.betc.eventbus;

import com.example.onurp.betc.model.coins.CoinData;
import com.example.onurp.betc.model.markets.Markets;

public class SendExchangeDataEvent {
    private Markets markets;

    public Markets getMarkets() {
        return markets;
    }

    public void setMarkets(Markets markets) {
        this.markets = markets;
    }

    public SendExchangeDataEvent(Markets markets) {
        this.markets = markets;
    }
}
