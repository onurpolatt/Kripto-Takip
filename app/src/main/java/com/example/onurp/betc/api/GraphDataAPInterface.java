package com.example.onurp.betc.api;

import com.example.onurp.betc.model.coins.CoinData;
import com.example.onurp.betc.model.graphs.Graphs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GraphDataAPInterface {
    String BASE_URL ="https://min-api.cryptocompare.com";

    @GET("data/histoday")
    Call<Graphs> getAllGraphDataByDay(@Query("fsym") String firstCurrency,
                                      @Query("tsym") String secondCurrency,
                                      @Query("limit") String limit,
                                      @Query("aggregate") String aggregate,
                                      @Query("e") String marketName );

    @GET("data/histohour")
    Call<Graphs> getAllGraphDataByHour(@Query("fsym") String firstCurrency,
                                        @Query("tsym") String secondCurrency,
                                        @Query("limit") String limit,
                                        @Query("aggregate") String aggregate,
                                        @Query("e") String marketName);

    @GET("data/histominute")
    Call<Graphs> getAllGraphDataByMinute(@Query("fsym") String firstCurrency,
                                           @Query("tsym") String secondCurrency,
                                           @Query("limit") String limit,
                                           @Query("aggregate") String aggregate,
                                           @Query("e") String marketName);
}
