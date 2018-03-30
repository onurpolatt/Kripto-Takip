package com.example.onurp.betc.api;

import com.example.onurp.betc.model.coins.CoinData;
import com.example.onurp.betc.model.markets.Markets;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AllCoinAPInterface {
    String BASE_URL = "https://min-api.cryptocompare.com/";

    @GET("data/all/coinlist")
    Call<CoinData> getAllCoins();
}
