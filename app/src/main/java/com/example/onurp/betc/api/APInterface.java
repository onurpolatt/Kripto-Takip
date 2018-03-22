package com.example.onurp.betc.api;

import com.example.onurp.betc.model.Coins;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by onurp on 9.03.2018.
 */

public interface APInterface {
    String BASE_URL="https://api.coinmarketcap.com/v1/";

    @GET("ticker")
    Call<List<Coins>> getUsdCoins();

    @GET("ticker")
    Call<List<Coins>> getCurrencyCoins(@Query("convert")String currency);
}
