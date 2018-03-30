package com.example.onurp.betc.api;

import com.example.onurp.betc.model.Coins;
import com.example.onurp.betc.model.markets.Markets;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MarketAPInterface {
    String BASE_URL = "https://min-api.cryptocompare.com/";

    @GET("data/all/exchanges")
    Call<Markets> getMarket();

}
