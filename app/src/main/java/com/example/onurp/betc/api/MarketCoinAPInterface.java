package com.example.onurp.betc.api;

import com.example.onurp.betc.model.exchanges.Exchanges;
import com.example.onurp.betc.model.markets.Markets;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarketCoinAPInterface {
    String BASE_URL = "https://min-api.cryptocompare.com/";

    @GET("data/pricemultifull")
    Call<Exchanges> getCoinInfo(@Query("fsyms") String firstCurrency,
                              @Query("tsyms") String secondCurrency,
                              @Query("e") String marketName);
}
