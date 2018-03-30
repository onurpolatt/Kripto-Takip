package com.example.onurp.betc.api;

import com.example.onurp.betc.model.exchanges.ExchangeDeserializer;
import com.example.onurp.betc.model.exchanges.Exchanges;
import com.example.onurp.betc.model.markets.MarketDeserializer;
import com.example.onurp.betc.model.markets.Markets;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MarketCoinAPIClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        retrofit = new Retrofit.Builder()
                .baseUrl(MarketCoinAPInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory
                        .create(new GsonBuilder()
                                .registerTypeAdapter(Exchanges.class, new ExchangeDeserializer())
                                .create()))
                .client(client)
                .build();

        return retrofit;
    }
}
