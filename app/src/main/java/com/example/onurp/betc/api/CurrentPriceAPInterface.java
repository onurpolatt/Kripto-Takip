package com.example.onurp.betc.api;

import com.example.onurp.betc.model.CurrentPrice;
import com.example.onurp.betc.model.exchanges.Exchanges;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrentPriceAPInterface {
    String BASE_URL = "https://min-api.cryptocompare.com/";

    @GET("data/price")
    Call<Map<String, String>> getCoinPrice(@Query("fsym") String firstCurrency,
                                           @Query("tsyms") String secondCurrency,
                                           @Query("e") String marketName);

}
