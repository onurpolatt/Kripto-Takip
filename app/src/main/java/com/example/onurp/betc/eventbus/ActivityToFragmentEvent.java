package com.example.onurp.betc.eventbus;

import com.example.onurp.betc.model.coins.CoinData;

public class ActivityToFragmentEvent  {
    private CoinData coinData;

    public CoinData getCoinData() {
        return coinData;
    }

    public void setCoinData(CoinData coinData) {
        this.coinData = coinData;
    }

    public ActivityToFragmentEvent(CoinData coinData) {
            this.coinData = coinData;
    }

}
